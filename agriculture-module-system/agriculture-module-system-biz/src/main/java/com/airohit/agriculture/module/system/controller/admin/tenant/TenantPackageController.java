package com.airohit.agriculture.module.system.controller.admin.tenant;

import com.airohit.agriculture.framework.common.enums.CommonStatusEnum;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.api.slave.vo.packages.*;
import com.airohit.agriculture.module.system.convert.tenant.TenantPackageConvert;
import com.airohit.agriculture.module.system.dal.dataobject.tenant.TenantPackageDO;
import com.airohit.agriculture.module.system.service.tenant.TenantPackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 租户套餐")
@RestController
@RequestMapping("/system/tenant-package")
@Validated
public class TenantPackageController {

    @Resource
    private TenantPackageService tenantPackageService;

    @PostMapping("/create")
    @ApiOperation("创建租户套餐")
    @PreAuthorize("@ss.hasPermission('system:tenant-package:create')")
    public CommonResult<Long> createTenantPackage(@Valid @RequestBody TenantPackageCreateReqVO createReqVO) {
        return success(tenantPackageService.createTenantPackage(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新租户套餐")
    @PreAuthorize("@ss.hasPermission('system:tenant-package:update')")
    public CommonResult<Boolean> updateTenantPackage(@Valid @RequestBody TenantPackageUpdateReqVO updateReqVO) {
        tenantPackageService.updateTenantPackage(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除租户套餐")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:tenant-package:delete')")
    public CommonResult<Boolean> deleteTenantPackage(@RequestParam("id") Long id) {
        tenantPackageService.deleteTenantPackage(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得租户套餐")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:tenant-package:query')")
    public CommonResult<TenantPackageRespVO> getTenantPackage(@RequestParam("id") Long id) {
        TenantPackageDO tenantPackage = tenantPackageService.getTenantPackage(id);
        return success(TenantPackageConvert.INSTANCE.convert(tenantPackage));
    }

    @GetMapping("/page")
    @ApiOperation("获得租户套餐分页")
    @PreAuthorize("@ss.hasPermission('system:tenant-package:query')")
    public CommonResult<PageResult<TenantPackageRespVO>> getTenantPackagePage(@Valid TenantPackagePageReqVO pageVO) {
        PageResult<TenantPackageDO> pageResult = tenantPackageService.getTenantPackagePage(pageVO);
        return success(TenantPackageConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/get-simple-list")
    @ApiOperation(value = "获取租户套餐精简信息列表", notes = "只包含被开启的租户套餐，主要用于前端的下拉选项")
    public CommonResult<List<TenantPackageSimpleRespVO>> getTenantPackageList() {
        // 获得角色列表，只要开启状态的
        List<TenantPackageDO> list = tenantPackageService.getTenantPackageListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(TenantPackageConvert.INSTANCE.convertList02(list));
    }

}
