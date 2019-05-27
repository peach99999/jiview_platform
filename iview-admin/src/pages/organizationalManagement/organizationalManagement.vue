<template>
  <div>
    <Card shadow>
      <Row>
        <Col span="6">
        <div class="layout-tree-list">
          <Card>
            <Tree :data="departmentList" @on-select-change="clickTreeNodeChange"></Tree>
          </Card>
        </div>
        </Col>
        <Col span="16">
        <div class="layout-table-list">
          <Card>
            <div>
              <Input v-model="searchValue" placeholder="请输入部门名称" class="table-search-input"  @on-change="searchValueChange"/>
            </div>
            <div class="search-button-group">
              <Button @click="searchDepartmentInfo" type="success">查询</Button>
              <Button @click="refreshDepartmentInfo" type="info">重置</Button>
            </div>
          </Card>
          <Card>
            <div class="table-button-group">
              <Button @click="addSelectedInfo" type="primary">新增</Button>
              <Button @click="changeSelectedInfo" type="warning">修改</Button>
              <Button @click="deleteSelectedInfo" type="error">删除</Button>
            </div>
            <div>
              <Table ref="selection" :columns="column" :data="tableData" @on-selection-change="handleSelectChange"></Table>
              <Page :current="filter.pageNo" class="table-page"  @on-change="pageNoChange" @on-page-size-change="pageSizeChange" :total="total" show-elevator show-sizer show-total/>
            </div>
            <Spin fix v-if="loading">
              <Icon type="load-c" size=18 class="demo-spin-icon-load"></Icon>
              <div>加载中...</div>
            </Spin>
          </Card>
        </div>
        </Col>
      </Row>
      <Modal v-model="modelVisible" width="560">
        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate"  :label-width="80" class="form-modal">
          <FormItem label="部门名称" prop="deptName">
            <Input type="text" v-model.trim="formValidate.deptName" ></Input>
          </FormItem>
          <FormItem label="上级部门" prop="parentId">
            <Select v-model="formValidate.parentId" clearable >
              <Option v-for="item in selectDeptList" :value="item.deptId" :key="item.deptId">{{ item.deptName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="排序号" prop="sortno">
            <Input v-model.trim="formValidate.sortno" ></Input>
          </FormItem>
          <FormItem label="备注"  prop="remark">
            <Input v-model.trim="formValidate.remark" ></Input>
          </FormItem>
        </Form>
        <div slot="footer">
          <Button type="text" size="large" @click="modelVisible=false">取消</Button>
          <Button type="primary" size="large" @click="addOrChangeDepartmentInfoBtn">确定</Button>
        </div>
      </Modal>
      <Modal
        v-model="deleteModal"
        title="提示"
        @on-ok="deleteModalOk"
        @on-cancel="deleteModalCancel">
        <p>删除部门将会同时删除其下属部门，确认删除吗？</p>
      </Modal>
    </Card>
  </div>
</template>
<script>
import { getDepartmentList, deleteDepartmentList, getSelectDepartmentList, addDepartmentList, getDepartmentDetail } from '@/api/organizationalManagement'
export default {
  data () {
    return {
      loading: false, // 树型结构列表 loading
      departmentList: [], // 树型结构列表 展示全部部门
      column: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title: '部门名称',
          key: 'deptName'
        },
        {
          title: '上级部门',
          key: 'parentDeptName'
        },
        {
          title: '排序号',
          key: 'sortno'
        },
        {
          title: '备注 ',
          key: 'remark'
        }
      ],
      tableData: [],
      filter: {
        pageNo: 1,
        pageSize: 10
      },
      total: 0,
      searchValue: '',
      deleteSeletionList: [],
      selectedList: [],
      modelVisible: false,
      formValidate: {
        deptName: '',
        parentId: '',
        sortno: ''
      },
      ruleValidate: {
        deptName: [
          { required: true, message: '部门名称不能为空', trigger: 'blur' }
        ],
        parentId: [
          { required: true, message: '上级部门不能为空', trigger: 'change', type: 'number' }
        ],
        sortno: [
          { required: true, message: '排序号不能为空', trigger: 'blur' }
        ],
        remark: [
          { required: false, trigger: 'blur' }
        ]
      },
      selectDeptList: [],
      deleteModal: false,
      nodeDeptId: ''
    }
  },
  mounted () {
    this.getDepartmentList()
    this.getSelectDepartmentList()
  },
  methods: {
    // 获取部门列表信息
    getDepartmentList (param, flag) {
      const self = this
      self.loading = true
      getDepartmentList(param).then(res => {
        let list = [...res.data.rows] || []
        self.loading = false
//        if (!param || flag) {
//          self.handleDepartmentList(list)
//        }
        self.tableData = list
        self.total = res.data.count
      }).catch(err => {
        console.log('err', err)
        self.loading = false
      })
    },
    // 页码变化
    pageNoChange (val) {
      const self = this
      self.filter.pageNo = val
      self.filter.pageSize = 10
      const param = {
        deptId:self.nodeDeptId,
        pageNo: self.filter.pageNo,
        pageSize: self.filter.pageSize,
        deptName: self.searchValue || ''
      }
      self.getDepartmentList(param)
    },
    // 每页条数变化
    pageSizeChange (val) {
      const self = this
      self.filter.pageNo = 1
      self.filter.pageSize = val
      const param = {
        deptId:self.nodeDeptId,
        pageNo: self.filter.pageNo,
        pageSize: self.filter.pageSize,
        deptName: self.searchValue || ''
      }
      self.getDepartmentList(param)
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
      this.departmentList = this.handleTreeData(listTmp, treeData)
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
    // 点击树节点（部门）触发事件
    clickTreeNodeChange (e) {
      console.log('e', e)
      const self = this
      self.nodeDeptId = e[0].deptId
      self.filter.pageNo = 1
      self.filter.pageSize = 10
      const param = {
        pageNo: self.filter.pageNo,
        pageSize: self.filter.pageSize,
        deptId: e[0].deptId,
        deptName: self.searchValue
      }
      self.getDepartmentList(param)
    },
    // 搜索框数据变化
    searchValueChange (val) {
      console.log('searchValue:', this.searchValue)
    },
    // 搜索按钮
    searchDepartmentInfo () {
      const self = this
      self.filter.pageNo = 1
      self.filter.pageSize = 10
      const param = {
        deptId:self.nodeDeptId,
        pageNo: self.filter.pageNo,
        pageSize: self.filter.pageSize,
        deptName: self.searchValue
      }
      self.getDepartmentList(param)
    },
    // 刷新按钮
    refreshDepartmentInfo (flag) {
      const self = this
      self.filter.pageNo = 1
      self.filter.pageSize = 10
      self.searchValue = ''
      self.nodeDeptId = ''
      const param = {
        deptId:self.nodeDeptId,
        pageNo: self.filter.pageNo,
        pageSize: self.filter.pageSize,
        deptName: self.searchValue
      }
      self.getDepartmentList(param, flag)
      self.getSelectDepartmentList()
    },
    handleSelectChange (seletion) {
      const self = this
      self.selectedList = []
      for (let i = 0; i < seletion.length; i++) {
        self.selectedList.push(seletion[i].deptId)
      }
    },
    // 判断删除部门是否有子部门
    deleteSelectedInfo () {
      const self = this
      self.loading = true
      self.deleteSeletionList = []
      self.handleDeleteInfoList(self.selectedList)
      if (self.deleteSeletionList && self.deleteSeletionList.length > self.selectedList.length) {
        self.deleteModal = true
      }
      if (self.deleteSeletionList && self.deleteSeletionList.length === self.selectedList.length) {
        self.deleteEnsureSelectedInfo()
      }
    },
    // 确认删除
    deleteModalOk () {
      const self = this
      self.deleteEnsureSelectedInfo()
    },
    deleteModalCancel () {
      const self = this
      self.deleteModal = false
    },
    // 调用删除部门Api
    deleteEnsureSelectedInfo () {
      const self = this
      const param = {
        deptIdList: self.deleteSeletionList
      }
      deleteDepartmentList(param).then(res => {
        self.refreshDepartmentInfo(true)
        self.loading = false
        self.deleteModal = false
      }).catch(err => {
        console.log('err', err)
        self.loading = false
      })
    },
    // 查找删除部门下的子部门
    handleDeleteInfoList (list) { // list为列表选中数据
      const self = this
      // self.departmentList 树型数据结构
      for (let node of list.values()) {
        self.traverseTree(self.departmentList[0], node)
      }
    },
    // 遍历一棵树
    traverseTree (treeList, selectNode) {
      const self = this
      const treeListValue = treeList
      const selectNodeValue = selectNode
      if (selectNodeValue === treeListValue.deptId) {
        self.traverseTreeNode(treeList)
      }
      if (selectNodeValue !== treeListValue.deptId) {
        if (treeListValue.children) {
          treeListValue.children.forEach(item => {
            self.traverseTree(item, selectNode)
          })
        }
      }
    },
    // 遍历一个树节点的子节点
    traverseTreeNode (treeList) {
      const self = this
      let flag = false
      console.log('treeList', treeList)
      if (treeList && treeList.deptId) {
        console.log('treeList.deptId', treeList.deptId)
        for (let value of self.deleteSeletionList) {
          if (value === treeList.deptId) {
            flag = true
          }
        }
        if (!flag) {
          self.deleteSeletionList.push(treeList.deptId)
        }
        if (treeList.children) {
          treeList.children.forEach(item => {
            self.traverseTreeNode(item)
          })
        }
      }
    },
    // 新增弹框
    addSelectedInfo () {
      const self = this
      self.$refs['formValidate'].resetFields()
      if (self.nodeDeptId) {
        self.formValidate.parentId = self.nodeDeptId
      }
      self.modelVisible = true
    },
    // 新增（修改）弹出框 获取上级部门下拉框
    getSelectDepartmentList () {
      const self = this
      getSelectDepartmentList().then(res => {
        self.selectDeptList = res.data.rows || []
        self.handleDepartmentList([...res.data.rows])
      }).catch(err => {
        console.log('err', err)
      })
    },
    // 新增（修改）部门信息
    addOrChangeDepartmentInfo (param) {
      const self = this
      self.loading = true
      console.log('param', param)
      addDepartmentList(param).then(res => {
        self.refreshDepartmentInfo(true)
        self.loading = false
        self.modelVisible = false
      }).catch(err => {
        console.log('err', err)
        self.loading = false
      })
    },
    // 新增（修改）确定
    addOrChangeDepartmentInfoBtn () {
      const self = this
      const param = {
        deptName: self.formValidate.deptName,
        leaf: true,
        parentId: self.formValidate.parentId,
        remark: self.formValidate.remark,
        sortno: self.formValidate.sortno
      }
      if (self.selectedList[0]) {
        param.deptId = self.selectedList[0]
      }
      // 验证数据来源后调用接口
      self.$refs['formValidate'].validate((valid) => {
        console.log('valid', valid)
        console.log('self', self)
        if (valid) {
          self.addOrChangeDepartmentInfo(param)
        }
      })
    },
    // 修改弹框
    changeSelectedInfo () {
      const self = this
      if (self.selectedList.length === 0) {
        this.$Notice.warning({
          title: '提示',
          desc: '请选择一个部门 '
        })
      }
      if (self.selectedList.length > 1) {
        this.$Notice.warning({
          title: '提示',
          desc: '只能选择选择一个部门 '
        })
      }
      if (self.selectedList.length === 1) {
        self.getSelectDepartmentList()
        self.getDepartmentInfoDetail(self.selectedList[0])
      }
    },
    // 获取部门详情
    getDepartmentInfoDetail (id) {
      const self = this
      getDepartmentDetail(id).then(res => {
        let data = res.data.row || {}
        self.formValidate.deptName = data.deptName
        self.formValidate.parentId = data.parentId
        self.formValidate.sortno = String(data.sortno)
        self.formValidate.remark = data.remark
        self.modelVisible = true
      }).catch(err => {
        console.log('err', err)
      })
    }
  }
}
</script>

<style>
  .layout-tree-list{
    margin: 20px 0 0 20px
  }
  .layout-table-list{
    margin: 20px 0 0 50px
  }
  .table-search-input{
    width: 300px;
  }
  .table-button-group Button{
    margin: 10px 5px 10px 0;
  }
  .table-page{
    margin-top: 20px;
  }
  .form-modal{
    margin-top: 40px;
  }
  .search-button-group Button{
    margin-top: 10px;
    margin-right: 5px;
  }
</style>
