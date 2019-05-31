<style lang="less">
</style>

<template>
  <div>
    <Row>
      <Card>
        <ButtonGroup class="margin-bottom-10">
          <Button type="info" icon="plus" @click="gotoSaveHandle()">
            新增菜单
          </Button>
          <Button type="error" icon="trash-a" @click="removeHandle()">
            删除勾选的菜单
          </Button>
        </ButtonGroup>
        <Row>
          <Col span="8">
            <Card>
              <Tree :data="menuTree" show-checkbox ref="menuTree" :render="renderContent"
                    @on-select-change="treeOnSelectChangeHandle"
                    @on-toggle-expand="treeOnToggleExpandHandle"
                    @on-check-change="treeOnCheckChangeHandle"></Tree>
              <Spin fix v-if="spinShow">
                  <Icon type="load-c" size=18 class="demo-spin-icon-load"></Icon>
                  <div>加载中...</div>
              </Spin>
            </Card>
          </Col>
          <Col span="15" offset="1">
            <Card>
              <Form ref="menu" :model="menu" :label-width="100" label-position="right">
                <FormItem label="父级菜单">
                  <Cascader :data="cascaderData" v-model="menu.parentPkids" change-on-select
                            ref="cascaderParentIds"></Cascader>
                </FormItem>
                <FormItem label="菜单名称" prop="title">
                  <Input v-model.trim="menu.title" placeholder="请输入" ref="txtMenuTitle"></Input>
                </FormItem>
                <FormItem label="菜单链接">
                  <Input v-model.trim="menu.url" placeholder="请输入"></Input>
                </FormItem>
                <FormItem label="菜单图标">
                  <Input v-model="menu.iconcls" placeholder="请输入"></Input>
                </FormItem>
                <FormItem label="菜单排序" prop="sortno">
                  <Input v-model.trim="menu.sortno" placeholder="请输入" ref="txtMenuSort"></Input>
                </FormItem>
              </Form>
              <div style="text-align: right">
                <Button @click="cancelSaveHandle" style="margin-right: 10px;">
                  取消
                </Button>
                <Button type="primary" :loading="saveLoading" @click="saveOrUpdateHandle">
                  保存
                </Button>
              </div>
            </Card>
          </Col>
        </Row>
      </Card>
    </Row>
  </div>
</template>

