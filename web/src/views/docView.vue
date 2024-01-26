<template>
  <a-layout>
    <a-layout-content :style="{ padding: '24px', background: '#fff', minHeight: '360px' }">

      <a-row>
        <a-col :span="4">

          <a-tree
              v-if="level1.length > 0"
              :tree-data="level1"
              @select = "onSelect"
              :fieldNames="{title:'name',key:'id',value:'id'}"
              :defaultExpandAll = "true"
              :defaultSelectedKeys="defaultSelectedKeys"
          >
          </a-tree>

        </a-col>

        <a-col :span="20">
          <h3 v-if="level1.length===0">无文档</h3>
          <div class="editor-content-view" :innerHTML="html">

          </div>

        </a-col>
      </a-row>


    </a-layout-content>
  </a-layout>
</template>

<script lang="ts" setup>
import {onMounted,ref} from "vue";
import axios from "axios";
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import {useRoute} from "vue-router";

const route = useRoute();
const docs = ref();

const level1 = ref();
level1.value = [];

const html = ref();

const defaultSelectedKeys = ref();
defaultSelectedKeys.value=[];

//内容查询
const handleQueryContent = (id:number) => {

    axios.get("/doc/find-content/"+id).then((response) => {
      const data = response.data;
      if (data.success){
        html.value = data.content;
      }else {
        message.error(data.message);
      }
    });
};

const handleQuery = () => {
  // console.log("route取得的id：",route.query.ebookId);
    level1.value = [];
    //如果不清空现有数据，编辑保存重新加载数据后，再点编辑
    axios.get("/doc/all/"+route.query.ebookId).then((response) => {
      const data = response.data;
      if (data.success){
        if (Tool.isEmpty(data.content)){
          message.warn("缺少相关文档");
        }else {
          docs.value = data.content;

          level1.value = [];
          level1.value = Tool.array2Tree(docs.value,0);

          console.log('level1.value[0]-id:',level1.value[0].id);
          if (Tool.isNotEmpty(level1)){
            console.log('level1.value[0]-id:',level1.value[0].id);
            defaultSelectedKeys.value = [level1.value[0].id];
            handleQueryContent(level1.value[0].id);
          }

        }
        }else {
        message.error(data.message);
      }

    });
};

const onSelect = (selectKeys: any) => {
  console.log('selected',selectKeys.info);
  if (Tool.isNotEmpty(selectKeys)){
    //加载内容
    handleQueryContent(selectKeys[0]);
  }
}

onMounted(() => {
  handleQuery();
})


</script>

<style>
.editor-content-view {
  border: 3px solid #ccc;
  border-radius: 5px;
  padding: 0 10px;
  margin-top: 20px;
  overflow-x: auto;
}

.editor-content-view p,
.editor-content-view li {
  white-space: pre-wrap;/* 保留空格 */
}

.editor-content-view blockquote {
  border-left: 8px solid #d0e5f2;
  padding: 10px 10px;
  margin: 10px 0;
  background-color: #f1f1f1;
}

.editor-content-view code {
  font-family: monospace;
  background-color: #eee;
  padding: 3px;
  border-radius: 3px;
}
.editor-content-view pre>code {
  display: block;
  padding: 10px;
}

.editor-content-view table {
  border-collapse: collapse;
}
.editor-content-view td,
.editor-content-view th {
  border: 1px solid #ccc;
  min-width: 50px;
  height: 20px;
}
.editor-content-view th {
  background-color: #f1f1f1;
}

.editor-content-view ul,
.editor-content-view ol {
  padding-left: 20px;
}

.editor-content-view input[type="checkbox"] {
  margin-right: 5px;
}

.editor-content-view blockquote p{
  font-family: "Ubuntu Mono" !important;
  margin: 20px 10px !important;
  font-size: 16px !important;
  font-weight: 600;
}
</style>
