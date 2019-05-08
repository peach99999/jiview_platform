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
            <Button>新增</Button>
            <Button>修改</Button>
            <Button>删除</Button>
            <Input />
            <Button>查询</Button>
            <Button>刷新</Button>
          </div>
          <div>
            <Table :columns="column" :data="tableData"></Table>
            <Page :current="filter.pageNo" @on-change="pageNoChange" @on-page-size-change="pageSizeChange" :total="total" show-elevator show-sizer show-total/>
          </div>
        </Col>
      </Row>
    </Card>
  </div>
</template>
<script>
import { getDepartmentList } from '@/api/organizationalManagement';
export default {
  data () {
    return {
      loading:false, //树型结构列表 loading
      departmentList:[], //树型结构列表 展示全部部门
      column:[
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
      total:0
    }
  },
  mounted () {
    this.getDepartmentList();
  },
  methods: {
    //获取部门列表信息
    getDepartmentList(param){
      const self = this;
      self.loading = true;
      getDepartmentList(param).then(res => {
        let list = [...res.data.rows] || [];
        self.loading = false;
        if(!param){
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
      this.filter.pageNo = val;
      this.getDepartmentList(this.filter);
    },
    // 每页条数变化
    pageSizeChange (val) {
      this.filter.pageNo = 1;
      this.filter.pageSize = val;
      this.getDepartmentList(this.filter);
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
      const param = {
        pageNo: self.filter.pageNo,
        pageSize: self.filter.pageSize,
        deptId: e[0].deptId,
      }
      self.getDepartmentList(param);
    }
  }
}
</script>

<style>

</style>
