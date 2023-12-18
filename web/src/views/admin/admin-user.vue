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
            <a-input v-model:value="param.loginName" placeholder="名称">
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
          :data-source="users"
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

  <a-modal v-model:open="open" title="用户编辑" :confirm-loading="confirmLoading" @ok="handleOk">
    <a-form :model="user" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="昵称">
        <a-input v-model:value="user.name" />
      </a-form-item>
      <a-form-item label="用户名">
        <a-input v-model:value="user.loginName" />
      </a-form-item>
      <a-form-item label="密码">
        <a-input-password v-model:value="user.password" type="password"/>
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

const users = ref();
const loading= ref<boolean>(false);
const pagination = ref({
  current: 1,
  pageSize: 5,
  total: 0
});

const open = ref<boolean>(false);
const confirmLoading = ref<boolean>(false);
const user = ref({});

//内联表单
const param = ref();
param.value={};

//内联表单

//表单
/**
 * 数组[100,101]对应：前端开发/Vue
 */

//表单

    //table

    //数据查询
const handleQuery = (params: any) => {
      loading.value = true;
      axios.get("/user/list", {
        //将原本的params参数展开，成为get请求参数中的参数
        params:{
          page: params.page,
          size: params.size,
          loginName: param.value.loginName
        }
      }).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success){
          users.value = data.content.list;
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
    title: '用户名',
    dataIndex: 'loginName',
    key: 'loginName',
  },
  {
    name: '昵称',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '密码',
    dataIndex: 'password',
    key: 'password',
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
  user.value = Tool.copy(record);
};
//新增
const add = () => {
  open.value = true;
  user.value = {};
};

//删除
const handleDelete= (id: number) => {
  axios.delete("/user/delete/" + id).then((response)=>{
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

const handleOk = () => {
  confirmLoading.value = true;
  axios.post("/user/save",user.value).then((response)=>{
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
    size: pagination.value.pageSize,
  });
});

</script>
<style scoped>
img{
  height: 50px;
  width: 50px;
}
</style>
