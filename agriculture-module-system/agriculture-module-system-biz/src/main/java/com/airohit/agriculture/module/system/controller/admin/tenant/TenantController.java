package com.airohit.agriculture.module.system.controller.admin.tenant;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.api.slave.vo.tenant.TenantCreateReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.tenant.TenantPageReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.tenant.TenantRespVO;
import com.airohit.agriculture.module.system.api.slave.vo.tenant.TenantUpdateReqVO;
import com.airohit.agriculture.module.system.convert.tenant.TenantConvert;
import com.airohit.agriculture.module.system.dal.dataobject.tenant.TenantDO;
import com.airohit.agriculture.module.system.service.tenant.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 租户")
@RestController
@RequestMapping("/system/tenant")
public class TenantController {

    @Resource
    private TenantService tenantService;

    @GetMapping("/get-id-by-name")
    @PermitAll
    @ApiOperation(value = "使用租户名，获得租户编号", notes = "登录界面，根据用户的租户名，获得租户编号")
    @ApiImplicitParam(name = "name", value = "租户名", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<Long> getTenantIdByName(@RequestParam("name") String name) {
        TenantDO tenantDO = tenantService.getTenantByName(name);
        return success(tenantDO != null ? tenantDO.getId() : null);
    }

    @PostMapping("/create")
    @ApiOperation("创建租户")
    @PreAuthorize("@ss.hasPermission('system:tenant:create')")
    public CommonResult<Long> createTenant(@Valid @RequestBody TenantCreateReqVO createReqVO) {
        return success(tenantService.createTenant(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新租户")
    @PreAuthorize("@ss.hasPermission('system:tenant:update')")
    public CommonResult<Boolean> updateTenant(@Valid @RequestBody TenantUpdateReqVO updateReqVO) {
        tenantService.updateTenant(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除租户")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:tenant:delete')")
    public CommonResult<Boolean> deleteTenant(@RequestParam("id") Long id) {
        tenantService.deleteTenant(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得租户")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:tenant:query')")
    public CommonResult<TenantRespVO> getTenant(@RequestParam("id") Long id) {
        TenantDO tenant = tenantService.getTenant(id);
        return success(TenantConvert.INSTANCE.convert(tenant));
    }

    @GetMapping("/page")
    @ApiOperation("获得租户分页")
    @PreAuthorize("@ss.hasPermission('system:tenant:query')")
    public CommonResult<PageResult<TenantRespVO>> getTenantPage(@Valid TenantPageReqVO pageVO) {
        PageResult<TenantDO> pageResult = tenantService.getTenantPage(pageVO);
        return success(TenantConvert.INSTANCE.convertPage(pageResult));
    }


}
