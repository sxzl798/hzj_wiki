<template>
  <a-layout>
    <a-layout-content :style="{ padding: '24px', background: '#fff', minHeight: '360px' }">
      <a-row>
        <a-col :span="6">

          <a-tree
              v-if="level1.length > 0"
              :tree-data="level1"
              @select = "onSelect"
              :replaceFields="{title:'name',key:'id',value:'id'}"
              :defaultExpandAll = "true"
          >
          </a-tree>

        </a-col>

        <a-col :span="18">

        </a-col>
      </a-row>


    </a-layout-content>
  </a-layout>
</template>

<script lang="ts" setup>
import {onMounted,ref,createVNode} from "vue";
import axios from "axios";
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import {useRoute} from "vue-router";

const route = useRoute();
const docs = ref();

const level1 = ref();
level1.value = [];

const handleQuery = () => {
  level1.value = [];
  //如果不清空现有数据，编辑保存重新加载数据后，再点编辑
  axios.get("/doc/all/"+route.query.ebookId).then((response) => {
    const data = response.data;
    if (data.success){
      docs.value = data.content;

      level1.value = [];
      level1.value = Tool.array2Tree(docs.value,0);

    }else {
      message.error(data.message);
    }
  });
};

onMounted(() => {
  handleQuery();
})


</script>
