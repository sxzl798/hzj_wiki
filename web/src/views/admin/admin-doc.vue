<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
      <a-form
          layout="inline" :model="param"
      >
        <a-form-item>
          <a-input v-model:value="param.name" placeholder="名称">
            <template ><UserOutlined style="color: rgba(0, 0, 0, 0.25)" /></template>
          </a-input>
        </a-form-item>

        <a-form-item>

          <a-button type="primary"
                    :icon="h(SearchOutlined)"
                    @click="handleQueryDoc()"
          >
            查询
          </a-button>

        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="add()">
            新增
          </a-button>
        </a-form-item>


      </a-form>
      </p>

      <a-table
          :columns="columns"
          :data-source="level1"
          :row-key="record=>record.id"
          :loading="loading"
          :pagination="false"
          :scroll="{ x: 1300, y: 1000 }"
      >
        <template #headerCell="{ column }">
          <template v-if="column.key === 'name'">
        <span>
          <smile-outlined />
          Name
        </span>
          </template>
        </template>

        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'name'">
            <a>
              {{ record.name }}
            </a>
          </template>

          <template v-if="column.key === 'cover'" >
            <img :src=record.cover>
          </template>

          <template v-else-if="column.key === 'tags'">
        <span>
          <a-tag
              v-for="tag in record.tags"
              :key="tag"
              :color="tag === 'loser' ? 'volcano' : tag.length > 5 ? 'geekblue' : 'green'"
          >
            {{ tag.toUpperCase() }}
          </a-tag>
        </span>
          </template>

          <template v-else-if="column.key === 'action'">
            <a-space size="small">
              <a-button type="primary" @click="edit(record)">
                编辑
              </a-button>
              <a-popconfirm
                  title="删除后不可恢复，是否删除？"
                  ok-text="是"
                  cancel-text="否"
                  @confirm="showConfirm(record.id)"
              >
<!--              "handleDelete(record.id)"-->
                <a-button danger>
                  删除
                </a-button>
              </a-popconfirm>

            </a-space>
          </template>

        </template>
      </a-table>

    </a-layout-content>
  </a-layout>

  <a-modal v-model:open="open" title="文档编辑" :confirm-loading="confirmLoading" @ok="handleOk">
    <a-form :model="doc" :label-col="labelCol" :wrapper-col="wrapperCol">

      <a-form-item label="名称">
        <a-input v-model:value="doc.name" />
      </a-form-item>

      <a-form-item label="父文档">
        <a-tree-select
            v-model:value="doc.parent"
            show-search
            style="width: 100%"
            :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
            placeholder="请选择父文档"
            allow-clear
            tree-default-expand-all
            :tree-data="treeSelectData"
            tree-node-filter-prop="name"
            :replaceFields="{label:'name',value:'id'}"
        >

        </a-tree-select>

      </a-form-item>
      <a-form-item label="顺序">
        <a-input v-model:value="doc.sort" />
      </a-form-item>

      <a-form-item label="内容">
        <div style="border: 1px solid #ccc">
          <Toolbar
              style="border-bottom: 1px solid #ccc"
              :editor="editorRef"
              :defaultConfig="toolbarConfig"

          />
          <Editor
              style="height: 500px; overflow-y: hidden;"
              v-model="valueHtml"
              :defaultConfig="editorConfig"

              @onCreated="handleCreated"
          />
        </div>
      </a-form-item>

<!--      <a-form-item :wrapper-col="{ span: 14, offset: 4 }">-->
<!--        <a-button type="primary" @click="onSubmit">Create</a-button>-->
<!--        <a-button style="margin-left: 10px">Cancel</a-button>-->
<!--      </a-form-item>-->
    </a-form>
  </a-modal>

</template>
<script lang="ts" setup>
import {onMounted ,ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import { UserOutlined } from '@ant-design/icons-vue';
import { h } from 'vue';
import { SearchOutlined } from '@ant-design/icons-vue';
import {Tool} from "@/util/tool";
import {useRoute} from "vue-router";
import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
import { createVNode } from 'vue';
import { Modal } from 'ant-design-vue';
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import { onBeforeUnmount, shallowRef } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

const docs = ref();
const loading= ref<boolean>(false);

const open = ref<boolean>(false);
const confirmLoading = ref<boolean>(false);
const doc = ref({});

const route = useRoute()

//内联表单
const param = ref();
param.value={};

//内联表单

    //table
/**
 * 一级文档树，children属性就是二级文档
 * [{
 *   id:"",
 *   name:"",
 *   children:[{
 *     id:"",
 *     name:"",
 *   }]
 * }]
 */
const level1 = ref();

//因为树选择组件的属性状态，会随当前编辑的节点而变化，所以单独声明一个响应式变量
const treeSelectData = ref();
treeSelectData.value = [];

    //数据查询
    const handleQueryDoc = () => {
      loading.value = true;
      level1.value=[];
      axios.get("/doc/all").then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success){
          docs.value = data.content;
          console.log("原始数组：",docs.value);

          level1.value = [];
          level1.value = Tool.array2Tree(docs.value,0);
          console.log("树形结构：",level1.value);

        }else {
          message.error(data.message);
        }

      });
    };




    const columns = [

      {
        title: '名称',
        dataIndex: 'name',
        key: 'name',
        fixed: 'left',
        width: 200
      },
      {
        title: '父文档',
        dataIndex: 'parent',
        key: 'parent',
        width: 150
      },
      {
        title: '顺序',
        dataIndex: 'sort',
        key: 'sort',
        width: 150
      },
      {
        title: 'Action',
        key: 'action',
        width: 150
      },
    ];


    //table
