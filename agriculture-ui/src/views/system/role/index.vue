<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryRef"
      v-show="showSearch"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="角色名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入角色名称"
          clearable
          style="width: 240px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="权限字符" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入权限字符"
          clearable
          style="width: 240px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="角色状态"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" style="width: 308px">
        <el-date-picker
          v-model="dateRange"
          value-format="YYYY-MM-DD"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['system:role:create']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:role:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        v-model:showSearch="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>
    <!-- 表格数据 -->
    <el-table
      v-loading="loading"
      :data="roleList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="角色编号" type="index" width="120" />
      <el-table-column
        label="角色名称"
        prop="name"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="角色标识"
        prop="code"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column label="角色类型" align="center" prop="type" width="80">
        <template #default="scope">
          <dict-tag :options="system_role_type" :value="scope.row.type" />
        </template>
      </el-table-column>
      <el-table-column label="显示顺序" prop="sort" width="100" />
      <el-table-column label="状态" align="center" width="100">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="0"
            :inactive-value="1"
            :before-change="() => handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template #default="scope">
          <el-tooltip content="修改" placement="top" v-if="scope.row.id !== 1">
            <el-button
              link
              type="primary"
              icon="Edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:role:update']"
            ></el-button>
          </el-tooltip>
          <el-tooltip
            content="菜单权限"
            placement="top"
            v-if="scope.row.id !== 1"
          >
            <el-button
              link
              type="primary"
              icon="User"
              @click="handleMenu(scope.row)"
              v-hasPermi="['system:permission:assign-role-menu']"
            ></el-button>
            <!-- <el-button link type="primary" icon="User" @click="handleAuthUser(scope.row)" v-hasPermi="['system:permission:assign-role-menu']"></el-button> -->
          </el-tooltip>
          <el-tooltip
            content="数据权限"
            placement="top"
            v-if="scope.row.id !== 1"
          >
            <el-button
              link
              type="primary"
              icon="CircleCheck"
              @click="handleDataScope(scope.row)"
              v-hasPermi="['system:permission:assign-role-data-scope']"
            ></el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top" v-if="scope.row.id !== 1">
            <el-button
              link
              type="primary"
              icon="Delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:role:delete']"
            ></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改角色配置对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="roleRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item prop="code" label="角色标识">
          <el-input v-model="form.code" placeholder="请输入角色标识" />
        </el-form-item>
        <el-form-item label="角色顺序" prop="sort">
          <el-input-number
            v-model="form.sort"
            controls-position="right"
            :min="0"
          />
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 分配角色数据权限对话框 -->
    <el-dialog
      :title="title"
      v-model="openDataScope"
      width="500px"
      append-to-body
    >
      <el-form :model="form" label-width="80px">
        <el-form-item label="角色名称">
          <el-input v-model="form.name" :disabled="true" />
        </el-form-item>
        <el-form-item label="权限字符">
          <el-input v-model="form.code" :disabled="true" />
        </el-form-item>
        <el-form-item label="权限范围">
          <el-select v-model="form.dataScope" @change="dataScopeSelectChange">
            <el-option
              v-for="item in dataScopeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据权限" v-show="form.dataScope == 2">
          <el-checkbox
            :checked="!form.deptCheckStrictly"
            @change="handleCheckedTreeConnect($event, 'dept')"
            >父子联动(选中父节点，自动选择子节点)</el-checkbox
          >
          <el-checkbox
            v-model="deptExpand"
            @change="handleCheckedTreeExpand($event, 'dept')"
            >展开/折叠</el-checkbox
          >
          <el-checkbox
            v-model="deptNodeAll"
            @change="handleCheckedTreeNodeAll($event, 'dept')"
            >全选/全不选</el-checkbox
          >
          <el-tree
            class="tree-border"
            :data="deptOptions"
            show-checkbox
            default-expand-all
            ref="deptRef"
            node-key="id"
            :check-strictly="form.deptCheckStrictly"
            empty-text="加载中，请稍候"
            style="width: 100%"
            :props="{ label: 'label', children: 'children' }"
          ></el-tree>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitDataScope">确 定</el-button>
          <el-button @click="cancelDataScope">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 分配角色的菜单权限对话框 -->
    <el-dialog :title="title" v-model="openMenu" width="500px" append-to-body>
      <el-form :model="menuForm" label-width="80px">
        <el-form-item label="角色名称">
          <el-input v-model="menuForm.name" :disabled="true" />
        </el-form-item>
        <el-form-item label="角色标识">
          <el-input v-model="menuForm.code" :disabled="true" />
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-checkbox
            v-model="menuExpand"
            @change="handleCheckedTreeExpand($event, 'menuRef')"
            >展开/折叠</el-checkbox
          >
          <el-checkbox
            v-model="menuNodeAll"
            @change="handleCheckedTreeNodeAll($event, 'menuRef')"
            >全选/全不选</el-checkbox
          >
          <el-tree
            class="tree-border"
            :data="menuOptions"
            show-checkbox
            ref="menuRef"
            node-key="id"
            style="width: 100%"
            :check-strictly="menuForm.menuCheckStrictly"
            empty-text="加载中，请稍候"
            :props="{ label: 'name', children: 'children' }"
          ></el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitMenu">确 定</el-button>
        <el-button @click="cancelMenu">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup name="Role">
