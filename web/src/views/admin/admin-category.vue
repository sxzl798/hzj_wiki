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
                    @click="handleQueryCategory()"
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
          v-if="lengthOfLevel1>0"
          :columns="columns"
          :data-source="level1"
          :row-key="record=>record.id"
          :loading="loading"
          :pagination="false"
          :defaultExpandAllRows = "true"
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
              <a-button  @click="edit(record)">
                编辑
              </a-button>
              <a-popconfirm
                  title="删除后不可恢复，是否删除？"
                  ok-text="是"
                  cancel-text="否"
                  @confirm="handleDelete(record.id)"
              >
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

  <a-modal v-model:open="open" title="分类编辑" :confirm-loading="confirmLoading" @ok="handleOk">
    <a-form :model="category" :label-col="labelCol" :wrapper-col="wrapperCol">

      <a-form-item label="名称">
        <a-input v-model:value="category.name" />
      </a-form-item>
      <a-form-item label="父分类">
        <a-select
            ref="select"
            v-model:value="category.parent"
        >
          <a-select-option value="0">无</a-select-option>
          <a-select-option v-for="c in level1"
                           :key="c.id"
                           :value="c.id"
                           :disabled="category.id === c.id"
          >
            {{c.name}}
          </a-select-option>

        </a-select>
      </a-form-item>
      <a-form-item label="顺序">
        <a-input v-model:value="category.sort" />
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

const categorys = ref();
const loading= ref<boolean>(false);

const open = ref<boolean>(false);
const confirmLoading = ref<boolean>(false);
const category = ref({});

//内联表单
const param = ref();
param.value={};

//内联表单

    //table
/**
 * 一级分类树，children属性就是二级分类
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
const lengthOfLevel1 = ref();

    //数据查询
    const handleQueryCategory = () => {
      loading.value = true;
      level1.value = [];
      //如果不清空现有数据，编辑保存重新加载数据后，再点编辑
      axios.get("/category/all").then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success){
          categorys.value = data.content;
          console.log("原始数组：",categorys.value);

          level1.value = [];
          level1.value = Tool.array2Tree(categorys.value,0);
          console.log("树形结构：",level1.value);

          lengthOfLevel1.value = level1.value.length;

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
      },
      {
        title: '父分类',
        dataIndex: 'parent',
        key: 'parent',
      },
      {
        title: '顺序',
        dataIndex: 'sort',
        key: 'sort',
      },
      {
        title: 'Action',
        key: 'action',
      },
    ];


    //table


//modal

    //编辑
    const edit = (record :any) => {
      open.value = true;
      category.value = Tool.copy(record);
    };
    //新增
    const add = () => {
      open.value = true;
      category.value = {};
    };

    //删除
    const handleDelete= (id: number) => {
      axios.delete("/category/delete/" + id).then((response)=>{
        const data = response.data;
        if (data.success){
          // router.go(0);
          //重新加载列表
          handleQueryCategory();

        }
      });
    };

    const handleOk = () => {
      confirmLoading.value = true;
      axios.post("/category/save",category.value).then((response)=>{
        confirmLoading.value=false;
        const data = response.data;
        if (data.success){
          open.value = false;
          //重新加载列表
          handleQueryCategory();
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





    onMounted(() => {
      handleQueryCategory();
    });

</script>
<style scoped>
img{
  height: 50px;
  width: 50px;
}
</style>
