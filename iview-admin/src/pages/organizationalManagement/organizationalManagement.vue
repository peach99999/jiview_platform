<template>
  <div>
    <Card shadow>
      <Row>
        <Col span="6">
          <div>
            <Tree :data="departmentList" @on-select-change="clickTreeNodeChange"></Tree>
          </div>
        </Col>
        <Col span="16">
          <div>
            <Button @click="addSelectedInfo">新增</Button>
            <Button>修改</Button>
            <Button @click="deleteSelectedInfo">删除</Button>
            <Input v-model="searchValue" placeholder="请输入部门名称"  @on-change="searchValueChange"/>
            <Button @click="searchDepartmentInfo">查询</Button>
            <Button @click="refreshDepartmentInfo">刷新</Button>
          </div>
          <div>
            <Table ref="selection" :columns="column" :data="tableData" @on-selection-change="handleSelectChange"></Table>
            <Page :current="filter.pageNo" @on-change="pageNoChange" @on-page-size-change="pageSizeChange" :total="total" show-elevator show-sizer show-total/>
          </div>
        </Col>
      </Row>
      <Modal v-model="modelVisible" width="560">
        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate"  :label-width="80">
          <FormItem label="部门名称" prop="deptName">
            <Input v-model="formValidate.deptName" ></Input>
          </FormItem>
          <FormItem label="上级部门" prop="deptId">
            <Select v-model="formValidate.deptId" clearable >
              <Option v-for="item in selectDeptList" :value="item.deptName" :key="item.deptId">{{ item.deptName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="排序号">
            <Input v-model="formValidate.sortno" ></Input>
          </FormItem>
          <FormItem label="备注">
            <Input v-model="formValidate.remark" ></Input>
          </FormItem>
        </Form>
      </Modal>
    </Card>
  </div>
</template>
<script>
import { getDepartmentList,deleteDepartmentList,getSelectDepartmentList } from '@/api/organizationalManagement';
export default {
  data () {
    return {
      loading:false, //树型结构列表 loading
      departmentList:[], //树型结构列表 展示全部部门
      column:[
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title:'部门名称',
          key:'deptName'
        },
        {
          title:'上级部门',
          key:'deptName'
        },
        {
          title:'排序号',
          key:'sortno'
        },
        {
          title:'备注 ',
          key:'remark'
        }
      ],
      tableData:[],
      filter:{
        pageNo:1,
        pageSize:10
      },
      total:0,
      searchValue:'',
      deleteSeletionList:[],
      modelVisible:false,
      formValidate: {
        deptName: '',
        deptId: '',
        sortno: '',
        remark: '',
      },
      ruleValidate: {
        deptName: [
          { required: true, message: '部门名称不能为空', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '上级部门不能为空', trigger: 'change' }
        ]
      },
      selectDeptList:[]
    }
  },
  mounted () {
    this.getDepartmentList();
  },
  methods: {
    //获取部门列表信息
    getDepartmentList(param,flag){
      const self = this;
      self.loading = true;
      getDepartmentList(param).then(res => {
        let list = [...res.data.rows] || [];
        self.loading = false;
        if(!param || flag){
          self.handleDepartmentList(list);
        }
        self.tableData = list;
        self.total = res.data.count;
      }).catch(err => {
        console.log('err', err);
        self.loading = false;
      });
    },
    // 页码变化
    pageNoChange (val) {
      const self = this;
      self.filter.pageNo = val;
      self.filter.pageSize = 10;
      const param = {
        pageNo: self.filter.pageNo,
        pageSize: self.filter.pageSize,
        deptName: self.searchValue || '',
      }
      self.getDepartmentList(param);
    },
    // 每页条数变化
    pageSizeChange (val) {
      const self = this;
      self.filter.pageNo = 1;
      self.filter.pageSize = val
      const param = {
        pageNo: self.filter.pageNo,
        pageSize: self.filter.pageSize,
        deptName: self.searchValue || '',
      }
      self.getDepartmentList(param);
    },
    //将获得数据转换成树型结构数据
    handleDepartmentList(list){
      let listTmp = [...list];
      let treeData = [];
      for(let i = 0; i < listTmp.length; i++){
        if(!listTmp[i].parentId){
          let obj = {
            title:listTmp[i].deptName,
            expand:true,
            deptId:listTmp[i].deptId,
            children:[]
          }
          treeData.push(obj);
          listTmp.splice(i, 1);
          i--;
        }
      }
      this.departmentList = this.handleTreeData(listTmp,treeData);
      //list:需要转换的数组（平铺型数组）  treeData:目标转换的数组（树型数组）
    },
    handleTreeData(list,treeData){
      //list:需要转换的数组（平铺型数组）  treeData:目标转换的数组（树型数组）
      let treeTmpList = list;
      let treeTmpData = treeData;
      if(treeTmpList.length !==0){
        for(let i = 0; i<treeTmpData.length; i++){
          for(let j = 0; j<treeTmpList.length; j++){
            if(treeTmpData[i].deptId === treeTmpList[j].parentId){
              let obj = {
                title:treeTmpList[j].deptName,
                expand:true,
                deptId:treeTmpList[j].deptId,
                parentId:treeTmpList[j].parentId,
                children:[]
              }
              treeTmpData[i].children.push(obj);
              treeTmpList.splice(j, 1);
              j--;
            }
          }
          this.handleTreeData(treeTmpList,treeTmpData[i].children);
        }

      }
      return treeTmpData;
    },
    //点击树节点（部门）触发事件
    clickTreeNodeChange(e){
      console.log('e',e)
      const self = this;
      self.filter.pageNo = 1;
      self.filter.pageSize = 10;
      const param = {
        pageNo: self.filter.pageNo,
        pageSize: self.filter.pageSize,
        deptId: e[0].deptId,
      }
      self.getDepartmentList(param);
    },
    //搜索框数据变化
    searchValueChange (val){
      console.log('searchValue:',this.searchValue)
    },
    //搜索按钮
    searchDepartmentInfo(){
      const self = this;
      self.filter.pageNo = 1;
      self.filter.pageSize = 10;
      const param = {
        pageNo: self.filter.pageNo,
        pageSize: self.filter.pageSize,
        deptName: self.searchValue,
      }
      self.getDepartmentList(param);
    },
    //刷新按钮
    refreshDepartmentInfo(flag){
      const self = this;
      self.filter.pageNo = 1;
      self.filter.pageSize = 10;
      self.searchValue = ''
      const param = {
        pageNo: self.filter.pageNo,
        pageSize: self.filter.pageSize,
        deptName: self.searchValue,
      }
      self.getDepartmentList(param,flag);
    },
    handleSelectChange(seletion){
      const self = this;
      self.deleteSeletionList = [];
      for(let i = 0; i < seletion.length; i++){
        self.deleteSeletionList.push(seletion[i].deptId)
      }
      console.log('self.deleteSeletionList:',self.deleteSeletionList)
    },
    deleteSelectedInfo(){
      const self = this;
      self.loading = true;
      const param = {
        deptIdList:self.deleteSeletionList
      }
      console.log('param',param)
      deleteDepartmentList(param).then(res => {
        self.refreshDepartmentInfo(true);
        self.loading = false;
      }).catch(err => {
        console.log('err', err);
        self.loading = false;
      });
    },
    addSelectedInfo(){
      const self = this;
      self.modelVisible = true;
      self.getSelectDepartmentList();
    },
    //新增（修改）弹出框 获取上级部门下拉框
    getSelectDepartmentList(){
      const self = this;
      getSelectDepartmentList().then(res => {
        self.selectDeptList = res.data.rows || [];
      }).catch(err => {
        console.log('err', err);
      });
    }
  }
}
</script>

<style>

</style>
