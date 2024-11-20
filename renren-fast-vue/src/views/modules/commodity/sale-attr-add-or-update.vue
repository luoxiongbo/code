<template>
  <el-dialog
    :title="!dataForm.attrId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="属性名" prop="attrName">
        <el-input v-model="dataForm.attrName" placeholder="属性名"></el-input>
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <el-input v-model="dataForm.icon" placeholder="图标"></el-input>
      </el-form-item>
      <el-form-item label="可选值列表" prop="valueSelect">
        <el-input v-model="dataForm.valueSelect" placeholder="可选值列表[用逗号分隔]"></el-input>
      </el-form-item>
      <el-form-item label="属性类型" prop="attrType">
        <!-- <el-input v-model="dataForm.attrType" placeholder="属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]"></el-input>-->
        <el-select v-model="dataForm.attrType" placeholder="请选择">
          <el-option label="基本属性" :value="1"></el-option>
          <el-option label="销售属性" :value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="启用状态" prop="enable">
        <el-input v-model="dataForm.enable" placeholder="启用状态[0 - 禁用，1 - 启用]"></el-input>
      </el-form-item>
      <el-form-item label="所属分类" prop="categoryId">
        <!-- <el-input v-model="dataForm.categoryId" placeholder=" 所 属 分 类"></el-input>-->
        <!-- 加入 el-cascader 并监听 cascadedCategoryId 变化 -->
        <el-cascader :cascadedCategoryId.sync="cascadedCategoryId" v-model="cascadedCategoryId" :options="categorys" :props="props"></el-cascader>
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
      cascadedCategoryId: [],
      props: { // 显示返回的家居分类的哪些字段/信息
        value: 'id',
        label: 'name',
        children: 'childrenCategories'
      },
      categorys: [], // 所有的家居分类
      // attrGroups: [], // 得到选择的某个分类的属性分组有哪些
      visible: false,
      dataForm: {
        attrId: 0,
        attrName: '',
        // searchType: '',
        icon: '',
        valueSelect: '',
        attrType: '',
        enable: '',
        categoryId: 0 // 给一个默认值，通过表单提交校验
        // attrGroupId: '', // 在添加销售属性时，没有属性组 id
        // showDesc: ''
      },
      dataRule: {
        attrName: [
          { required: true, message: '属性名不能为空', trigger: 'blur' }
        ],
        icon: [
          { required: true, message: '图标不能为空', trigger: 'blur' }
        ],
        valueSelect: [
          { required: true, message: '可选值列表[用逗号分隔]不能为空', trigger: 'blur' }
        ],
        attrType: [
          { required: true, message: '属性类型[0-销售属性，1-基本属性]不能为空', trigger: 'blur' }
        ],
        enable: [
          { required: true, message: '启用状态[0 - 禁用，1 - 启用]不能为空', trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: '所属分类不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getCategorys()
  },
  watch: {
    cascadedCategoryId (path) {
      this.dataForm.categoryId = path[path.length - 1]
    }
  },
  methods: {
    // 显示添加属性界面相关方法
    getCategorys () {
      this.$http({
        // 如果启动了网关服务, 则 url 修改成走网关服务即可
        // url: 'http://localhost:9090/commodity/category/list/tree',
        url: this.$http.adornUrl('/commodity/category/list/tree'),
        method: 'get'
      }).then(({data}) => {
        this.categorys = data.data
      })
    },
    init (id) {
      this.dataForm.attrId = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.attrId) {
          this.$http({
            url: this.$http.adornUrl(`/commodity/attr/info/${this.dataForm.attrId}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.attrName = data.attr.attrName
              this.dataForm.icon = data.attr.icon
              this.dataForm.valueSelect = data.attr.valueSelect
              this.dataForm.attrType = data.attr.attrType
              this.dataForm.enable = data.attr.enable
              this.dataForm.categoryId = data.attr.categoryId
              // 因为修改，需要回显该所在第 3 级分类, 是一个数组, 所以我们需要在回显查询时,把这个数组查询到，返回给 cascadedCategoryId 数组即可
              this.cascadedCategoryId = data.attr.cascadedCategoryId
            }
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit () {
      console.log('id是{}', this.dataForm.categoryId)
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/commodity/attr/${!this.dataForm.attrId ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'attrId': this.dataForm.attrId || undefined,
              'attrName': this.dataForm.attrName,
              // 'searchType': this.dataForm.searchType,
              'icon': this.dataForm.icon,
              'valueSelect': this.dataForm.valueSelect,
              'attrType': this.dataForm.attrType,
              'enable': this.dataForm.enable,
              'categoryId': this.dataForm.categoryId
              // 'attrGroupId': this.dataForm.attrGroupId, // 在添加时把属性所在组 id 也发送
              // 'showDesc': this.dataForm.showDesc
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
