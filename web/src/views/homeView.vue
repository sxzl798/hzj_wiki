<template>
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
          @click = "handleClick"

      >
        <a-menu-item key="welcome">
          <router-link to="/">
            <SmileTwoTone />
              <span>欢迎</span>
          </router-link>
        </a-menu-item>

        <a-sub-menu v-for="item in level1" :key="item.id">
          <template v-slot:title>
            <span><TagsTwoTone />{{item.name}}</span>
          </template>
          <a-menu-item v-for="child in item.children" :key="child.id">
            <TagTwoTone /><span>{{child.name}}</span>
          </a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <a-list item-layout="vertical" size="large" :grid="{ gutter: 16, column: 2 }" :pagination="pagination" :data-source="ebooks">

        <template #renderItem="{ item }">
          <a-list-item key="item.name">
            <template #actions>
          <span v-for="{ icon, text } in actions" :key="icon">
            <component :is="icon" style="margin-right: 8px" />
            {{ text }}
          </span>
            </template>

            <a-list-item-meta :description="item.description">
              <template #title>
                <a :href="item.href">{{ item.name }}</a>
              </template>
              <template #avatar><a-avatar :src="item.cover" /></template>
            </a-list-item-meta>
          </a-list-item>
        </template>
      </a-list>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts" setup>
import { onMounted ,ref} from 'vue';
import { StarOutlined, LikeOutlined, MessageOutlined } from '@ant-design/icons-vue';
import axios from "axios";
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";

const actions: Record<string, any>[] = [
  { icon: StarOutlined, text: '156' },
  { icon: LikeOutlined, text: '156' },
  { icon: MessageOutlined, text: '2' },
];
const ebooks = ref();
// const ebooks1 = reactive({books:[]});

const level1 = ref();
let categorys: any;

//数据查询
const handleQueryCategory = () => {
  axios.get("/category/all").then((response) => {
    const data = response.data;
    if (data.success){
      categorys = data.content;
      console.log("原始数组：",categorys);

      level1.value = [];
      level1.value = Tool.array2Tree(categorys,0);
      console.log("树形结构：",level1.value);

      axios.get("/ebook/list",{
        params:{
          page:1,
          size:10
        }
      }).then((response)=>{
        const data = response.data;
        ebooks.value = data.content.list;
        ebooks.value.total = data.content.total;
      });
    }else {
      message.error(data.message);
    }

  });
};

const handleClick = () =>{
  console.log("menu click")
};


onMounted(()=>{
  handleQueryCategory();

});


</script>

<style scoped>
  .ant-avatar{
    width: 50px;
    height: 50px;
    line-height: 50px;
    border-radius: 8%;
    margin: 5px 0;
  }
</style>
