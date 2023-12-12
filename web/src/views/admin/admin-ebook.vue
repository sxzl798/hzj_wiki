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
                    @click="handleQuery({page:1,size:pagination.pageSize})"
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
          :data-source="ebooks"
          :row-key="record=>record.id"
          :pagination="pagination"
          :loading="loading"
          @change = "handleTableChange"
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

  <a-modal v-model:open="open" title="电子表单编辑" :confirm-loading="confirmLoading" @ok="handleOk">
    <a-form :model="ebook" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="封面">
        <a-input v-model:value="ebook.cover" />
      </a-form-item>
      <a-form-item label="名称">
        <a-input v-model:value="ebook.name" />
      </a-form-item>
      <a-form-item label="分类一">
        <a-input v-model:value="ebook.categoryId" />
      </a-form-item>
      <a-form-item label="分类二">
        <a-input v-model:value="ebook.category2Id" />
      </a-form-item>
      <a-form-item label="描述">
        <a-textarea v-model:value="ebook.description" type="text"/>
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
import { reactive, toRaw } from 'vue';
import type { UnwrapRef } from 'vue';
import {message} from "ant-design-vue";
import router from "@/router";
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue';
import type { FormProps } from 'ant-design-vue';
import { h } from 'vue';
import { SearchOutlined } from '@ant-design/icons-vue';

    const ebooks = ref();
    const loading= ref<boolean>(false);
    const pagination = ref({
      current: 1,
      pageSize: 4,
      total: 0
    });

  const open = ref<boolean>(false);
  const confirmLoading = ref<boolean>(false);
  const ebook = ref({});

//内联表单
const param = ref();
param.value={};

//内联表单

    //table

    //数据查询
    const handleQuery = (params: any) => {
      loading.value = true;
      axios.get("/ebook/list", {
        //将原本的params参数展开，成为get请求参数中的参数
        params:{
          page: params.page,
          size: params.size,
          name: param.value.name
        }
      }).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success){
          ebooks.value = data.content.list;
          //重置分页按钮
          pagination.value.current = params.page;
          pagination.value.total = data.content.total;
        }else {
          message.error(data.message);
        }

      });
    };

    //表格点击页码时触发
    const handleTableChange = (pagination: any) => {
      console.log("看看自带的分页参数都有哪些：" + pagination);
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });
    };


    const columns = [
      {
        name: '封面',
        dataIndex: 'cover',
        key: 'cover',
      },
      {
        title: '名称',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '分类一',
        dataIndex: 'categoryId',
        key: 'categoryId',
      },
      {
        title: '分类二',
        dataIndex: 'category2Id',
        key: 'category2Id',
      },
      {
        title: '文档数',
        dataIndex: 'docCount',
        key: 'docCount',
      },
      {
        title: '阅读数',
        dataIndex: 'viewCount',
        key: 'viewCount',
      },
      {
        title: '点赞数',
        dataIndex: 'voteCount',
        key: 'voteCount',
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
      ebook.value = record;
    };
    //新增
    const add = () => {
      open.value = true;
      ebook.value = {};
    };

    //删除
    const handleDelete= (id: number) => {
      axios.delete("/ebook/delete/" + id).then((response)=>{
        const data = response.data;
        if (data.success){
          // router.go(0);
          //重新加载列表
          handleQuery({
            page:pagination.value.current,
            size:pagination.value.pageSize,
          });

        }
      });
    };

    const handleOk = (e: MouseEvent) => {
      confirmLoading.value = true;
      axios.post("/ebook/save",ebook.value).then((response)=>{
        confirmLoading.value=false;
        const data = response.data;
        if (data.success){
          open.value = false;
          //重新加载列表
          handleQuery({
            page:pagination.value.current,
            size:pagination.value.pageSize,
          });
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
      handleQuery({
        page: 1,
        size: pagination.value.pageSize
      });
    });

</script>
<style scoped>
img{
  height: 50px;
  width: 50px;
}
</style>
