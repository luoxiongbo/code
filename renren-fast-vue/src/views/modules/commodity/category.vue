<template>
  <div>
    <el-button type="danger" @click="batchDelete">批量删除</el-button>
    <el-tree ref="categoryTree" :data="data" :default-expanded-keys="expandedKey" :props="defaultProps" :expand-on-click-node="false" show-checkbox node-key="id">
      <!-- 老师解读 (增加的属性，方法等，都可以参考 elementUI Tree 树形控件 的文档说明)
      1. 使用 vue 的插槽机制在每个菜单后加入 Append 和 Delete 连接
      2. slot-scope="{ node, data } : 是一种解构写法, node 表示当前节点, data 就是从数据库取出的分类对象信息
      3. @node-click="handleNodeClick" 没有什么用，删除，注意把对应的 method 也删除
      4. 增加相应的 append(data) 和 remove(node, data) 方法
      5. :expand-on-click-node="false": 设置 false 表示点击 Append 和 Delete 连接不展开下级分类
      6. show-checkbox 分类项显示成复选框，在删除时，可以支持多选
      7. node-key="id" 使用分类信息的 id 来唯一标识一个 node-->
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button v-if="node.level <= 2" type="text" size="mini" @click="() => append(data)">
            Append
          </el-button>
          <el-button type="text" size="mini" @click="() => edit(data)">
            Edit
          </el-button>
          <el-button v-if="node.childNodes.length === 0" type="text" size="mini" @click="() => remove(node, data)">
            Delete
          </el-button>
        </span>
      </span>
    </el-tree>

    <!-- 老韩解读
      1. :visible.sync="dialogVisible" 动态绑定 dialogVisible , 如果为 true,就显示对话框，否则不显示-->
    <el-dialog title="添加分类" :visible.sync="dialogVisible" width="30%">
      <!-- 动态绑定 catagory 数据对象, catagory 在数据池定义-->
      <el-form :model="category">
        <el-form-item label="分类名">
          <el-input v-model="category.name" autocomplete="off"></el-input>
        </el-form-item>
        <!-- 增加添加/修改需要的字段信息-->
        <el-form-item label="图标">
          <el-input v-model="category.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="单位">
          <el-input v-model="category.proUnit" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <!-- 点击确定，触发 addCategory 方法-->
        <el-button type="primary" @click="addOrUpdate()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      dialogType: '', // add 或者是 edit
      dialogVisible: false, // 是否显示对话框
      category: { // 初始化添加分类的表单对象数据
        name: '',
        parentId: 0,
        catLevel: 0,
        isShow: 1,
        sort: 0,
        proCount: 0,
        proUnit: '',
        icon: '',
        id: null
      },
      data: [],
      expandedKey: [],
      defaultProps: {
        children: 'childrenCategories', // 这里是返回的子分类集合节点名,不能乱写,否则，只会显示一级分类
        label: 'name' // 取出 name 显示, 可以参考 elementUI 树形控件规则
      }
    }
  },
  methods: {
    batchDelete () {
      /**
       * 老韩解读 this.$refs.categoryTree.getCheckedNodes(leafOnly, includeHalfChecked)
       * 1. this.$refs.categoryTree : 代表 el-tree 控件
       * 2. getCheckedNodes(leafOnly, includeHalfChecked)
       * 2.1 若节点可被选择（即 show-checkbox 为 true），则返回目前被选中的节点所组成的数组
       * 2.2 (leafOnly, includeHalfChecked) 接收两个 boolean 类型的参数，
       *    1. 是否只是叶子节点，默认值为 false
       *    2. 是否包含半选节点，默认值为 false
       * 2.3 老师没有传入参数 , 等价getCheckedNodes(false, false), 即返回选中的分类节点(不包括半选的)
       * 2.4 半选就是某个分类的子分类, 只选择了部分，没有全选，是 - 符号
       */
      var checkedNodes = this.$refs.categoryTree.getCheckedNodes()
      console.log('被选中的分类元素= ', checkedNodes)
      var ids = []
      var categoryNames = [] // 收集分类名，给出提示
      for (var i = 0; i < checkedNodes.length; i++) {
        ids.push(checkedNodes[i].id)
        categoryNames.push(checkedNodes[i].name)
      }
      this.$confirm(`是否批量删除【${categoryNames}】菜单?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/commodity/category/delete'),
          // url: 'http://localhost:9090/commodity/category/delete',
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          this.$message({
            message: '家居分类批量删除 OK',
            type: 'success'
          })
          // 刷新分类列表
          this.getCategories()
        })
      }).catch(() => {
      })
    },
    addOrUpdate () {
      if (this.dialogType === 'add') {
        this.addCategory() // 真正添加
      }
      if (this.dialogType === 'edit') {
        // 真正修改(到数据库)
        this.updateCategory()
      }
    },
    // 真正修改分类信息(数据库/表)
    updateCategory () {
      /**
       * 老师解读
       * 1. this.category 已经绑定了要修改的最新数据
       * 2. var { id, name, icon, proUnit } = this.category 是对象解构写法, 当然你也可以分开写
       * 3. 这里我们只是发送了要修改的字段信息，没有发送的字段，在数据库不会被修改(保持原值, 可以通过后台 update 语句看)
       */
      var {id, name, icon, proUnit} = this.category
      this.$http({
        url: this.$http.adornUrl('/commodity/category/update'),
        // url: 'http://localhost:9090/commodity/category/update',
        method: 'post',
        data: this.$http.adornData({id, name, icon, proUnit}, false)
      }).then(({data}) => {
        this.$message({
          message: '分类修改成功',
          type: 'success'
        })
        // 关闭对话框
        this.dialogVisible = false
        // 刷新分类列表
        this.getCategories()
        // 设置需要默认展开的菜单, 因为要使用到 this.category.parentId,所以在 edit()
        // 方法绑定一下,否则是没有值的
        this.expandedKey = [this.category.parentId]
      })
    },
    // 回显修改分类的信息, 可以通过 data.id, data.name...来输出对应数据
    edit (data) {
      this.dialogType = 'edit'
      console.log('要修改的数据', data)
      this.dialogVisible = true
      // 发送请求, 到数据库获取当前分类的实时数据
      this.$http({
        url: this.$http.adornUrl(`/commodity/category/info/${data.id}`),
        // url: `http://localhost:9090/commodity/category/info/${data.id}`,
        method: 'get'
      }).then(({data}) => {
        // 请求成功
        console.log('要回显的数据', data)
        // 将返回的 data 数据字段信息，分别绑定到 category 对象
        this.category.name = data.category.name
        this.category.id = data.category.id
        this.category.icon = data.category.icon
        this.category.proUnit = data.category.proUnit
        // 不修改的，可以不用绑定
        this.category.parentId = data.category.parentId
        // this.category.catLevel = data.category.catLevel
        // this.category.sort = data.category.sort
        // this.category.isShow = data.category.isShow
      })
    },
    // 处理添加请求, 显示添加分类对话框，同时根据点击的位置，
    // 获取添加分类 category 对象的相关数据
    append (data) {
      this.dialogType = 'add'
      this.dialogVisible = true
      this.category.parentId = data.id
      // 添加的分类为其父类的层级+1, 这里*1 是为了将字符串转成数值
      this.category.catLevel = data.catLevel * 1 + 1
      this.category.id = null
      this.category.name = ''
      this.category.icon = ''
      this.category.proUnit = ''
      this.category.proCount = 0
      this.category.sort = 0
      this.category.isShow = 1
      // 调试，看看 this.category 数据是否 oK
      console.log('this.category= ', this.category)
    },
    // ------------------------
    // 添加三级分类
    addCategory () {
      this.$http({
        url: this.$http.adornUrl('/commodity/category/save'),
        // url: 'http://localhost:9090/commodity/category/save',
        method: 'post',
        data: this.$http.adornData(this.category, false)
      }).then(({ data }) => {
        this.$message({
          message: '分类信息保存 OK',
          type: 'success'
        })
        // 关闭对话框
        this.dialogVisible = false
        // 刷新分类列表
        this.getCategories()
        // 设置需要默认展开的菜单
        this.expandedKey = [this.category.parentId]
      })
    },
    // 处理删除请求
    remove (node, data) {
      // 查看 data 对象 的数据构成情况
      console.log('data= ', data, ' id= ' + data.id + ' name= ' + data.name)
      var ids = [data.id] // 要删除的 id，放入到一个数组
      // 确认删除
      this.$confirm(`是否删除【${data.name}】菜单?`, '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => { // 确认就操作删除
        this.$http({
          url: this.$http.adornUrl('/commodity/category/delete'),
          // url: 'http://localhost:9090/commodity/category/delete',
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => { // 删除成功
          this.$message({
            message: '菜单删除成功',
            type: 'success'
          })
          // 刷新分类列表
          this.getCategories()
          // 设置需要默认展开的菜单，即父节点
          this.expandedKey = [node.parent.data.id]
        })
      }).catch(() => { // 取消就不删除了
      })
    },
    getCategories () {
      this.$http({
        url: this.$http.adornUrl('/commodity/category/list/tree'),
        // url: 'http://localhost:9090/commodity/category/list/tree',
        method: 'get'
      }).then(({data}) => { // ({data}是解构了返回数据的 data 部分
        console.log('返回的 data 数据= ', data)
        this.data = data.data
      })
    },
    handleNodeClick (data) {
      console.log(data)
    }
  },
  // 生命周期创建完成（可以访问当前 this 实例）
  created () {
    this.getCategories()
  }
}
</script>
<style scoped>
</style>
