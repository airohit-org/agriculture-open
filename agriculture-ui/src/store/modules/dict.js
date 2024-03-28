const useDictStore = defineStore(
  'dict',
  {
    state: () => ({
      dict: [
        {
          key: 'sys_normal_disable',
          value: [
            {
              label: '正常',
              value: 0,
              elTagType: 'primary',
              elTagClass: ''
            },
            {
              label: '停用',
              value: 1,
              elTagType: 'danger',
              elTagClass: ''
            }
          ]
        },
        {
          key: 'sys_user_sex',
          value: [
            {
              label: '男',
              value: 1,
              elTagType: '',
              elTagClass: ''
            },
            {
              label: '女',
              value: 2,
              elTagType: '',
              elTagClass: ''
            }
          ]
        },
        {
          key: 'system_role_type',
          value: [
            {
              value: '1',
              label: '内置',
              elTagType: 'danger',
              elTagClass: ''
            },
            {
              value: '2',
              label: '自定义',
              elTagType: 'primary',
              elTagClass: ''
            }
          ]
        },
        {
          key: 'common_status',
          value: [
            {
              value: '0',
              label: '开启',
              elTagType: 'primary',
              elTagClass: ''
            },
            {
              value: '1',
              label: '关闭',
              elTagType: 'info',
              elTagClass: ''
            },
          ]
        },
        {
          key: 'system_menu_type',
          value: [
            {
              value: 1,
              label: '目录',
              colorType: '',
              cssClass: ''
            },
            {
              value: 2,
              label: '菜单',
              colorType: '',
              cssClass: ''
            },
            {
              value: 3,
              label: '按钮',
              colorType: '',
              cssClass: ''
            },
          ]
        }

      ]
    }),
    actions: {
      // 获取字典
      getDict(_key) {
        if (_key == null && _key == "") {
          return null;
        }
        try {
          // console.log(this.dict)
          for (let i = 0; i < this.dict.length; i++) {
            if (this.dict[i].key == _key) {
              return this.dict[i].value;
            }
          }
        } catch (e) {
          return null;
        }
      },
      // 设置字典
      setDict(_key, value) {
        if (_key !== null && _key !== "") {
          this.dict.push({
            key: _key,
            value: value
          });
        }
      },
      // 删除字典
      removeDict(_key) {
        var bln = false;
        try {
          for (let i = 0; i < this.dict.length; i++) {
            if (this.dict[i].key == _key) {
              this.dict.splice(i, 1);
              return true;
            }
          }
        } catch (e) {
          bln = false;
        }
        return bln;
      },
      // 清空字典
      cleanDict() {
        this.dict = new Array();
      },
      // 初始字典
      initDict() {
      }
    }
  })

export default useDictStore
