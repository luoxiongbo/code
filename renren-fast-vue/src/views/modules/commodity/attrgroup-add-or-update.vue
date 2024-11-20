<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    @close="dialogClose">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="组名" prop="name">
      <el-input v-model="dataForm.name" placeholder="组名"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
    </el-form-item>
    <el-form-item label="说明" prop="description">
      <el-input v-model="dataForm.description" placeholder="说明"></el-input>
    </el-form-item>
    <el-form-item label="组图标" prop="icon">
      <el-input v-model="dataForm.icon" placeholder="组图标"></el-input>
    </el-form-item>
    <el-form-item label="所属分类 id" prop="categoryId">
    <!--<el-input v-model="dataForm.categoryId" placeholder="所属分类 id"></el-input>-->
      <!-- 将 el-input 组件改成 el-cascader-->
      <!--老师解读
        1. 在添加选择所属分类 ID 时，我们使用了 Cascader 级联选择器, 具体用法参考 elementUi-Cascader 级联选择器-基础用法
        2. v-model="cascadedCategoryId" : 是最终绑定的值,因为 el-cascader 关联的 v-model 是一个数组，
           记录的是三级分类全部 id,即[第 1 级分类 id,第 2 级分类 id,第 3 级分类 id],
           比如[1,4,321]，因此在数据池的 categoryId 是一个数组
        3. :options="categorys" 表示级联显示的时候各个层的选型是从 categorys 数组来的
        4. :props="props" 显示的选项值(value)/显示的标签(label)/子选项(children) 分别和返回的 category 的对象的哪个字段关联
      -->
      <el-cascader filterable placeholder="搜索: " v-model="cascadedCategoryId" :options="categorys" :props="props"></el-cascader>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        props: { // 显示返回的家居分类的哪些字段/信息
          value: 'id',
          label: 'name',
          children: 'childrenCategories'
        },
        categorys: [], // 所有的家居分类
        cascadedCategoryId: [], // 这个值的变化要注意，后端变前端也得变
        visible: false,
        dataForm: {
          id: 0,
          name: '',
          sort: '',
          description: '',
          icon: '',
          categoryId: 0 // 默认给一个 0, 否则表单提交校验通不过，都是有原因的，这是为了前端的校验是满足下面的rule的，请求方法需要传入参数是必要的
        },
        dataRule: {
          name: [
            { required: true, message: '组名不能为空', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ],
          description: [
            { required: true, message: '说明不能为空', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: '组图标不能为空', trigger: 'blur' }
          ],
          categoryId: [
            { required: true, message: '所属分类 id不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    // 调用方法
    created () {
      this.getCategories()
    },
    methods: {
      dialogClose () {
        this.cascadedCategoryId = []
      },
      // 到服务端要所有的家居分类
      getCategories () {
        this.$http({
          // 如果启动了网关服务, 则 url 修改成走网关服务即可
          url: this.$http.adornUrl('/commodity/category/list/tree'),
          // url: 'http://localhost:9090/commodity/category/list/tree',
          method: 'get'
        }).then(({ data }) => {
          this.categorys = data.data
        })
      },
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/commodity/attrgroup/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.attrgroup.name
                this.dataForm.sort = data.attrgroup.sort
                this.dataForm.description = data.attrgroup.description
                this.dataForm.icon = data.attrgroup.icon
                this.dataForm.categoryId = data.attrgroup.categoryId
                // 因为修改，需要回显该所在第 3 级分类, 是一个数组, 所以我们需要在回显查询时,把这个数组查询到，返回给 cascadedCategoryId 数组即可
                this.cascadedCategoryId = data.attrgroup.cascadedCategoryId
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/commodity/attrgroup/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'sort': this.dataForm.sort,
                'description': this.dataForm.description,
                'icon': this.dataForm.icon,
                // 'categoryId': this.dataForm.categoryId,
                // 将 categoryId : this.cascadedCategoryId 数组的最后一个值就是该属性分组所在的第 3 级分类 id
                'categoryId': this.cascadedCategoryId[this.cascadedCategoryId.length - 1]
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
