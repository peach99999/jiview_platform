<style lang="less">
  @import './login.less';
</style>

<template>
  <div class="login">
    <div class="login-con">
      <Card icon="log-in" title="欢迎登录" :bordered="false">
        <div class="form-con">
          <login-form @on-success-valid="handleSubmit" :loading="loading"></login-form>
          <!--<p class="login-tip">输入任意用户名和密码即可</p>-->
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
import LoginForm from '_c/login-form'
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      loading: false
    }
  },
  components: {
    LoginForm
  },
  methods: {
    ...mapActions([
      'handleLogin'
    ]),
    handleSubmit ({ userName, password }) {
      console.log('提交登录')
      this.loading = true
      this.handleLogin({ userName, password }).then(res => {
        this.loading = false
        this.$router.push({
          name: this.$config.homeName
        })
      }).catch(err => {
        this.loading = false
        console.log('error==>', err)
        if (err.response && err.response.data && err.response.data.msgCode === '1200012') {
          this.$Message.warning('您的账号已过期')
        } else if (String(err).indexOf('timeout') > -1) {
          this.$Message.warning('登陆超时!')
        } else {
          this.$Message.warning('请输入正确的用户名或密码')
        }
      })
    }
  }
}
</script>

<style>

</style>
