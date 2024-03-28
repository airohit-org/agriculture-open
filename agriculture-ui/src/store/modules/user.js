import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken, setIp, setTenantId ,setFarmTenantId} from '@/utils/auth'
import defAva from '@/assets/images/profile.jpg'
import avatarImage from '@/assets/images/farmer.png'
const useUserStore = defineStore(
  'user',
  {
    state: () => ({
      token: getToken(),
      id: '',
      name: '',
      avatar: '',
      roles: [],
      permissions: []
    }),
    actions: {
      // 登录
      login(userInfo) {
        const username = userInfo.username.trim()
        const password = userInfo.password
        // const code = userInfo.code
        // const uuid = userInfo.uuid
        return new Promise((resolve, reject) => {
          login(username, password).then(res => {
            setToken(res.data.accessToken)
            setIp(res.data.userIP);
            setTenantId(res.data.tenantId)
            setFarmTenantId(res.data.farmList[0].id)
            resolve(res.data)
            this.token = res.data.accessToken
            resolve()
          }).catch(error => {
            reject(error)
          })
        })
      },
      // 获取用户信息
      getInfo() {
        return new Promise((resolve, reject) => {
          getInfo().then(res => {
            const user = res.data.user
            // const avatar = (user.avatar == "" || user.avatar == null) ? defAva : import.meta.env.VITE_APP_BASE_API + user.avatar;
            const avatar = ( user.avatar === "" || user.avatar == null ) ? avatarImage : user.avatar;
            if (res.data.roles && res.data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
              this.roles = res.data.roles
              this.permissions = res.data.permissions
            } else {
              this.roles = ['ROLE_DEFAULT']
            }
            this.id = user.id
            this.name = user.nickname
            this.avatar = avatar
            resolve(res)
          }).catch(error => {
            reject(error)
          })
        })
      },
      // 退出系统
      logOut() {
        return new Promise((resolve, reject) => {
          logout(this.token).then(() => {
            this.token = ''
            this.roles = []
            this.permissions = []
            removeToken()
            resolve()
          }).catch(error => {
            reject(error)
          })
        })
      }
    }
  })

export default useUserStore
