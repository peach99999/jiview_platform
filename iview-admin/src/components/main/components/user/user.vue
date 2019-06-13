<template>
  <div>
    <div class="user-avatar-dropdown">
      <Dropdown @on-click="handleClick">
        <Badge :dot="!!messageUnreadCount">
          <Avatar :src="userAvatar"/>
        </Badge>
        <Icon :size="18" type="md-arrow-dropdown"></Icon>
        <DropdownMenu slot="list">
          <DropdownItem name="message">
            消息中心<Badge style="margin-left: 10px" :count="messageUnreadCount"></Badge>
          </DropdownItem>
          <DropdownItem name="changeMyPwd">修改密码</DropdownItem>
          <DropdownItem name="logout">退出登录</DropdownItem>
        </DropdownMenu>
      </Dropdown>
    </div>
    <Modal v-model="showChangePwdFlg" scrollable title="修改密码">
      <Form ref="account" :model="account" :label-width="100" label-position="right"
            :rules="passwordValidate">
        <FormItem label="新密码" prop="newPwd">
          <Input v-model="account.newPwd" placeholder="请输入新密码，至少6位字符" style="width:300px;" />
        </FormItem>
        <FormItem label="确认新密码" prop="confirmNewPwd">
          <Input v-model="account.confirmNewPwd" placeholder="请再次输入新密码" style="width:300px;" />
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="text" @click="changePwdCancelHandle">取消</Button>
        <Button type="primary" :loading="changePwd_loading" @click="changePwdConfirmHandle">保存</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import './user.less'
import { mapActions } from 'vuex'
import * as userManagementApi from '@/api/user'
export default {
  name: 'User',
  data () {
    const valideRePassword = (rule, value, callback) => {
      if (value !== this.account.newPwd) {
        callback(new Error('两次输入密码不一致'))
      } else {
        callback()
      }
    }
    return {
      changePwd_loading: false,
      account: {
        newPwd: '',
        confirmPwd: ''
      },
      showChangePwdFlg: false,
      passwordValidate: {
        newPwd: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, message: '请至少输入6个字符', trigger: 'blur' },
          { max: 32, message: '最多输入32个字符', trigger: 'blur' }
        ],
        confirmNewPwd: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' },
          { validator: valideRePassword, trigger: 'blur' }
        ]
      }
    }
  },
  props: {
    userAvatar: {
      type: String,
      default: ''
    },
    messageUnreadCount: {
      type: Number,
      default: 0
    }
  },
  methods: {
    ...mapActions([
      'handleLogOut'
    ]),
    changePwdCancelHandle () {
      this.showChangePwdFlg = false
      this.$refs.account.resetFields()
    },
    changePwdConfirmHandle () {
      const self = this
      this.$refs.account.validate((valid) => {
        if (valid) {
          this.changePwd_loading = true
          let params = {
            account: self.account.account,
            password: self.account.newPwd
          }
          userManagementApi.changeUserPwd(params)
            .then(res => {
              self.$Message.success('密码修改成功')
              self.changePwd_loading = false
              self.showChangePwdFlg = false
              self.logout()
            })
            .catch(err => {
              console.log('err', err)
              self.changePwd_loading = false
              self.$Message.error('密码修改失败')
            })
        }
      })
    },
    logout () {
      this.handleLogOut().then(() => {
        this.$router.push({
          name: 'login'
        })
      })
    },
    message () {
      this.$router.push({
        name: 'message_page'
      })
    },
    changeMyPwd () {
      this.account = {
        newPwd: ''
      }
      this.showChangePwdFlg = true
    },
    handleClick (name) {
      switch (name) {
        case 'logout': this.logout()
          break
        case 'message': this.message()
          break
        case 'changeMyPwd': this.changeMyPwd()
          break
      }
    }
  }
}
</script>
