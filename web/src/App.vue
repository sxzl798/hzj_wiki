<template>
  <a-layout>
    <the-header></the-header>
    <div class="app-container">
      <router-view/>

      <the-footer/>
    </div>


  </a-layout>
</template>

<script lang="ts">
import { defineComponent} from 'vue';
import TheHeader from '@/components/the-header.vue';
import TheFooter from '@/components/the-footer.vue';


export default defineComponent({
  name: 'app',
    components: {
      TheHeader,
      TheFooter
},

});
</script>

<style scoped>


.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}


</style>

<style>
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 确保容器的最小高度为视窗高度 */
}

router-view {
  flex: 1; /* 让内容区自动扩展，填满剩余空间 */
}
</style>