/**
 *将某节点及其子孙节点全部置为disable
 * @param record
 */

const setDisable = (treeSelectData: any,id: any) => {
  // console.log(treeSelectData,id)
  //遍历数组，即遍历某一层节点
  for( let i = 0;i<treeSelectData.length;i++){
    const node = treeSelectData[i];
    if (node.id === id){
      //如果当前节点就是目标节点
      console.log("disabled",node);
      //将目标节点设置为disabled
      node.disabled = true;
      //遍历所有子节点，将所有子节点全部都加上disabled
      const children = node.children;
      if (Tool.isNotEmpty(children)){
        for (let j =0;j< children.length;j++){
          setDisable(children,children[j].id);
        }
      }
    }else{
      //如果当前节点不是目标节点，则到其子节点再找找看
      const children =node.children;
      if (Tool.isNotEmpty(children)){
        setDisable(children,id);
      }
    }
  }
}

/**
 * 查找整根树枝
 */

let ids: Array<string> = [];

let names: Array<string> = [];

//声明为const是因为这里是数组类型，为引用类型，所以这个ids是不会变化的，所以用常量即可

const getDeleteIds = (treeSelectData: any,id: any) => {
  // console.log(treeSelectData,id)
  //遍历数组，即遍历某一层节点
  for( let i = 0;i<treeSelectData.length;i++){
    const node = treeSelectData[i];
    if (node.id === id){
      //如果当前节点就是目标节点
      console.log("disabled",node);
      //将目标id放入结果集ids中
      ids.push(id);
      names.push(node.name);
      //遍历所有子节点，将所有子节点全部都加上disabled
      const children = node.children;
      if (Tool.isNotEmpty(children)){
        for (let j =0;j< children.length;j++){
          getDeleteIds(children,children[j].id);
        }
      }
    }else{
      //如果当前节点不是目标节点，则到其子节点再找找看
      const children =node.children;
      if (Tool.isNotEmpty(children)){
        getDeleteIds(children,id);
      }
    }
  }
}


//modal

    //编辑
    const edit = (record :any) => {
      open.value = true;
      doc.value = Tool.copy(record);

      //不能选择当前节点及其所有子孙节点，作为父节点，会使树断开
      treeSelectData.value = Tool.copy(level1.value);
      setDisable(treeSelectData.value,record.id);
      //为选择树添加一个“无”
      treeSelectData.value.unshift({id:0,name:'无'});
    };
    //新增
    const add = () => {
      open.value = true;
      doc.value = {
        ebookId: route.query.ebookId
      };

      //不能选择当前节点及其所有子孙节点，作为父节点，会使树断开
      treeSelectData.value = Tool.copy(level1.value);
      //为选择树添加一个“无”
      treeSelectData.value.unshift({id:0,name:'无'});
    };

    //删除
    const handleDelete= (id: number) => {
      ids = [];
      names = [];
      getDeleteIds(level1.value,id);
      axios.delete("/doc/delete/"+ids.join(",")).then((response)=>{
        const data = response.data;
        if (data.success){
          // router.go(0);
          //重新加载列表
          handleQueryDoc();

        }
      });
    };


    const handleOk = () => {
      confirmLoading.value = true;
      axios.post("/doc/save",doc.value).then((response)=>{
        confirmLoading.value=false;
        const data = response.data;
        if (data.success){
          open.value = false;
          //重新加载列表
          handleQueryDoc();
        }else {
          message.error(data.message);
        }
      });
    };

    //modal

//form
//数据源



const labelCol = { style: { width: '150px' } };
const wrapperCol = { span: 14 };
//form

const showConfirm = (id:number) => {
  ids = [];
  names = [];
  getDeleteIds(level1.value,id);
  Modal.confirm({
    title: '确定删除下列文档吗？',
    icon: createVNode(ExclamationCircleOutlined),
    content: createVNode('div', { style: 'color:red;' }, names.join(", ")),
    onOk() {
      console.log('OK');
      handleDelete(id);
    },
    onCancel() {
      console.log('Cancel');
    },
    class: 'test',

  });
};

// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()

// 内容 HTML
const valueHtml = ref('<p>hello</p>')

// 模拟 ajax 异步获取内容
onMounted(() => {
  setTimeout(() => {
    valueHtml.value = '<p>模拟 Ajax 异步设置内容</p>'
  }, 1500)
})

const toolbarConfig = {}
const editorConfig = { placeholder: '请输入内容...' }

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})

const handleCreated = (editor:any) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}




    onMounted(() => {
      handleQueryDoc();
    });

</script>
<style scoped>
img{
  height: 50px;
  width: 50px;
}
</style>
