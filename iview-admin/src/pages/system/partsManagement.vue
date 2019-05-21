<style lang="less">
</style>

<template>
  <Card>
    <Form ref="partsObj" :model="partsObj" :label-width="100" >
      <Row v-for="(item, index) in partsObj.partsList" :key="index">
        <Col span="7" >
          <FormItem
            :label="'部件' + (index+1) + ' 类型：'"
            :prop="'partsList.' + index + '.cmpType'"
            :rules="[{required: true, type:'number', trigger: 'change', message: '请选择部件类型'}]"
          >
            <Select v-model="item.cmpType" :transfer="true" style="width: 100%" placeholder="部件类型" @on-change="partTypeChange">
              <Option v-for="item in partTypeList" :value="item.partTypePkid" :key="item.partTypePkid">{{ item.partType }}</Option>
            </Select>
          </FormItem>
        </Col>
        <Col span="7" >
          <FormItem
            :label=" 'ID：'"
            :prop="'partsList.' + index + '.cmpId'"
            :rules="[{required: true, trigger: 'blur', message: '请输入部件ID'}]"
          >
            <Input type="text" v-model.trim="item.cmpId" placeholder="部件ID" @on-blur="changePartId" ></Input>
          </FormItem>
        </Col>
        <Col span="7" >
          <FormItem
            :label=" '备注：'"
            :prop="'partsList.' + index + '.remark'"
            :rules="[{required: true, trigger: 'blur', message: '请输入部件备注'}]"
          >
            <Input v-model.trim="item.remark" placeholder="备注（部件功能等）"></Input>
          </FormItem>
        </Col>
        <Col span="2" offset="1">
        <Button @click="handleRemove(index)">Delete</Button>
        </Col>
      </Row>
      <FormItem>
        <Row>
          <Col span="12" offset="6">
            <Button type="dashed" long @click="handleAdd" icon="md-add">Add item</Button>
          </Col>
        </Row>
      </FormItem>
      <FormItem>
        <Row>
          <Col span="12" offset="10">
            <Button type="primary" @click="handleSubmit('partsObj')">Submit</Button>
            <Button @click="handleReset('partsObj')" style="margin-left: 8px">Reset</Button>
          </Col>
        </Row>
      </FormItem>
    </Form>
  </Card>
</template>

<script>
import * as util from '@/libs/util'
import * as partsManagementApi from '@/api/partsManagement'
export default {
  data () {
    return {
      menuId: null,
      index: 0,
      partsObj: {
        partsList: []
      },
      partTypeList: [
        {
          partTypePkid: 1,
          partType: '按钮组件'
        }
      ],
      sameId: ''
    }
  },
  computed: {},
  mounted () {
    const self = this
    self.menuId = self.$route.params.menuId
    self.getMenuPartDetail(self.menuId)
  },
  methods: {
    getMenuPartDetail (menuid) {
      const self = this
      partsManagementApi.getMenuPartDetail(menuid)
        .then(function (response) {
          console.log('getMenuPartDetail response', response)
          self.partsObj.partsList = response.data.rows
        })
        .catch(function (error) {
          console.log('partsManagementApi.getMenuPartDetail→error:', error)
        })
    },
    partTypeChange(){

    },
    saveOrUpdateMenuPart(param){
      const self = this
      partsManagementApi.saveOrUpdateMenuPart(param)
        .then(function (response) {
          self.getMenuPartDetail(self.menuId)
        })
        .catch(function (error) {
          console.log('partsManagementApi.saveOrUpdateMenuPart→error:', error)
        })
    },
    handleSubmit (name) {
      this.judgeIsExistSameId()
      if(!this.sameId){
        if(this.partsObj.partsList && this.partsObj.partsList.length === 0){
          console.log('partsList:', this.partsObj.partsList)
          let menuPartList= []
          let param = {
            menuId: this.menuId
          }
          menuPartList.push(param)
          let sysMenuPartSaveOrupdateParam = {
            menuPartList:  menuPartList
          }
          this.saveOrUpdateMenuPart(sysMenuPartSaveOrupdateParam)
        }
        if(this.partsObj.partsList && this.partsObj.partsList.length !== 0){
          let list = []
          if(this.menuId){
            this.partsObj.partsList.forEach((item, index) => {
              let param = {
                cmpId: item.cmpId,
                cmpType: item.cmpType,
                menuId: this.menuId,
                remark: item.remark
              }
              if(item.partId){
                param.partId= item.partId
              }
              list.push(param)
            })
          }
          let sysMenuPartSaveOrupdateParam = {
            menuPartList:list
          }
          this.$refs[name].validate((valid) => {
            console.log('valid:', valid)
            if (valid) {
              this.$Message.success('Success!');
              this.saveOrUpdateMenuPart(sysMenuPartSaveOrupdateParam)
            } else {
              this.$Message.error('Fail!');
            }
          })
        }
      }
    },
    handleReset (name) {
      this.$refs[name].resetFields();
    },
    handleAdd () {
//      this.index = this.partsObj.partsList.length+1
//      let id = this.menuId + '-' + this.index
      this.partsObj.partsList.push({
        cmpId: '',
        cmpType: 1,
        remark: ''
      });
    },
    handleRemove (index) {
      this.partsObj.partsList.splice(index,1)
    },
    changePartId (e) {
//      this.judgeIsExistSameId()
    },
    judgeIsExistSameId () {
      let list = []
      this.sameId = ''
      for(let item of this.partsObj.partsList){
        list.push(item.cmpId)
      }
      let str = list.join(",") + ",";
      for (let i = 0; i < list.length; i++) {
        if (str.replace(list[i] + ",", "").indexOf(list[i] + ",") > -1) {
          this.sameId = list[i]
          this.$Notice.warning({
            title: '警告',
            desc: '部件ID' + this.sameId + '存在重复，请修改'
          })
          break
        }
      }
    }
  }
}
</script>
