<template>
  <div>
    <Collapse v-model="value1">
      <Panel name="1">
        <Row slot="content">
          <Row>
            <Form ref="filter" :model="filter" inline @keydown.enter.native="doQuery">
              <FormItem>
                <Cascader :data="cascaderData" v-model="filter.deptId" change-on-select ref="cascaderParentIds" placeholder="请选择部门" style="width:300px;"></Cascader>
              </FormItem>
              <FormItem>
                <Input type="text" v-model.trim="filter.account" placeholder="账号名" clearable style="width:300px;"/>
              </FormItem>
              <FormItem>
                <Input type="text" v-model.trim="filter.userName" placeholder="用户名" clearable style="width:300px;"/>
              </FormItem>
            </Form>
          </Row>
          <Row>
            <Button type="primary" icon="ios-search" :loading="loading" @click="doQuery">查询</Button>
            <Button class="margin-left-10" type="default" icon="ios-document-outline" @click="reset" >重置</Button>
          </Row>
        </Row>
      </Panel>
    </Collapse>
    <Card class="margin-top-10">
      <Row>
        <ButtonGroup class="margin-bottom-10">
          <Button type="success" icon="ios-add-circle-outline" @click="addOrEditRoleFlg = true;showPassword=true">新增</Button>
          <Button type="warning" icon="ios-trash-outline" @click="deleteBatch">删除</Button>
        </ButtonGroup>
        <Table :columns="tableTitle" :data="tableData" @on-selection-change="changeSelect"></Table>
      </Row>
      <Row class="margin-top-10" style="text-align: center">
        <Page :current="filter.pageNo" :total="total" show-elevator show-sizer show-total @on-change="pageChangeHandle" @on-page-size-change="pageSizeChangeHandle"/>
      </Row>
      <Spin fix v-if="loading">
        <Icon type="load-c" size=18 class="demo-spin-icon-load"></Icon>
        <div>加载中...</div>
      </Spin>
      <Modal v-model="addOrEditRoleFlg" scrollable title="新增/编辑用户" @on-visible-change="restTest" :mask-closable="false">
        <Form ref="user" :label-width="120" :model="user" inline :rules="inforValidate">
          <FormItem label="账号：" prop="account">
            <Input v-model.trim="user.account" style="width:300px;" placeholder="请输入账号"/>
          </FormItem>
          <FormItem label="密码：" prop="password" v-if="showPassword">
            <Input v-model.trim="user.password" style="width:300px;" placeholder="请输入密码"/>
          </FormItem>
          <FormItem label="所属部门：" prop="deptId">
            <Cascader :data="cascaderData" v-model="user.deptId" change-on-select ref="cascaderParentIds" placeholder="请选择部门"  style="width:300px;"></Cascader>
          </FormItem>
          <FormItem label="E-mail：" prop="email">
            <Input v-model.trim="user.email" style="width:300px;" placeholder="请输入E-mail"/>
          </FormItem>
          <FormItem label="手机号：" prop="mobile">
            <Input v-model.trim="user.mobile" style="width:300px;" placeholder="请输入手机号"/>
          </FormItem>
          <FormItem label="用户名：" prop="userName">
            <Input v-model.trim="user.userName" style="width:300px;" placeholder="请输入用户名"/>
          </FormItem>
          <FormItem label="用户类型：" prop="userType">
            <Select v-model="user.userType" style="width:300px;" placeholder="请选择用户类型" clearable filterable :transfer="true">
              <Option v-for="(item,index) in userTypeList" :value="item.userType" :key="index">{{ item.userTypeName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="角色类型：" prop="roleId">
            <Select v-model="user.roleId" style="width:300px;" multiple placeholder="请选择角色类型" clearable filterable :transfer="true">
              <Option v-for="(item,index) in roleSelectList" :value="item.roleId" :key="index">{{ item.roleName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="性别：" prop="sex">
            <Select v-model="user.sex" style="width:300px;" placeholder="请选择用户类型" clearable filterable :transfer="true">
              <Option v-for="(item,index) in sexList" :value="item.sex" :key="index">{{ item.sexName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="启用状态：" prop="enabled">
            <Select v-model="user.enabled" style="width:300px;" placeholder="请选择锁定状态" clearable filterable :transfer="true">
              <Option v-for="(item,index) in enabledStatusList" :value="item.enabled" :key="index">{{ item.enabledStatus }}</Option>
            </Select>
          </FormItem>
          <FormItem label="锁定状态：" prop="locked">
            <Select v-model="user.locked" style="width:300px;" placeholder="请选择锁定状态" clearable filterable :transfer="true">
              <Option v-for="(item,index) in lockedStatusList" :value="item.locked" :key="index">{{ item.lockedStatus }}</Option>
            </Select>
          </FormItem>
          <FormItem label="备注：" prop="remark">
            <Input v-model.trim="user.remark" style="width:300px;" placeholder="请输入备注"/>
          </FormItem>
        </Form>
        <div slot="footer">
          <Button type="text" @click="saveOrUpdateHandle">取消</Button>
          <Button type="primary" :loading="save_loading" @click="saveOrUpdateConfirmHandle">保存</Button>
        </div>
      </Modal>
      <Modal v-model="showChangePwdFlg" scrollable title="修改密码" :mask-closable="false">
        <Row>
          <Form ref="account" :model="account" :label-width="100" label-position="right"
                :rules="passwordValidate">
            <FormItem label="新密码" prop="newPwd">
              <Input v-model="account.newPwd" placeholder="请输入新密码，至少6位字符" style="width:300px;" />
            </FormItem>
            <FormItem label="确认新密码" prop="confirmNewPwd">
              <Input v-model="account.confirmNewPwd" placeholder="请再次输入新密码" style="width:300px;" />
            </FormItem>
          </Form>
        </Row>
        <div slot="footer">
          <Button type="text" @click="changePwdCancelHandle">取消</Button>
          <Button type="primary" :loading="changePwd_loading" @click="changePwdConfirmHandle">保存</Button>
        </div>
      </Modal>
    </Card>
  </div>
</template>

<script>
import * as sysUserManagementApi from '@/api/sysUser'
import * as roleManagementApi from '@/api/role'
import * as userManagementApi from '@/api/user'
import { getDepartmentList } from '@/api/organizationalManagement'
import { getMenuId } from '@/libs/util'
export default {
  components: {

  },
  data () {
    const valideRePassword = (rule, value, callback) => {
      if (value !== this.account.newPwd) {
        callback(new Error('两次输入密码不一致'));
      } else {
        callback();
      }
    };
    return {
      value1: '1',
      addOrEditRoleFlg: false,
      loading: false,
      currentRow: {},
      filter: {
        pageNo: 1,
        pageSize: 10
      },
      user: {
        account: '',
        password: '',
        deptId: [],
        email: '',
        mobile: '',
        userName: '',
        userType: null,
        roleId: [],
        sex: null,
        locked: null,
        enabled: null,
        remark: ''
      },
      save_loading: false,
      inforValidate: {
        account: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '请选择部门', trigger: 'change', type: 'array' }
        ],
        email: [
          { required: true, message: '请输入E-mail', trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '请输入手机号', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        userType: [
          { required: true, message: '请选择用户类型', trigger: 'change', type: 'number' }
        ],
        roleId: [
          { required: false, message: '请选择角色类型', trigger: 'change', type: 'array' }
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'change', type: 'number' }
        ],
        locked: [
          { required: true, message: '请选择锁定状态', trigger: 'change', type: 'number' }
        ],
        enabled: [
          { required: true, message: '请选择启用状态', trigger: 'change', type: 'number' }
        ],
        remark: [
          { required: true, message: '请输入备注', trigger: 'blur' }
        ]
      },
      dateFormat: 'yyyy-MM-dd hh:mm:ss',
      tableTitle: [
        {
          type: 'selection',
          fixed: 'left',
          align: 'center',
          width: 60
        },
        {
          title: '操作',
          key: 'action',
          fixed: 'left',
          align: 'center',
          width: 170,
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small',
                  ghost: ''
                },
                on: {
                  click: () => {
                    this.queryRoleDetail(params.row.id)
                  }
                }
              }, '详情'),
              h('Button', {
                props: {
                  type: 'success',
                  size: 'small',
                  ghost: ''
                },
                style: {
                  marginLeft: '10px'
                },
                on: {
                  click: () => {
                    console.log('params:', params)
                    this.account = {
                      account: params.row.account,
                      newPwd: '',
                      confirmNewPwd: ''
                    };
                    this.showChangePwdFlg = true;
                  }
                }
              }, '修改密码')
            ])
          }
        },
        {
          title: '部门名称',
          key: 'deptName',
          align: 'center',
          width: 260,
          render: function (h, params) {
            return h('div', params.row.deptName ? params.row.deptName : '----')
          }
        },
        {
          title: '账号',
          key: 'account',
          align: 'center',
          width: 260,
          render: function (h, params) {
            return h('div', params.row.account ? params.row.account : '----')
          }
        },
        {
          title: '用户名',
          key: 'userName',
          align: 'center',
          width: 260,
          render: function (h, params) {
            return h('div', params.row.userName ? params.row.userName : '----')
          }
        },
        {
          title: '人员类型',
          key: 'userType',
          align: 'center',
          width: 260,
          render: function (h, params) {
            if (params.row.userType === 1) {
              return h('div', '业务人员')
            }
            if (params.row.userType === 2) {
              return h('div', '管理员')
            }
            if (params.row.userType === 3) {
              return h('div', '系统内置人员')
            }
          }
        }
      ],
      tableData: [],
      pkidList: [],
      selected: [],
      total: 0,
      regionCompanyList: [],
      cascaderData: [],
      userTypeList: [
        {
          userType: 1,
          userTypeName: '业务人员'
        },
        {
          userType: 2,
          userTypeName: '管理员'
        },
        {
          userType: 3,
          userTypeName: '系统内置人员'
        }
      ],
      sexList: [
        {
          sex: 1,
          sexName: '男'
        },
        {
          sex: 2,
          sexName: '女'
        }
      ],
      lockedStatusList: [
        {
          locked: 1,
          lockedStatus: '锁定'
        },
        {
          locked: 0,
          lockedStatus: '激活'
        }
      ],
      enabledStatusList: [
        {
          enabled: 1,
          enabledStatus: '启用'
        },
        {
          enabled: 0,
          enabledStatus: '不启用'
        }
      ],
      deptIdList: [],
      currentInfo: {},
      showPassword: false,
      roleSelectList: [],
      showChangePwdFlg: false,
      account: {
        newPwd: '',
        confirmNewPwd: ''
      },
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
      },
      changePwd_loading: false
    }
  },
  mounted () {
    const self = this
    self.init()
  },
  methods: {
    init () {
      const self = this
      self.doQuery()
      self.getDepartmentList()
      self.getRoleSelectList()
      self.getMenuId(self.$store.state.app.menuList, self.$route.meta.title)
    },
    getMenuId (list, name) {
      if (getMenuId(list, name)) {
        localStorage.setItem("menuId", getMenuId(list, name))
      }
      console.log('menuId:', localStorage.getItem("menuId"))
    },
    listForInit () {
      const self = this
      self.loading = true
      // 调用角色列表接口
      let param = {
        pageNo: self.filter.pageNo,
        pageSize: self.filter.pageSize
      }
      if (self.filter.deptId) {
        let index = self.filter.deptId.length - 1
        param.deptId = self.filter.deptId[index]
      }
      if (self.filter.account) {
        param.account = self.filter.account
      }
      if (self.filter.userName) {
        param.userName = self.filter.userName
      }
      sysUserManagementApi.getUserList(param).then(res => {
        self.total = res.data.count
        self.tableData = res.data.rows || []
        self.loading = false
      }).catch(err => {
        console.log('err', err)
        self.loading = false
        // self.$Message.error(message['1001']);
      })
    },
    // 查询
    doQuery () {
      const self = this
      self.filter.pageNo = 1
      self.listForInit()
    },
    // 重置
    reset () {
      const self = this
      self.filter.roleName = ''
      self.filter.deptId = []
      self.filter.account = ''
      self.filter.userName = ''
    },
    // 新增或更新保存
    saveOrUpdateConfirmHandle () {
      const self = this
      self.$refs.user.validate((valid) => {
        if (valid) {
          this.saveOrUpdateRole()
        }
      })
    },
    saveOrUpdateRole () {
      const self = this
      self.save_loading = true
      let index = self.user.deptId.length - 1
      let param = {
        account: self.user.account,
        password: self.user.password,
        deptId: self.user.deptId[index],
        email: self.user.email,
        mobile: self.user.mobile,
        userName: self.user.userName,
        userType: self.user.userType,
        roleIdList: self.user.roleId,
        sex: self.user.sex,
        locked: self.user.locked,
        remark: self.user.remark,
        enabled: self.user.enabled
      }
      if (self.user.id) {
        param.id = self.user.id
      }
      sysUserManagementApi.addAndUpdateUserInfo(param)
        .then(res => {
          self.save_loading = false
          if (self.user.id) {
            // self.$Message.success(message['3001']);
          } else {
            // self.$Message.success(message['3003']);
          }
          // 关闭弹框
          self.saveOrUpdateHandle()
          // 列表刷新
          self.listForInit()
        })
        .catch(err => {
          console.log('err', err)
          self.save_loading = false
          if (self.role.pkid) {
            // self.$Message.error(message['3002']);
          } else {
            // self.$Message.error(message['3004']);
          }
        })
    },
    // 新增或更新取消
    saveOrUpdateHandle () {
      this.addOrEditRoleFlg = false
      this.$refs.user.resetFields()
      this.user = {}
    },
    // 点击遮罩层、右上角关闭按钮时清空数据
    restTest () {
      if (this.addOrEditRoleFlg === false) {
        this.saveOrUpdateHandle()
      }
    },
    // 详情按钮单击
    queryRoleDetail (pkid) {
      const self = this
      self.showPassword = false
      self.deptIdList = []
      // 调用获取角色信息接口
      sysUserManagementApi.getUserInfoDetail(pkid).then(res => {
        self.addOrEditRoleFlg = true
        self.foreachAndSearchDeptParentNode(self.cascaderData, res.data.row.deptId)
        if (res.data.row) {
          self.user.deptId = self.deptIdList
          self.user.account = res.data.row.account
          self.user.email = res.data.row.email
          self.user.mobile = res.data.row.mobile
          self.user.sex = res.data.row.sex
          self.user.userType = res.data.row.userType
          self.user.userName = res.data.row.userName
          self.user.id = res.data.row.id
          if (res.data.row.sysUserRoleExtList && res.data.row.sysUserRoleExtList.length > 0) {
            let list = []
            for (const value of res.data.row.sysUserRoleExtList) {
              list.push(value.roleId)
            }
            self.user.roleId = list
          }
          if (res.data.row.remark) {
            self.user.remark = res.data.row.remark
          }
          if (res.data.row.locked) {
            self.user.locked = 1
          }
          if (!res.data.row.locked) {
            self.user.locked = 0
          }
          if (res.data.row.enabled) {
            self.user.enabled = 1
          }
          if (!res.data.row.enabled) {
            self.user.enabled = 0
          }
        }
      }).catch(err => {
        console.log('err', err)
        // self.$Message.error(message['1001']);
      })
    },
    // 遍历数组 查找部门的父部门
    foreachAndSearchDeptParentNode (deptList, deptId) {
      const self = this
      if (deptList) {
        for (let value of deptList) {
          if (deptId === value.deptId) {
            if (value.parentId) {
              self.foreachAndSearchDeptParentNode(self.cascaderData, value.parentId)
            }
            self.deptIdList.push(value.deptId)
          }
          if (deptId !== value.deptId) {
            if (value.children) {
              self.foreachAndSearchDeptParentNode(value.children, deptId)
            }
          }
        }
      }
    },
    // 选中项发生变化
    changeSelect (value) {
      const self = this
      self.selected = value
    },
    // 批量删除
    deleteBatch () {
      const self = this
      if (self.selected.length <= 0) {
        // self.$Message.warning(message['4001']);
        return false
      }
      console.log('self.selected:', self.selected)
      self.$Modal.confirm({
        title: '提示',
        content: '是否确认删除?',
        okText: '确认',
        cancelText: '取消',
        onOk: () => {
          for (let i of self.selected) {
            self.pkidList.push(i.id)
          }
          let params = {
            userIdList: self.pkidList
          }
          sysUserManagementApi.removeUserInfo(params).then(res => {
            // 清空选中列表
            self.pkidList = []
            self.selected = []
            // self.$Message.success(message['4002']);
            self.listForInit()
          }).catch(err => {
            console.log('err', err)
            // self.$Message.error(message['1001']);
          })
        },
        onCancel: () => {}
      })
    },
    // 翻页
    pageChangeHandle (pageNo) {
      const self = this
      self.filter.pageNo = pageNo
      self.listForInit()
    },
    pageSizeChangeHandle (pageSize) {
      const self = this
      self.filter.pageSize = pageSize
      self.listForInit()
    },
    // 获取部门信息
    getDepartmentList (param, flag) {
      const self = this
      getDepartmentList(param).then(res => {
        let list = [...res.data.rows] || []
        if (!param || flag) {
          self.handleDepartmentList(list)
        }
      }).catch(err => {
        console.log('err', err)
      })
    },
    // 将获得数据转换成树型结构数据
    handleDepartmentList (list) {
      let listTmp = [...list]
      let treeData = []
      for (let i = 0; i < listTmp.length; i++) {
        if (!listTmp[i].parentId) {
          let obj = {
            title: listTmp[i].deptName,
            expand: true,
            deptId: listTmp[i].deptId,
            children: []
          }
          treeData.push(obj)
          listTmp.splice(i, 1)
          i--
        }
      }
      this.cascaderData = this.handleTreeData(listTmp, treeData)
      this.formatForCascader(this.cascaderData)
      // list:需要转换的数组（平铺型数组）  treeData:目标转换的数组（树型数组）
    },
    handleTreeData (list, treeData) {
      // list:需要转换的数组（平铺型数组）  treeData:目标转换的数组（树型数组）
      let treeTmpList = list
      let treeTmpData = treeData
      if (treeTmpList.length !== 0) {
        for (let i = 0; i < treeTmpData.length; i++) {
          for (let j = 0; j < treeTmpList.length; j++) {
            if (treeTmpData[i].deptId === treeTmpList[j].parentId) {
              let obj = {
                title: treeTmpList[j].deptName,
                expand: true,
                deptId: treeTmpList[j].deptId,
                parentId: treeTmpList[j].parentId,
                children: []
              }
              treeTmpData[i].children.push(obj)
              treeTmpList.splice(j, 1)
              j--
            }
          }
          this.handleTreeData(treeTmpList, treeTmpData[i].children)
        }
      }
      return treeTmpData
    },
    formatForCascader (list) {
      const self = this
      for (let item of list) {
        item.label = item.title
        item.value = item.deptId
        if (item.children && item.children.length > 0) {
          self.formatForCascader(item.children)
        }
      }
    },
    // 角色列表
    getRoleSelectList () {
      roleManagementApi.getAllValidRoles().then(res => {
        const self = this
        self.roleSelectList = []
        let list = res.data.rows || []
        if (list && list.length > 0) {
          for(const value of list){
            let param = {
              roleId: value.roleId,
              roleName: value.roleName
            }
            self.roleSelectList.push(param)
          }
        }
      }).catch(err => {
        console.log('err', err)
        // self.$Message.error(message['1001']);
      })
    },
    // 修改密码Modal
    changePwdCancelHandle () {
      this.showChangePwdFlg = false;
      this.$refs.account.resetFields();
    },
    changePwdConfirmHandle () {
      const self = this;
      this.$refs.account.validate((valid) => {
        if (valid) {
          this.changePwd_loading = true;
          let param = {
            account: self.account.account,
            password: self.account.newPwd
          }
          userManagementApi.changeUserPwd(param)
            .then(res => {
              self.$Message.success('密码修改成功');
              self.changePwd_loading = false;
              self.showChangePwdFlg = false;
            })
            .catch(err => {
              console.log('err', err);
              self.changePwd_loading = false;
              // self.$Message.error(message['1001']);
            });
        }
      });
    }
  }
}
</script>

<style>

</style>
