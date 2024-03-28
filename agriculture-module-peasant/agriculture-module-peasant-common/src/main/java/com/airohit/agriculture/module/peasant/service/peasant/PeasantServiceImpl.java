package com.airohit.agriculture.module.peasant.service.peasant;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.airohit.agriculture.framework.common.exception.ServiceException;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.datapermission.core.annotation.DataPermission;
import com.airohit.agriculture.module.peasant.convert.peasant.PeasantConvert;
import com.airohit.agriculture.module.peasant.dal.dataobject.peasant.PeasantDO;
import com.airohit.agriculture.module.peasant.dal.dataobject.peasant.ProvinceVo;
import com.airohit.agriculture.module.peasant.dal.mysql.peasant.PeasantMapper;
import com.airohit.agriculture.module.peasant.dal.mysql.peasant.RealProvinceMapper;
import com.airohit.agriculture.module.peasant.vo.*;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.peasant.enums.ErrorCodeConstants.*;
import static com.airohit.agriculture.module.system.enums.ErrorCodeConstants.*;

/**
 * 农户 Service 实现类
 *
 * @author lrj
 */
@Service
@Validated
public class PeasantServiceImpl implements PeasantService {

    @Resource
    private PeasantMapper Mapper;

    @Resource
    private RealProvinceMapper realProvinceMapper;


    @Override
    public Integer create(PeasantCreateReqVO createReqVO) {
        // 插入
        PeasantDO peasantDO = PeasantConvert.INSTANCE.convert(createReqVO);

        String phone = peasantDO.getPhone();
        if (Mapper.selectByPhone(phone) == null) {

            // 密码加密
            RSA rsa = new RSA(PRIVATE_KAY, PUBLIC_KSY);
            //解密密码
            String password = new String(
                    rsa.decrypt(Base64.getDecoder().decode(peasantDO.getPassWord().getBytes(StandardCharsets.UTF_8))
                            , KeyType.PrivateKey), StandardCharsets.UTF_8);
            peasantDO.setPassWord(password);

            Mapper.insert(peasantDO);
            // 返回
            return peasantDO.getId();
        } else {
            throw exception(PEASANT_EXISTS);
        }
    }

    @Override
    public void update(PeasantUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateExists(updateReqVO.getId());
        // 更新
        PeasantDO updateObj = PeasantConvert.INSTANCE.convert(updateReqVO);

        RSA rsa = new RSA(PRIVATE_KAY, PUBLIC_KSY);
        //解密密码
        String password = new String(
                rsa.decrypt(Base64.getDecoder().decode(updateObj.getPassWord().getBytes(StandardCharsets.UTF_8))
                        , KeyType.PrivateKey), StandardCharsets.UTF_8);
        updateObj.setPassWord(password);

        Mapper.updateById(updateObj);
    }

    @Override
    public void delete(Integer id) {
        // 校验存在
        this.validateExists(id);
        // 删除
        Mapper.deleteById(id);
    }

    private void validateExists(Integer id) {
        if (Mapper.selectById(id) == null) {
            throw exception(PEASANT_NOT_EXISTS);
        }
    }


    @Override
    public PeasantDO get(Integer id) {
        return Mapper.selectById(id);
    }

