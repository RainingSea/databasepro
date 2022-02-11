<template>
  <el-container style="height: 700px; border: 1px solid #eee">
    <!--    <el-container style="height: 700px; border: 1px solid #eee">-->
    <el-aside width="250px" >
      <el-menu
          router
          default-active="2"
          class="el-menu-vertical-demo"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          style="height: 100%">
        <el-menu-item
            v-for="child in $router.options.routes[5].children" :index="child.path"
            :class="$route.path==child.path?'is-active':''">
          {{child.name}}
        </el-menu-item>
<!--        <el-menu-item index="/send_admin">-->
<!--          <i class="el-icon-location"></i>-->
<!--          <span>导航一</span>-->
<!--        </el-menu-item>-->
<!--        <el-menu-item index="2">-->
<!--          <i class="el-icon-menu"></i>-->
<!--          <span slot="title">导航二</span>-->
<!--        </el-menu-item>-->
<!--        <el-menu-item index="3">-->
<!--          <i class="el-icon-document"></i>-->
<!--          <span slot="title">导航三</span>-->
<!--        </el-menu-item>-->
<!--        <el-menu-item index="4">-->
<!--          <i class="el-icon-setting"></i>-->
<!--          <span slot="title">导航四</span>-->
<!--        </el-menu-item>-->
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="height:55px; text-align: right; font-size: 12px">
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "admin"
}
</script>

<style scoped>

</style>