import {
  addRole,
  changeRoleStatus,
  dataScope,
  delRole,
  getRole,
  listRole,
  updateRole,
  deptTreeSelect,
} from "@/api/system/role";

import { listRoleMenus,assignRoleMenu } from "@/api/system/permission";
import { listSimpleDepts } from "@/api/system/dept";
import { listSimpleMenus } from "@/api/system/menu";
import { downloadGet } from '@/utils/request'
const router = useRouter();
const { proxy } = getCurrentInstance();
const { sys_normal_disable, system_role_type } = proxy.useDict(
  "sys_normal_disable",
  "system_role_type"
);

const roleList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dateRange = ref([]);
const menuOptions = ref([]);
const menuExpand = ref(false);
const menuNodeAll = ref(false);
const deptExpand = ref(true);
const deptNodeAll = ref(false);
const deptOptions = ref([]);
const openDataScope = ref(false);
const openMenu = ref(false);
const menuRef = ref(null);
const deptRef = ref(null);

/** 数据范围选项*/
const dataScopeOptions = ref([
  { value: 1, label: "全部数据权限" },
  { value: 2, label: "指定部门数据权限" },
  { value: 3, label: "本部门数据权限" },
  { value: 4, label: "本部门及以下数据权限" },
  { value: 5, label: "仅本人数据权限" },
]);

const data = reactive({
  menuForm: {},
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: undefined,
    code: undefined,
    status: undefined,
  },
  rules: {
    name: [{ required: true, message: "角色名称不能为空", trigger: "blur" }],
    code: [{ required: true, message: "权限字符不能为空", trigger: "blur" }],
    sort: [{ required: true, message: "角色顺序不能为空", trigger: "blur" }],
  },
});

const { queryParams, form, rules, menuForm } = toRefs(data);

/** 查询角色列表 */
function getList() {
  loading.value = true;
  listRole(proxy.addDateRange(queryParams.value, dateRange.value)).then(
    (response) => {
      roleList.value = response.data.list;
      total.value = response.data.total;
      loading.value = false;
    }
  );
}
/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}
/** 重置按钮操作 */
function resetQuery() {
  dateRange.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}
