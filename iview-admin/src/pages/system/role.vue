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
              <FormItem prop="user">
                <Input type="text" v-model.trim="filter.roleName" placeholder="角色名" clearable style="width:300px;"/>
              </FormItem>
            </Form>
          </Row>
          <Row>
            <Button type="primary" :loading="loading" @click="doQuery">查询</Button>
            <Button class="margin-left-10" type="default" @click="reset" >重置</Button>
          </Row>
        </Row>
      </Panel>
    </Collapse>
    <Card class="margin-top-10">
      <Row>
        <ButtonGroup class="margin-bottom-10">
          <!--<Button type="success" @click="addOrEditRoleFlg = true">新增</Button>-->
          <button-custom :buttonStyle="addObj.type" :buttonText="addObj.text" :partType="addObj.partType" @click.native="addOrEditRoleFlg = true" />
          <button-custom :buttonStyle="deleteObj.type" :buttonText="deleteObj.text" :partType="deleteObj.partType" @click.native="deleteBatch" />
          <!--<Button type="warning" @click="deleteBatch">删除</Button>-->
        </ButtonGroup>
        <Table :columns="tableTitle" :loading="tableLoading" :data="tableData" @on-selection-change="changeSelect"></Table>
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
          <FormItem label="角色类型：" prop="roleType">
            <Select v-model="role.roleType" style="width:300px;" placeholder="请选择角色类型" clearable filterable :transfer="true">
              <Option v-for="(item,index) in roleTypeList" :value="item.roleType" :key="index">{{ item.roleTypeName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="锁定状态：" prop="locked">
            <Select v-model="role.locked" style="width:300px;" placeholder="请选择锁定状态" clearable filterable :transfer="true">
              <Option v-for="(item,index) in lockedStatusList" :value="item.locked" :key="index">{{ item.lockedStatus }}</Option>
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
        <div class="menu-tree">
          <div v-for="(item) in menuTree" :key="item.menuId">
            <div v-if="!item.parentId && item.children && item.children.length > 0">
              <span>{{item.menuName}}</span>
              <div v-for="(childItem) in item.children" :key="childItem.menuId">
                <div v-if="!childItem.children || childItem.children.length === 0">
                  <Row class="child-item-menu-name">
                    <Col span="8" offset="1">
                      <span class="menu-tree-title">{{childItem.menuName}}</span>
                    </Col>
                    <Col span="9">
                      <input type="checkbox" :checked="childItem.authorizeLevel === 1" @change="changeVisitChecked" @click="changeVisitInfo(childItem)">
                      <label>访问权限</label>
                      <input type="checkbox" :checked="childItem.authorizeLevel === 2" @change="changeChecked" @click="changeVisitInfo(childItem)">
                      <label>管理权限</label>
                    </Col>
                    <Col span="5">
                      <Button type="dashed" size="small" v-if="childItem.authorizeLevel === 2" @click="getMenuPartAuthDetail(childItem.menuId)">部件权限</Button>
                    </Col>
                  </Row>
                </div>
                <div v-if="childItem.children && childItem.children.length > 0">
                  <Row class="child-item-menu-name">
                    <Col span="8" offset="1">
                      <span>{{childItem.menuName}}</span>
                    </Col>
                  </Row>
                  <div v-for="(grandChildItem) in childItem.children" :key="grandChildItem.menuId">
                    <Row class="grand-child-item-menu-name">
                      <Col span="8" offset="2">
                        <span class="menu-tree-title">{{grandChildItem.menuName}}</span>
                      </Col>
                      <Col span="9">
                      <input type="checkbox" :checked="grandChildItem.authorizeLevel === 1" @change="changeVisitChecked" @click="changeVisitInfo(grandChildItem)">
                      <label>访问权限</label>
                      <input type="checkbox" :checked="grandChildItem.authorizeLevel === 2" @change="changeChecked" @click="changeVisitInfo(grandChildItem)">
                      <label>管理权限</label>
                      </Col>
                      <Col span="5">
                        <Button type="dashed" size="small" v-if="grandChildItem.authorizeLevel === 2" @click="getMenuPartAuthDetail(grandChildItem.menuId)">部件权限</Button>
                      </Col>
                    </Row>
                  </div>
                </div>
              </div>
            </div>
            <div v-if="!item.parentId && (!item.children || item.children.length === 0)">
              <Row>
                <Col span="10">
                  <span class="menu-tree-title">{{item.menuName}}</span>
                </Col>
                <Col span="9">
                  <input type="checkbox" :checked="item.authorizeLevel === 1" @change="changeVisitChecked" @click="changeVisitInfo(item)">
                <label>访问权限</label>
                  <input type="checkbox" :checked="item.authorizeLevel === 2" @change="changeChecked" @click="changeVisitInfo(item)">
                <label>管理权限</label>
                </Col>
                <Col span="5">
                  <Button type="dashed" size="small" v-if="item.authorizeLevel === 2" @click="getMenuPartAuthDetail(item.menuId)">部件权限</Button>
                </Col>
              </Row>
            </div>
          </div>
        </div>
        <div slot="footer">
          <Button type="text" @click="menuCfgCancelHandle">取消</Button>
          <Button type="primary" :loading="saveLoading" @click="menuCfgConfirmHandle">保存</Button>
        </div>
      </Modal>
      <Modal v-model="showUpdatePartAuthorizationFlg" :closable='false' :mask-closable=false :width="780" >
        <h3 slot="header" style="color:#2D8CF0">配置部件权限</h3>
        <div v-if="!partsAuthList || partsAuthList.length === 0">
          <span>该菜单未设置部件信息</span>
        </div>
        <div v-for="(item) in partsAuthList " :key="item.partId">
          <Row style="display: flex;align-items: center;margin-top: 10px;">
            <Col span="6">
              <span>组件Id：{{item.cmpId}}</span>
            </Col>
            <Col span="5">
              <span>组件类型：{{item.cmpType}}</span>
            </Col>
            <Col span="5">
              <span>备注：{{item.remark}}</span>
            </Col>
            <Col span="8">
              <span>权限类型：</span>
              <Select v-model="item.partAuthType" style="width:150px;" placeholder="请选择锁定状态" filterable :transfer="true">
                <Option v-for="(partItem,index) in partAuthTypeList" :value="partItem.partAuthType" :key="index">{{ partItem.partAuthTypeName }}</Option>
              </Select>
            </Col>
          </Row>
        </div>
        <div slot="footer">
          <Button type="text" @click="cancelPartAuthorization">取消</Button>
          <Button type="primary" :loading="partAuthLoading" @click="submitPartAuthorization">确定</Button>
        </div>
      </Modal>
    </Card>
  </div>
</template>

<script>
import { getMenuId } from '@/libs/util'
import * as roleManagementApi from '@/api/role'
import * as menuManagementApi from '@/api/menu'
// import * as sysUserManagementApi from '@/api/sysUser'
import * as partsManagementApi from '@/api/partsManagement'
import { getDepartmentList } from '@/api/organizationalManagement'
// import { list } from '../../api/menu'
import { getMenuPartAuth } from '@/api/sysUser'
import ButtonCustom from '@/components/button-custom/button-custom.vue'
export default {
  components: {
    ButtonCustom
  },
  data () {
    return {
      value1: '1',
      addOrEditRoleFlg: false,
      loading: false,
      showUpdateRoleMenuAuthorizationFlg: false,
      showUpdatePartAuthorizationFlg: false,
      saveLoading: false,
      partAuthLoading: false,
      // menuTreeOrig: [],
      menuTree: [],
      currentRow: {},
      filter: {
        // roleName: '',
        // deptId: null,
        // orgRegionCompanyPkid: null,
        pageNo: 1,
        pageSize: 10
      },
      role: {
        deptId: [],
        roleName: '',
        roleType: null,
        locked: null,
        remark: ''
      },
      save_loading: false,
      inforValidate: {
        roleName: [
          { required: true, message: '请输入角色名', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '请选择部门', trigger: 'change', type: 'array' }
        ],
        roleType: [
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
              h(ButtonCustom, {
                props: {
                  buttonStyle: 'primary',
                  btnSize: 'small',
                  btnGhost: true,
                  partType: this.detailObj.partType,
                  buttonText: '详情'
                },
                nativeOn: {
                  click: () => {
                    this.queryRoleDetail(params.row.roleId)
                  }
                }
              }, '详情'),
              h(ButtonCustom, {
                props: {
                  buttonStyle: 'success',
                  btnSize: 'small',
                  btnGhost: true,
                  partType: this.menuAuthObj.partType,
                  buttonText: '菜单权限'
                },
                style: {
                  marginLeft: '10px'
                },
                nativeOn: {
                  click: () => {
                    this.changeMenuPermissions(params.row.roleId)
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
          roleType: 1,
          roleTypeName: '业务角色'
        },
        {
          roleType: 2,
          roleTypeName: '管理角色'
        },
        {
          roleType: 3,
          roleTypeName: '系统内置角色'
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
      deptIdList: [],
      currentInfo: {},
      partsAuthList: [],
      partAuthTypeList: [
        {
          partAuthType: 1,
          partAuthTypeName: '禁用'
        },
        {
          partAuthType: 2,
          partAuthTypeName: '只读'
        },
        {
          partAuthType: 3,
          partAuthTypeName: '编辑'
        },
        {
          partAuthType: 4,
          partAuthTypeName: '显示'
        },
        {
          partAuthType: 5,
          partAuthTypeName: '隐藏'
        },
        {
          partAuthType: 6,
          partAuthTypeName: '挂起'
        },
        {
          partAuthType: 7,
          partAuthTypeName: '激活'
        }
      ],
      tableLoading: false,
      addObj: {
        type: 'success',
        text: '新增',
        partType: 4,
        partId: 'role_add'
      },
      deleteObj: {
        type: 'warning',
        text: '删除',
        partType: 4,
        partId: 'role_delete'
      },
      detailObj: {
        type: 'primary',
        text: '详情',
        partType: 4,
        partId: 'role_detail'
      },
      menuAuthObj: {
        type: 'success',
        text: '菜单权限',
        partType: 4,
        partId: 'role_menu_auth'
      }
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
      self.listMenuTree()
      self.getMenuId(self.$store.state.app.menuList, self.$route.meta.title)
    },
    getMenuId (list, name) {
      if (getMenuId(list, name)) {
        localStorage.setItem('menuId', getMenuId(list, name))
      }
      console.log('menuId:', localStorage.getItem('menuId'))
      const menuId = localStorage.getItem('menuId')
      this.getPagePartAuth(menuId)
    },
    // 获取页面部件权限
    getPagePartAuth (menuId) {
      getMenuPartAuth(menuId).then(res => {
        console.log('getMenuPartDetail res:', res)
        const partAuthList = res.data.rows || []
        for (const value of partAuthList) {
          if (value.cmpId === this.addObj.partId) {
            this.addObj.partType = value.partAuthType
          }
          if (value.cmpId === this.deleteObj.partId) {
            this.deleteObj.partType = value.partAuthType
          }
          if (value.cmpId === this.detailObj.partId) {
            this.detailObj.partType = value.partAuthType
          }
          if (value.cmpId === this.menuAuthObj.partId) {
            this.menuAuthObj.partType = value.partAuthType
          }
        }
      }).catch(err => {
        console.log('err', err)
      })
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
    // 重置
    reset () {
      const self = this
      self.filter.roleName = ''
      self.filter.deptId = []
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
        roletype: self.role.roleType,
        locked: self.role.locked
      }
      if (self.role.roleId) {
        param.roleId = self.role.roleId
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
      self.deptIdList = []
      self.tableLoading = true
      // 调用获取角色信息接口
      roleManagementApi.getRoleInfo(pkid).then(res => {
        self.addOrEditRoleFlg = true
        self.tableLoading = false
        self.foreachAndSearchDeptParentNode(self.cascaderData, res.data.row.deptId)
        if (res.data.row) {
          self.role.deptId = self.deptIdList
          self.role.roleName = res.data.row.roleName
          self.role.roleType = res.data.row.roletype
          self.role.roleId = res.data.row.roleId
          if (res.data.row.remark) {
            self.role.remark = res.data.row.remark
          }
          if (res.data.row.locked) {
            self.role.locked = 1
          }
          if (!res.data.row.locked) {
            self.role.locked = 0
          }
        }
      }).catch(err => {
        console.log('err', err)
        // self.$Message.error(message['1001']);
      })
    },
    // 点击菜单权限
    changeMenuPermissions (pkid) {
      const self = this
      self.tableLoading = true
      self.listMenuTree()
      roleManagementApi.getRoleInfo(pkid).then(res => {
        self.tableLoading = false
        self.showUpdateRoleMenuAuthorizationFlg = true
        if (res.data.row) {
          self.role.menuIds = res.data.row.menuIds || []
          self.role.roleId = res.data.row.roleId
        }
        self.matchMenuPermissions(self.menuTree, self.role.menuIds)
      }).catch(err => {
        console.log('err', err)
        // self.$Message.error(message['1001']);
      })
    },
    // 匹配菜单权限
    matchMenuPermissions (menuTree, roleMenuTree) {
      const self = this
      if (menuTree && roleMenuTree) {
        for (const menuItem of menuTree) {
          menuItem.authorizeLevel = 0
          for (const roleMenuItem of roleMenuTree) {
            if (menuItem.menuId === roleMenuItem.menuId) {
              console.log('roleMenuItem.menuName', roleMenuItem.menuName)
              menuItem.authorizeLevel = roleMenuItem.authorizeLevel
            }
          }
          if (menuItem.children && menuItem.children.length > 0) {
            self.matchMenuPermissions(menuItem.children, roleMenuTree)
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
    },
    menuCfgConfirmHandle () {
      console.log('menuCfgConfirmHandle', this.menuTree)
      const self = this
      self.saveLoading = true
      let sysRoleUpdateMenuAuthParam = {}
      if (self.role.roleId) {
        sysRoleUpdateMenuAuthParam.roleId = self.role.roleId
      }
      sysRoleUpdateMenuAuthParam.sysRoleMenuParams = []
      for (const item of self.menuTree) {
        if (item.authorizeLevel || item.children.length > 0) {
          let param = {
            authorizeLevel: item.authorizeLevel,
            menuId: item.menuId
          }
          sysRoleUpdateMenuAuthParam.sysRoleMenuParams.push(param)
        }
        if (item.children && item.children.length > 0) {
          for (const childItem of item.children) {
            if (childItem.authorizeLevel || childItem.children.length > 0) {
              let childParam = {
                authorizeLevel: childItem.authorizeLevel,
                menuId: childItem.menuId
              }
              sysRoleUpdateMenuAuthParam.sysRoleMenuParams.push(childParam)
            }
            if (childItem.children && childItem.children.length > 0) {
              for (const grandChildItem of childItem.children) {
                if (grandChildItem.authorizeLevel) {
                  let grandChildParam = {
                    authorizeLevel: grandChildItem.authorizeLevel,
                    menuId: grandChildItem.menuId
                  }
                  sysRoleUpdateMenuAuthParam.sysRoleMenuParams.push(grandChildParam)
                }
              }
            }
          }
        }
      }
      console.log('sysRoleUpdateMenuAuthParam', sysRoleUpdateMenuAuthParam)
      roleManagementApi.updateMenuAuthorize(sysRoleUpdateMenuAuthParam)
        .then(function (response) {
          console.log('getMenuPartAuthDetail response:', response)
          self.saveLoading = false
          self.showUpdateRoleMenuAuthorizationFlg = false
        })
        .catch(function (error) {
          console.log('partsManagementApi.getMenuPartDetail→error:', error)
        })
    },
    // 获取全部菜单（树型）
    listMenuTree () {
      const self = this
      menuManagementApi.listMenuTree()
        .then(function (response) {
          self.menuTree = JSON.parse(JSON.stringify(response.data.rows))
        })
        .catch(function (error) {
          console.log('menuManagementApi.listMenuTree→error:', error)
          // self.$Message.error('系统错误,请联系管理员!')
        })
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
    changeVisitChecked (e) {
      const self = this
      const menuId = this.currentInfo.menuId
      const checked = e.target.checked
      if (checked) {
        self.searchAndChangeMenuAuthority(self.menuTree, menuId, 1)
      }
      if (!checked) {
        self.searchAndChangeMenuAuthority(self.menuTree, menuId, 0)
      }
    },
    changeChecked (e) {
      const self = this
      const menuId = this.currentInfo.menuId
      const checked = e.target.checked
      if (checked) {
        self.searchAndChangeMenuAuthority(self.menuTree, menuId, 2)
      }
      if (!checked) {
        self.searchAndChangeMenuAuthority(self.menuTree, menuId, 0)
      }
    },
    changeVisitInfo (item) {
      console.log('changeVisitInfo', item)
      this.currentInfo = item
    },
    // 搜索菜单并修改权限
    searchAndChangeMenuAuthority (menuTree, menuId, authority) {
      console.log('authority', authority)
      const self = this
      for (const value of menuTree) {
        if (value.menuId === menuId) {
          value.authorizeLevel = authority
        }
        if (value.children && value.children.length > 0) {
          self.searchAndChangeMenuAuthority(value.children, menuId, authority)
        }
      }
      self.menuTree = [...self.menuTree]
      console.log('self.menuTree', self.menuTree)
    },
    // 部件权限详情
    getMenuPartAuthDetail (menuId) {
      console.log('getMenuPartAuthDetail menuId:', menuId)
      const self = this
      self.partsAuthList = []
      partsManagementApi.getMenuPartDetail(menuId)
        .then(function (response) {
          console.log('getMenuPartAuthDetail response:', response)
          const partAuthList = response.data.rows || []
          self.matchMenuPartAuthDetail(partAuthList, menuId)
        })
        .catch(function (error) {
          console.log('partsManagementApi.getMenuPartDetail→error:', error)
        })
    },
    // 匹配菜单部件权限
    matchMenuPartAuthDetail (partAuthList, menuId) {
      console.log('partAuthList:', partAuthList)
      const self = this
      let param = {
        menuId: menuId,
        roleId: self.role.roleId
      }
      roleManagementApi.getMenuPartAuthorize(param).then(res => {
        self.showUpdatePartAuthorizationFlg = true
        self.showUpdateRoleMenuAuthorizationFlg = false
        const partAuthMatchList = res.data.rows || []
        for (const item of partAuthList) {
          let param = {
            cmpId: item.cmpId,
            // cmpType: item.cmpType,
            createTime: item.createTime,
            createUserId: item.createUserId,
            menuId: item.menuId,
            partId: item.partId,
            remark: item.remark,
            partAuthType: null
          }
          if (item.cmpType === 1) {
            param.cmpType = '按钮组件'
          }
          for (const value of partAuthMatchList) {
            if (item.partId === value.partId) {
              param.partAuthType = value.partAuthType
            }
          }
          self.partsAuthList.push(param)
        }
        console.log('self.partsAuthList', self.partsAuthList)
        console.log('matchMenuPartAuthDetail res', res)
      }).catch(err => {
        console.log('err', err)
        // self.$Message.error(message['1001']);
      })
    },
    cancelPartAuthorization () {
      const self = this
      self.partAuthLoading = true
      self.showUpdatePartAuthorizationFlg = false
      self.showUpdateRoleMenuAuthorizationFlg = true
      self.partAuthLoading = false
    },
    submitPartAuthorization () {
      const self = this
      self.partAuthLoading = true
      console.log('self.partsAuthList', self.partsAuthList)
      let SysRoleMenuPartSaveParam = {}
      if (self.partsAuthList && self.partsAuthList.length > 0) {
        SysRoleMenuPartSaveParam.menuId = self.partsAuthList[0].menuId
      }
      console.log('self.role.roleId', self.role.roleId)
      if (self.role.roleId) {
        SysRoleMenuPartSaveParam.roleId = self.role.roleId
      }
      SysRoleMenuPartSaveParam.menuPartList = []
      for (const item of self.partsAuthList) {
        if (item.partAuthType) {
          let param = {
            partAuthType: item.partAuthType,
            partId: item.partId
          }
          SysRoleMenuPartSaveParam.menuPartList.push(param)
        }
      }
      roleManagementApi.updateMenuPartAuthorize(SysRoleMenuPartSaveParam)
        .then(function (response) {
          self.showUpdatePartAuthorizationFlg = false
          self.showUpdateRoleMenuAuthorizationFlg = true
          self.partAuthLoading = false
        })
        .catch(function (error) {
          console.log('partsManagementApi.getMenuPartDetail→error:', error)
        })
    }
  }
}
</script>

<style>
  .child-item-menu-name {
    /*margin-left: 20px;*/
    margin-top: 8px;
    display: flex;
    align-items: center;
  }
  .grand-child-item-menu-name {
    /*margin-left: 40px;*/
    margin-top: 5px;
    display: flex;
    align-items: center;
  }
  .menu-tree {
    padding: 10px 10px 20px 10px;
  }
  .menu-tree-title {
    font-weight: bold;
  }
</style>
