<template>
  <a-layout-footer style="text-align: center">
    <P size="small" v-show="!!user.id">
      你好，{{user.name}}
    </P>
    Design ©2023 Created by Hzzzj
  </a-layout-footer>
</template>

<script lang="ts">
import {defineComponent,computed} from "vue";
import store from "@/store";
export default defineComponent({
  name:'the-footer',
  setup(){
    const user = computed(()=> store.state.user);
    return{
      user
    }
  }
});

</script>