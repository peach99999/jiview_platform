<template>
  <div>
    <Collapse v-model="value1">
      <Panel name="1">
        <Row slot="content">
          <Row>
            <Form ref="filter" :model="filter" inline @keydown.enter.native="doQuery">
              <FormItem>
                <Cascader :data="cascaderData" v-model="filter.deptId" change-on-select ref="cascaderParentIds" placeholder="请选择部门"></Cascader>
              </FormItem>
              <FormItem prop="user">
                <Input type="text" v-model.trim="filter.roleName" placeholder="角色名" clearable/>
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
          <Button type="success" icon="ios-add-circle-outline" @click="addOrEditRoleFlg = true">新增</Button>
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

      <Modal v-model="addOrEditRoleFlg" scrollable title="新增/编辑角色" @on-visible-change="restTest" :mask-closable="false">
        <Form ref="role" :label-width="120" :model="role" inline :rules="inforValidate">
          <FormItem label="所属部门：" prop="deptId">
            <Cascader :data="cascaderData" v-model="role.deptId" change-on-select ref="cascaderParentIds" placeholder="请选择部门"  style="width:300px;"></Cascader>
          </FormItem>
          <FormItem label="角色名：" prop="roleName">
            <Input v-model.trim="role.roleName" style="width:300px;" placeholder="请输入角色名"/>
          </FormItem>
          <FormItem label="角色类型：" prop="roleTypeId">
            <Select v-model="role.roleTypeId" style="width:300px;" placeholder="请选择角色类型" clearable filterable :transfer="true">
              <Option v-for="(item,index) in roleTypeList" :value="item.roleTypeId" :key="index">{{ item.roleTypeName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="锁定状态：" prop="locked">
            <Select v-model="role.locked" style="width:300px;" placeholder="请选择锁定状态" clearable filterable :transfer="true">
              <Option v-for="(item,index) in lockedStatusList" :value="item.lockedId" :key="index">{{ item.lockedStatus }}</Option>
            </Select>
          </FormItem>
          <FormItem label="备注：" prop="remark">
            <Input v-model.trim="role.remark" style="width:300px;" placeholder="请输入备注"/>
          </FormItem>
        </Form>
        <div slot="footer">
            <Button type="text" @click="saveOrUpdateHandle">取消</Button>
            <Button type="primary" :loading="save_loading" @click="saveOrUpdateConfirmHandle">保存</Button>
        </div>
      </Modal>
      <Modal v-model="showUpdateRoleMenuAuthorizationFlg" :closable='true' :mask-closable=false :width="500">
          <h3 slot="header" style="color:#2D8CF0">配置菜单权限</h3>
          <Tree :data="menuTree" show-checkbox multiple ref="menuTree"></Tree>
          <div slot="footer">
              <Button type="text" @click="menuCfgCancelHandle">取消</Button>
              <Button type="primary" :loading="saveLoading" @click="menuCfgConfirmHandle">保存</Button>
          </div>
      </Modal>
    </Card>
  </div>
</template>

<script>
// import * as util from '@/libs/util'
import * as roleManagementApi from '@/api/role'
import * as menuManagementApi from '@/api/menu'
export default {
  data () {
    return {
      value1: '1',
      addOrEditRoleFlg: false,
      loading: false,
      showUpdateRoleMenuAuthorizationFlg: false,
      saveLoading: false,
      menuTreeOrig: [],
      menuTree: [],
      currentRow: {},
      filter: {
        // roleName: '',
        // deptId: null,
        // orgRegionCompanyPkid: null,
        pageNo: 1,
        pageSize: 10
      },
      role: {},
      save_loading: false,
      inforValidate: {
        roleName: [
          { required: true, message: '请输入角色名', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '请选择部门', trigger: 'change', type: 'array' }
        ],
        roleTypeId: [
          { required: true, message: '请选择角色类型', trigger: 'change', type: 'number' }
        ],
        locked: [
          { required: true, message: '请选择锁定状态', trigger: 'change', type: 'number' }
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
          width: 200,
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
                    this.queryRoleDetail(params.row.roleId)
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
                    const self = this
                    self.currentRow = params.row
                    roleManagementApi.getRoleInfo(self.currentRow.pkid)
                      .then(response => {
                        self.showUpdateRoleMenuAuthorizationFlg = true
                        self.menuTree = JSON.parse(JSON.stringify(self.menuTreeOrig))
                        for (let i in self.menuTree) {
                          self.$set(self.menuTree[i], 'expand', true)
                        }
                        let roleMenus = (response.data.row && response.data.row.menuPkids) || []
                        self.restoreMenuCheckedStatus(roleMenus)
                      })
                      .catch(err => {
                        console.log('err', err)
                      })
                  }
                }
              }, '菜单权限')
            ])
          }
        },
        {
          title: '部门名称',
          key: 'deptName',
          align: 'center',
          width: 510,
          render: function (h, params) {
            return h('div', params.row.deptName ? params.row.deptName : '----')
          }
        },
        {
          title: '角色名',
          key: 'roleName',
          align: 'center',
          width: 500,
          render: function (h, params) {
            return h('div', params.row.roleName ? params.row.roleName : '----')
          }
        }
      ],
      tableData: [],
      pkidList: [],
      selected: [],
      total: 0,
      regionCompanyList: [],
      cascaderData: [],
      roleTypeList: [
        {
          roleTypeId: 1,
          roleTypeName: '业务角色'
        },
        {
          roleTypeId: 2,
          roleTypeName: '管理角色'
        },
        {
          roleTypeId: 3,
          roleTypeName: '系统内置角色'
        }
      ],
      lockedStatusList: [
        {
          lockedId: 1,
          lockedStatus: '锁定'
        },
        {
          lockedId: 0,
          lockedStatus: '激活'
        }
      ]
    }
  },
  mounted () {
    const self = this
    // if (this.$store.state.app.listPageParams.has(this.$route.name)) {
    // this.filter = this.$store.state.app.listPageParams.get(this.$route.name);
    // }
    self.init()
  },
  methods: {
    init () {
      const self = this
      self.doQuery()
      self.listMenuTree()
    },
    listForInit () {
      const self = this
      self.loading = true
      // self.$store.dispatch('saveListPageParams', {path: this.$route.name, pars: this.filter});
      // 调用角色列表接口
      let param = {
        pageNo: self.filter.pageNo,
        pageSize: self.filter.pageSize
      }
      if (self.filter.deptId) {
        let index = self.filter.deptId.length - 1
        param.deptId = self.filter.deptId[index]
      }
      if (self.filter.roleName) {
        param.roleName = self.filter.roleName
      }
      console.log('Query Param:', param)
      roleManagementApi.getRoleList(param).then(res => {
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
    // listMenuTree () {
    // const self = this;
    // menuManagementApi.listMenuTree()
    // .then(function (response) {
    // if (response.data.rows) {
    // self.menuTreeOrig = JSON.parse(JSON.stringify(response.data.rows));
    // }
    // })
    // .catch(function (error) {
    // console.log(error);
    // // self.$Message.error('系统错误,请联系管理员!');
    // });
    // },
    // 重置
    reset () {
      const self = this
      self.filter.roleName = ''
      self.filter.deptId = ''
      // if (!self.regionCompanyFlag) {
      // self.$refs.companyObj.clearSingleSelect();
      // }
    },
    // 新增或更新保存
    saveOrUpdateConfirmHandle () {
      const self = this
      self.$refs.role.validate((valid) => {
        if (valid) {
          this.saveOrUpdateRole()
        }
      })
    },
    saveOrUpdateRole () {
      const self = this
      self.save_loading = true
      console.log('saveOrUpdateRole role', self.role)
      let index = self.role.deptId.length - 1
      let param = {
        deptId: self.role.deptId[index],
        remark: self.role.remark,
        roleName: self.role.roleName,
        roletype: self.role.roleTypeId,
        locked: self.role.locked
      }
      roleManagementApi.updateRoleInfo(param)
        .then(res => {
          self.save_loading = false
          if (self.role.pkid) {
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
      this.$refs.role.resetFields()
      this.role = {}
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
      self.addOrEditRoleFlg = true
      // 调用获取角色信息接口
      roleManagementApi.getRoleInfo(pkid).then(res => {
        self.role = res.data.row || []
        console.log('queryRoleDetail self.role', self.role)
      }).catch(err => {
        console.log('err', err)
        // self.$Message.error(message['1001']);
      })
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
            self.pkidList.push(i.roleId)
          }
          let params = {
            roleIdList: self.pkidList
          }
          roleManagementApi.removeRoleInfo(params).then(res => {
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
    // 配置菜单权限
    menuCfgCancelHandle () {
      this.showUpdateRoleMenuAuthorizationFlg = false
      this.menuTree = []
    },
    menuCfgConfirmHandle () {
      const self = this
      let checkedNodes = self.$refs.menuTree.getCheckedNodes()
      let menuPkids = []
      for (let i in checkedNodes) {
        menuPkids.push(checkedNodes[i].pkid)
      }
      let data = {
        pkid: self.currentRow.pkid,
        menuPkids: menuPkids
      }
      self.saveLoading = true
      roleManagementApi.updateRoleMenuAuthorization(data)
        .then(res => {
          self.$Message.success('配置成功')
          self.showUpdateRoleMenuAuthorizationFlg = false
          self.init()
          self.saveLoading = false
        })
        .catch(err => {
          console.log('err', err)
          self.saveLoading = false
          // self.$Message.error(message['1001']);
        })
    },
    restoreMenuCheckedStatus (roleMenus) {
      this.menuTree.forEach(menu => {
        // menu.expand = true
        for (let i in roleMenus) {
          if (roleMenus[i] === menu.pkid) {
            this.$set(menu, 'checked', true)
          }
        }
        for (let j in menu.children) {
        // menu.children[j].expand = true
          for (let k in roleMenus) {
            if (menu.children[j].pkid === roleMenus[k]) {
              this.$set(menu.children[j], 'checked', true)
            }
          }
          for (let m in menu.children[j].children) {
            for (let n in roleMenus) {
              if (menu.children[j].children[m].pkid === roleMenus[n]) {
                this.$set(menu.children[j].children[m], 'checked', true)
              }
            }
          }
        }
      })
    },
    // 部门Id下拉选内容
    listMenuTree (menuPkidsToExpand) {
      const self = this
      menuManagementApi.listMenuTree()
        .then(function (response) {
          self.menuTree = response.data.rows
          self.cascaderData = JSON.parse(JSON.stringify(self.menuTree))
          self.formatForCascader()
          // self.formatForMenuTree()
          // self.expandMenuTreeByIds(menuPkidsToExpand)
        })
        .catch(function (error) {
          console.log('menuManagementApi.listMenuTree→error:', error)
          // self.$Message.error('系统错误,请联系管理员!')
        })
    },
    formatForCascader () {
      const self = this
      for (let i in self.cascaderData) {
        self.cascaderData[i].label = self.cascaderData[i].menuName
        self.cascaderData[i].value = self.cascaderData[i].menuId
        if (self.cascaderData[i].children !== null) {
          for (let j in self.cascaderData[i].children) {
            self.cascaderData[i].children[j].label = self.cascaderData[i].children[j].menuName
            self.cascaderData[i].children[j].value = self.cascaderData[i].children[j].menuId
            if (self.cascaderData[i].children[j].children !== null) {
              for (let m in self.cascaderData[i].children[j].children) {
                let data = {}
                data.label = self.cascaderData[i].children[j].children[m].menuName
                data.value = self.cascaderData[i].children[j].children[m].menuId
                self.cascaderData[i].children[j].children[m] = data
              }
            } else {
              let data = {}
              data.label = self.cascaderData[i].children[j].menuName
              data.value = self.cascaderData[i].children[j].menuId
              self.cascaderData[i].children[j] = data
            }
          }
        } else {
          let data = {}
          data.label = self.cascaderData[i].menuName
          data.value = self.cascaderData[i].menuId
          self.cascaderData[i] = data
        }
      }
    }
  }
}
</script>