    @Override
    public List<PeasantDO> getList(Collection<Integer> ids) {
        return Mapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PeasantDO> getPage(PeasantPageReqVO pageReqVO) {
        return Mapper.selectPage(pageReqVO);
    }

    @Override
    public List<PeasantDO> getList(PeasantExportReqVO exportReqVO) {
        return Mapper.selectList(exportReqVO);
    }

    public List<PeasantDO> getAllList() {
        PeasantExportReqVO planExportReqVO = new PeasantExportReqVO();
        return Mapper.selectListAll(planExportReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public ExcelImportRespVO importExcel(List<Excel> importUsers, boolean isUpdateSupport) {

        if (CollUtil.isEmpty(importUsers)) {
            throw exception(USER_IMPORT_LIST_IS_EMPTY);
        }
        ExcelImportRespVO respVO = ExcelImportRespVO.builder()
                .createUsernames(new ArrayList<>())
                .updateUsernames(new ArrayList<>())
                .failureUsernames(new LinkedHashMap<>()).build();
        importUsers.forEach(importUser -> {

            // 校验，判断是否有不符合的原因
            try {
                checkCreateOrUpdate(null, importUser.getPhone(), importUser.getProvince(), importUser.getCity(), importUser.getArea());
            } catch (ServiceException ex) {
                respVO.getFailureUsernames().put(importUser.getPhone(), ex.getMessage());
                return;
            }

            // 判断如果不存在，在进行插入
            PeasantDO existUser = Mapper.selectByPhone(importUser.getPhone());

            if (existUser == null) {
                Mapper.insert(PeasantConvert.INSTANCE.convertExcel(importUser)
                                .setPassWord("123456")
                        //.setPostIds(new HashSet<>())
                ); // 设置默认密码及空岗位编号数组
                respVO.getCreateUsernames().add(importUser.getPhone());
                return;
            }

            // 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureUsernames().put(importUser.getPhone(), USER_USERNAME_EXISTS.getMsg());
                return;
            }

            PeasantDO updateUser = PeasantConvert.INSTANCE.convertExcel(importUser);
            updateUser.setId(existUser.getId());
            Mapper.updateById(updateUser);
            respVO.getUpdateUsernames().add(importUser.getPhone());

        });
        return respVO;
    }

    @DataPermission(enable = false) // 关闭数据权限，避免因为没有数据权限，查询不到数据，进而导致唯一校验不正确
    public void checkCreateOrUpdate(Integer id, String mobile, String province, String city, String area) {
        // 校验用户存在
        //checkUserExists(id);
        // 校验手机号唯一
        checkMobileUnique(id, mobile);
        checkProvinceExists(province);
        checkCityExists(city);
        checkAreaExists(area);

    }

    // 校验手机号
    @VisibleForTesting
    public void checkMobileUnique(Integer id, String phone) {
        // 判断空
        if (StrUtil.isBlank(phone)) {
            // 没填写 手机号
            throw exception(PEASANT_NOT_PHONE);
        } else {
            PeasantDO user = Mapper.selectByPhone(phone);

            if (user == null) {
                return;
            } else {
                // 手机号重复
                throw exception(PEASANT_NOT_PHONE_ONE);
            }
        }
    }

    // 省
    public void checkProvinceExists(String province) {
        if (StrUtil.isBlank(province)) {
            throw exception(PEASANT_NOT_PROVINCE);
        } else {

            ProvinceVo user = realProvinceMapper.selectByProvince(province);
            //ProvinceVo user = Mapper.selectProvince(province);

            if (user == null) {
                throw exception(PEASANT_ERROR_PROVINCE);
            } else {
                return;
            }

        }
    }

    // 市
    public void checkCityExists(String city) {
        if (StrUtil.isBlank(city)) {
            throw exception(PEASANT_NOT_CITY);
        } else {
            return;
            /*
            CityVo user = cityMapper.selectByCity(city);

            if (user == null) {
                throw exception(PEASANT_ERROR_CITY);
            }
            else {
                return;
            }

             */
        }
    }

    // 区县
    public void checkAreaExists(String area) {
        if (StrUtil.isBlank(area)) {
            throw exception(PEASANT_NOT_AREA);
        } else {
            return;

            /*
            AreaVo user = areaMapper.selectByArea(area);

            if (user == null) {
                throw exception(PEASANT_ERROR_AREA);
            }
            else{
                return;
            }

             */
        }
    }

    //  ------------------------------------- app 相关

    public Integer createApp(PeasantCreateReqVO createReqVO) {
        // 插入
        PeasantDO peasantDO = PeasantConvert.INSTANCE.convert(createReqVO);

        String phone = peasantDO.getPhone();
        if (Mapper.selectByPhone(phone) == null) {

            // 密码加密
            RSA rsa = new RSA(PRIVATE_KAY, PUBLIC_KSY);
            //解密密码
            String password = new String(
                    rsa.decrypt(Base64.getDecoder().decode(peasantDO.getPassWord().getBytes(StandardCharsets.UTF_8))
                            , KeyType.PrivateKey), StandardCharsets.UTF_8);
            peasantDO.setPassWord(password);

            Mapper.insert(peasantDO);
            // 返回
            return peasantDO.getId();
        } else {
            throw exception(PEASANT_EXISTS);
        }
    }

    @Override
    public void updateApp(PeasantUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateExists(updateReqVO.getId());
        // 更新
        PeasantDO updateObj = PeasantConvert.INSTANCE.convert(updateReqVO);

        RSA rsa = new RSA(PRIVATE_KAY, PUBLIC_KSY);
        //解密密码
        String password = new String(
                rsa.decrypt(Base64.getDecoder().decode(updateObj.getPassWord().getBytes(StandardCharsets.UTF_8))
                        , KeyType.PrivateKey), StandardCharsets.UTF_8);
        updateObj.setPassWord(password);

        Mapper.updateById(updateObj);
    }

    @Override
    public void deleteApp(Integer id) {
        // 校验存在
        this.validateExists(id);
        // 删除
        Mapper.deleteById(id);
    }


    @Override
    public PeasantDO getApp(Integer id) {
        return Mapper.selectById(id);
    }

    @Override
    public List<PeasantDO> getListApp(Collection<Integer> ids) {
        return Mapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PeasantDO> getPageApp(PeasantPageReqVO pageReqVO) {
        return Mapper.selectPage(pageReqVO);
    }


}
