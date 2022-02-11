<template>
  <el-container style="height: 700px; border: 1px solid #eee">

    <el-aside width="250px" style="background-color: rgb(238, 241, 246)">

      <el-menu router :default-openeds="['0','1','2','3','4']">
        <el-submenu v-for="(item,index) in $router.options.routes" :index="index+''" v-if="item.show">
          <template slot="title"><i class="el-icon-menu"></i>{{ item.name }}</template>
          <el-menu-item v-for="(child,c_index) in item.children" :index="child.path"
          :class="$route.path==child.path?'is-active':''">{{child.name}}</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header style="height:55px; text-align: right; font-size: 12px">
        <el-dropdown>
          <i class="el-icon-setting" style="margin-right: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>查看</el-dropdown-item>
            <el-dropdown-item>新增</el-dropdown-item>
            <el-dropdown-item>删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <span>王小虎</span>
      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "Index"
}
</script>

<style scoped>

</style>