<script>
import * as util from '@/libs/util'
import * as menuManagementApi from '@/api/menu'
export default {
  data () {
    return {
      menuTree: [],
      menu: {
        pkid: null,
        parentPkids: [],
        title: null,
        url: null,
        iconcls: '',
        sortno: null,
        parentPkid: null,
        checked: false,
        selected: false,
        expand: false
      },
      cascaderData: [],
      saveLoading: false,
      menus: [],
      spinShow: false,
      lastSelectedNode: null
    }
  },
  computed: {},
  mounted () {
    const self = this
    self.init()
  },
  methods: {
    init (menuPkidsToExpand) {
      const self = this
      self.listMenu()
      self.listMenuTree(menuPkidsToExpand)
      self.cancelSaveHandle()
    },
    expandMenuTreeById (menuPkidToExpand) {
      const self = this
      self.expandMenuTreeByIds([menuPkidToExpand])
    },
    expandMenuTreeByIds (menuPkidsToExpand) {
      const self = this
      let expandMenuIds = self.getMenuIdsHierarchy(menuPkidsToExpand)
      if (menuPkidsToExpand) {
        self.menuTree.forEach((nodeLvl1) => {
          if (util.inArray(nodeLvl1.menuId, expandMenuIds)) {
            self.$set(nodeLvl1, 'expand', true)
          }
          if (nodeLvl1.children) {
            nodeLvl1.children.forEach((nodeLvl2) => {
              if (util.inArray(nodeLvl2.menuId, expandMenuIds)) {
                self.$set(nodeLvl2, 'expand', true)
              }
              if (nodeLvl2.children) {
                nodeLvl2.children.forEach((nodeLvl3) => {
                  if (util.inArray(nodeLvl3.menuId, expandMenuIds)) {
                    self.$set(nodeLvl3, 'expand', true)
                  }
                })
              }
            })
          }
        })
      }
    },
    // 获取菜单列表
    listMenu () {
      const self = this
      self.spinShow = true
      menuManagementApi.list()
        .then(function (response) {
          self.menus = response.data.rows
          self.spinShow = false
        })
        .catch(function (error) {
          console.log('menuManagementApi.list→error:', error)
          // self.$Message.error('系统错误,请联系管理员!')
          self.spinShow = false
        })
    },
    // 获取父级菜单下拉选内容
    listMenuTree (menuPkidsToExpand) {
      const self = this
      menuManagementApi.listMenuTree()
        .then(function (response) {
          self.menuTree = response.data.rows
          console.log('listMenuTree self.menuTree:', self.menuTree)
          self.cascaderData = JSON.parse(JSON.stringify(self.menuTree))
          self.formatForCascader()
          self.formatForMenuTree()
          self.expandMenuTreeByIds(menuPkidsToExpand)
        })
        .catch(function (error) {
          console.log('menuManagementApi.listMenuTree→error:', error)
          // self.$Message.error('系统错误,请联系管理员!')
        })
    },
    // 格式化树形菜单
    formatForMenuTree () {
      const self = this
      for (let i in self.menuTree) {
        self.menuTree[i].title = self.menuTree[i].menuName
        if (self.menuTree[i].children !== null) {
          for (let j in self.menuTree[i].children) {
            self.menuTree[i].children[j].title = self.menuTree[i].children[j].menuName
            if (self.menuTree[i].children[j].children !== null) {
              for (let m in self.menuTree[i].children[j].children) {
                // let data = {}
                self.menuTree[i].children[j].children[m].title = self.menuTree[i].children[j].children[m].menuName
                // self.menuTree[i].children[j].children[m] = data
              }
            } else {
              // let data = {}
              self.menuTree[i].children[j].title = self.menuTree[i].children[j].menuName
              // self.menuTree[i].children[j] = data
            }
          }
        } else {
          // let data = {}
          self.menuTree[i].title = self.menuTree[i].menuName
          // self.menuTree[i] = data
        }
      }
      console.log('formatForMenuTree menuTree:', self.menuTree)
    },
    // 格式化父级菜单下拉选内容
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
    },
    initCheckStatusForTree () {
      const self = this
      self.$refs.menuTree.getCheckedNodes().forEach((node) => {
        self.$set(node, 'checked', false)
      })
    },
    initSelectionForTree () {
      const self = this
      self.$refs.menuTree.getSelectedNodes().forEach((node) => {
        self.$set(node, 'selected', false)
      })
    },
    save (menu) {
      const self = this
      self.saveLoading = true
      menuManagementApi.save(menu)
        .then(function (response) {
          self.saveLoading = false
          self.$Message.success('菜单保存成功')
          self.initCheckStatusForTree()
          self.initSelectionForTree()
          self.init([menu.parentId])
        })
        .catch(function (error) {
          console.log('menuManagementApi.save→error:', error)
          self.saveLoading = false
          // self.$Message.error('系统错误,请联系管理员!');
        })
    },
    update (menu) {
      const self = this
      self.saveLoading = true
      menuManagementApi.update(menu)
        .then(function (response) {
          self.saveLoading = false
          self.$Message.success('菜单保存成功')
          self.initCheckStatusForTree()
          self.initSelectionForTree()
          self.init([menu.parentId])
        })
        .catch(function (error) {
          console.log('menuManagementApi.update→error:', error)
          self.saveLoading = false
          // self.$Message.error('系统错误,请联系管理员!');
        })
    },
    // 递归获取menuPkid层级
    recurseMenuId (menuPkid, menuArr) {
      const self = this
      for (let i in self.menus) {
        if (self.menus[i].menuId === menuPkid) {
          if (self.menus[i].parentId) {
            menuArr = [menuPkid, ...menuArr]
            return self.recurseMenuId(self.menus[i].parentId, menuArr)
          } else {
            menuArr = [menuPkid, ...menuArr]
          }
        }
      }
      return menuArr
    },
    // 获取单menuPkid的parentPkid层级数组
    getParentIdHierarchy (id) {
      const self = this
      let menuArr = self.getMenuIdHierarchy(id)
      menuArr.pop()
      return menuArr
    },
    // 获取单menuPkid层级数组
    getMenuIdHierarchy (id) {
      const self = this
      return self.recurseMenuId(id, [])
    },
    // 获取多menuPkid层级数组
    getMenuIdsHierarchy (ids) {
      const self = this
      let menuPkidsHierarchy = []
      if (ids) {
        for (let i = 0; i < ids.length; i++) {
          let menuPkidHierarchy = self.getMenuIdHierarchy(ids[i])
          menuPkidsHierarchy = menuPkidsHierarchy.concat(menuPkidHierarchy)
        }
      }
      return menuPkidsHierarchy
    },
    getSelectedNode () {
      const self = this
      let lastSelectedNode = self.$refs.menuTree.getSelectedNodes()
      return lastSelectedNode && lastSelectedNode.length > 0 && lastSelectedNode[0]
    },
    generateOrderNum () {
      const self = this
      let selectedNode = self.getSelectedNode()
      let parentPkid = selectedNode.pkid || 0
      let maxOrderNum = -1
      for (let i in self.menus) {
        if (self.menus[i].parentPkid === parentPkid && self.menus[i].sortno > maxOrderNum) {
          maxOrderNum = self.menus[i].sortno
        }
      }
      return ++maxOrderNum
    },
    gotoSaveHandle () {
      console.log('新增菜单', this.menu)
      const self = this
      let selectedNode = self.getSelectedNode()
      let selectedMenuId = selectedNode && selectedNode.pkid
      self.expandMenuTreeById(selectedMenuId)
      self.menu = {
        pkid: -1,
        title: '',
        url: '',
        iconcls: '',
        sortno: self.generateOrderNum(),
        parentPkids: self.getMenuIdHierarchy(selectedMenuId),
        type: 1
      }
      self.initCheckStatusForTree()
      self.initSelectionForTree()
      self.$refs.txtMenuTitle.focus()
    },
    cancelSaveHandle () {
      const self = this
      self.menu = {}
      let selectedNodes = self.$refs.menuTree.getSelectedNodes() || []
      Object.assign(self.menu, selectedNodes[0] || {})
      self.menu.parentPkids = self.getParentIdHierarchy(self.menu.menuId)
    },
    validateForSaveOrUpdate (menu) {
      const self = this
      if (menu.parentPkids && menu.parentPkids.length >= 3) {
        self.$Message.warning('菜单最多为三级')
        self.$refs.cascaderParentIds.handleFocus()
        return false
      }
      if (menu.title === null || menu.title === undefined || menu.title === '') {
        self.$Message.warning('请输入菜单名称')
        self.$refs.txtMenuTitle.focus()
        return false
      }
      if (menu.sortno === null || menu.sortno === undefined || menu.sortno === '') {
        self.$Message.warning('请输入菜单排序')
        self.$refs.txtMenuSort.focus()
        return false
      }
      return true
    },
    saveOrUpdateHandle () {
      const self = this
      if (!self.validateForSaveOrUpdate(self.menu)) {
        return
      }
      let menu = {
        iconcls: self.menu.iconcls ? self.menu.iconcls : null,
        menuId: (self.menu.menuId && self.menu.menuId > 0) ? self.menu.menuId : null,
        menuName: self.menu.title,
        parentId: self.menu.parentPkids.length > 0 ? self.menu.parentPkids[self.menu.parentPkids.length - 1] : 0,
        sortno: self.menu.sortno,
        url: self.menu.url
      }
      if (menu.menuId) {
        self.update(menu)
      } else {
        self.save(menu)
      }
    },
    treeOnSelectChangeHandle (selectedNodes) {
      console.log('treeOnSelectChangeHandle→selectedNodes:', selectedNodes)
      const self = this
      self.menu = {}
      if (!selectedNodes || !selectedNodes.node) {
        self.menu = {}
      } else {
        Object.assign(self.menu, selectedNodes.node)
        self.menu.parentPkids = self.getParentIdHierarchy(self.menu.menuId)
      }
      console.log('点击查看菜单详情：', this.menu)
    },
    treeOnToggleExpandHandle (expandMenuTreeByIds) {
      console.log('treeOnToggleExpandHandle→expandMenuTreeByIds:', expandMenuTreeByIds)
    },
    treeOnCheckChangeHandle (checkedNodes) {
      console.log('treeOnCheckChangeHandle→checkedNodes:', checkedNodes)
    },
    remove (menuPkidsToRemove, menuPkidsToExpand) {
      const param = {
        pkids: menuPkidsToRemove
      }
      const self = this
      self.$Modal.confirm({
        title: '提示',
        content: '<h2>您确定要删除这条数据吗?</h2>',
        loading: true,
        onOk: () => {
          menuManagementApi.remove(param)
            .then(function (response) {
              if (response.data.code) {
                self.$Message.warning(response.data.msg)
                self.$Modal.remove()
              } else {
                self.$Modal.remove()
                self.$Message.success('删除成功')
                self.initCheckStatusForTree()
                self.initSelectionForTree()
                self.init(menuPkidsToExpand)
              }
            })
            .catch(function (error) {
              console.log('menuManagementApi.remove→error:', error)
              self.$Modal.remove()
              // self.$Message.error('系统错误,请联系管理员!');
            })
        }
      })
    },
    removeHandle () {
      const self = this
      let checkedMenuList = self.$refs.menuTree.getCheckedNodes()
      let menuPkidsToRemove = []
      let menuPkidsToExpand = []
      if (checkedMenuList.length < 1) {
        self.$Message.warning('请勾选要删除的菜单')
        return
      }
      console.log('checkedMenuList:', checkedMenuList)
      for (let i = 0; i < checkedMenuList.length; i++) {
        let row = checkedMenuList[i]
        console.log('checkedMenuList row:', row)
        menuPkidsToRemove.push(row.menuId)
        menuPkidsToExpand.push(row.parentId)
      }
      console.log('menuPkidsToRemove:', menuPkidsToRemove)
      console.log('menuPkidsToExpand:', menuPkidsToExpand)
      if (menuPkidsToRemove && menuPkidsToRemove.length > 0) {
        self.remove(menuPkidsToRemove, menuPkidsToExpand)
      }
    },
    renderContent (h, { root, node, data }) {
      console.log('data:', data)
      return h('span', {
        style: {
          display: 'inline-block',
          width: '100%'
        }
      }, [
        h('span', [
          h('Icon', {
            style: {
              marginRight: '8px'
            }
          }),
          h('span', {
            on: {
              click: () => { this.treeOnSelectChangeHandle(node) }
            }
          }, data.title)
        ]),
        h('Button', {
          props: Object.assign({}, {
            size: 'small'
          }),
          style: {
            marginRight: '38px',
            float: 'right',
            display: data.children && data.children.length > 0 ? 'none' : ''
          },
          on: {
            click: () => { this.$router.push({ name: 'partsManagement', params: { menuId: data.menuId } }) }
          }
        }, '部件管理')
      ])
    }
  }
}
</script>