/** 删除按钮操作 */
function handleDelete(row) {
  const ids = row.id || ids.value;
  proxy.$modal
    .confirm('是否确认删除角色编号为"' + ids + '"的数据项?')
    .then(function () {
      return delRole(ids);
    })
    .then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    })
    .catch(() => {});
}
/** 导出按钮操作 */
function handleExport() {
  downloadGet(
    "system/role/export",
    {
      ...queryParams.value,
    },
    `角色数据.xls`
  );
}
/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map((item) => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}
/** 角色状态修改 */
function handleStatusChange(row) {
  let text = row.status === "0" ? "启用" : "停用";
  proxy.$modal
    .confirm('确认要"' + text + '""' + row.name + '"角色吗?')
    .then(function () {
      return changeRoleStatus(row.id, row.status);
    })
    .then(() => {
      proxy.$modal.msgSuccess(text + "成功");
    })
    .catch(function () {
      row.status = row.status === "0" ? "1" : "0";
    });
}
/** 更多操作 */
function handleCommand(command, row) {
  switch (command) {
    case "handleDataScope":
      handleDataScope(row);
      break;
    // case "handleAuthUser":
    //   handleAuthUser(row);
    //   break;
    default:
      break;
  }
}
// /** 分配用户 */
// function handleAuthUser(row) {
//   router.push("/system/role-auth/user/" + row.id);
// }
/** 查询菜单树结构 */
function getMenuTreeselect() {
  listSimpleMenus().then((response) => {
    menuOptions.value = response.data;
  });
}
/** 所有部门节点数据 */
function getDeptAllCheckedKeys() {
  // 目前被选中的部门节点
  let checkedKeys = deptRef.value.getCheckedKeys();
  // 半选中的部门节点
  let halfCheckedKeys = deptRef.value.getHalfCheckedKeys();
  checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
  return checkedKeys;
}
/** 重置新增的表单以及其他数据  */
function reset() {
  if (menuRef.value != undefined) {
    menuRef.value.setCheckedKeys([]);
  }
  menuExpand.value = false;
  menuNodeAll.value = false;
  deptExpand.value = true;
  deptNodeAll.value = false;
  form.value = {
    id: undefined,
    name: undefined,
    code: undefined,
    sort: 0,
    status: "0",
    menuIds: [],
    deptIds: [],
    menuCheckStrictly: true,
    deptCheckStrictly: true,
    remark: undefined,
  };
  proxy.resetForm("roleRef");
}
/** 添加角色 */
function handleAdd() {
  reset();
  // getMenuTreeselect();
  open.value = true;
  title.value = "添加角色";
}
/** 修改角色 */
function handleUpdate(row) {
  reset();
  // proxy.resetForm("queryRef");
  const id = row.id || ids.value;
  // const roleMenu = getRoleMenuTreeselect(id);
  getRole(id).then((response) => {
    form.value = response.data;
    form.value.sort = Number(form.value.sort);
    open.value = true;
    // nextTick(() => {
    //   roleMenu.then((res) => {
    //     let checkedKeys = res.checkedKeys;
    //     checkedKeys.forEach((v) => {
    //       nextTick(() => {
    //         menuRef.value.setChecked(v, true, false);
    //       });
    //     });
    //   });
    // });
    title.value = "修改角色";
  });
}
function menuReset() {
  proxy.resetForm("menuForm");
  menuForm.value = {
    id: undefined,
    name: undefined,
    code: undefined,
  };
}
function cancelMenu() {
  openMenu.value = false;
  menuReset();
}
function submitMenu() {
  if (menuForm.value.id !== undefined) {
    assignRoleMenu({
      roleId: menuForm.value.id,
      menuIds: [...menuRef.value.getCheckedKeys(), ...menuRef.value.getHalfCheckedKeys()],
    }).then((response) => {
      proxy.$modal.msgSuccess("修改成功");
      openMenu.value = false;
      getList();
    });
  }
}
/** 分配菜单权限操作 */
function handleMenu(row) {
  // this.reset();
  menuReset();
  title.value = "分配菜单权限";
  const id = row.id;
  // 处理了 form 的角色 name 和 code 的展示
  menuForm.value.id = id;
  menuForm.value.name = row.name;
  menuForm.value.code = row.code;
  // 打开弹窗
  openMenu.value = true;
  // 获得菜单列表
  listSimpleMenus().then((response) => {
    // 处理 menuOptions 参数
    menuOptions.value = [];
    menuOptions.value=proxy.handleTree(response.data, "id");
    // 获取角色拥有的菜单权限
    listRoleMenus(id).then((response) => {
      // 设置为严格，避免设置父节点自动选中子节点，解决半选中问题
      menuForm.value.menuCheckStrictly = true;
      // 设置选中
      menuRef.value.setCheckedKeys(response.data);
      // 设置为非严格，继续使用半选中
      menuForm.value.menuCheckStrictly = false;
    });
  });
}
/** 根据角色ID查询菜单树结构 */
// function getRoleMenuTreeselect(id) {
//   return roleMenuTreeselect(id).then((response) => {
//     menuOptions.value = response.menus;
//     return response;
//   });
// }
/** 根据角色ID查询部门树结构 */
function getDeptTree(id) {
  return deptTreeSelect(id).then((response) => {
    deptOptions.value = response.depts;
    return response;
  });
}
/** 树权限（展开/折叠）*/
function handleCheckedTreeExpand(value, type) {
  if (type == "menuRef") {
    let treeList = menuOptions.value;
    for (let i = 0; i < treeList.length; i++) {
      menuRef.value.store.nodesMap[treeList[i].id].expanded = value;
    }
  } else if (type == "dept") {
    let treeList = deptOptions.value;
    for (let i = 0; i < treeList.length; i++) {
      deptRef.value.store.nodesMap[treeList[i].id].expanded = value;
    }
  }
}
/** 树权限（全选/全不选） */
function handleCheckedTreeNodeAll(value, type) {
  if (type == "menuRef") {
    menuRef.value.setCheckedNodes(value ? menuOptions.value : []);
  } else if (type == "dept") {
    deptRef.value.setCheckedNodes(value ? deptOptions.value : []);
  }
}
/** 树权限（父子联动） */
function handleCheckedTreeConnect(value, type) {
  if (type == "menuRef") {
    form.value.menuCheckStrictly = value ? true : false;
  } else if (type == "dept") {
    form.value.deptCheckStrictly = value ? true : false;
  }
}
// /** 所有菜单节点数据 */
// function getMenuAllCheckedKeys() {
//   // 目前被选中的菜单节点
//   let checkedKeys = menuRef.value.getCheckedKeys();
//   // 半选中的菜单节点
//   let halfCheckedKeys = menuRef.value.getHalfCheckedKeys();
//   checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
//   return checkedKeys;
// }
/** 提交按钮 */
function submitForm() {
  proxy.$refs["roleRef"].validate((valid) => {
    if (valid) {
      if (form.value.id != undefined) {
        // form.value.menuIds = getMenuAllCheckedKeys();
        updateRole(form.value).then((response) => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        // form.value.menuIds = getMenuAllCheckedKeys();
        addRole(form.value).then((response) => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}
/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}
/** 选择角色权限范围触发 */
function dataScopeSelectChange(value) {
  if (value !== "2") {
    deptRef.value.setCheckedKeys([]);
  }
}
/** 分配数据权限操作 */
function handleDataScope(row) {
  reset();
  const deptTreeSelect = listSimpleDepts();
  getRole(row.id).then((response) => {
    form.value = response.data;
    openDataScope.value = true;
    nextTick(() => {
      deptTreeSelect.then((res) => {
        nextTick(() => {
          if (deptRef.value) {
            deptRef.value.setCheckedKeys(res.checkedKeys);
          }
        });
      });
    });
    title.value = "分配数据权限";
  });
}
/** 提交按钮（数据权限） */
function submitDataScope() {
  if (form.value.id != undefined) {
    form.value.deptIds = getDeptAllCheckedKeys();
    const data = {
      roleId: form.value.id,
      dataScope: form.value.dataScope,
      dataScopeDeptIds:form.value.dataScopeDeptIds||[]
    }
    dataScope(data).then((response) => {
      proxy.$modal.msgSuccess("修改成功");
      openDataScope.value = false;
      getList();
    });
  }
}
/** 取消按钮（数据权限）*/
function cancelDataScope() {
  openDataScope.value = false;
  reset();
}

getList();
</script>
