<template>
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
          @click = "handleClick"
      >
        <a-menu-item key="welcome" @click = "handleClickItem_isShowWelcome">
<!--          <router-link to="/">-->
            <SmileTwoTone />
            <span>欢迎</span>
<!--          </router-link>-->

        </a-menu-item>

        <a-sub-menu v-for="item in level1" :key="item.id">
          <template v-slot:title>
            <a-button type="text" @click="handleClickItem(item.id)">
              <TagsTwoTone /><span >{{item.name}}</span>
            </a-button>

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
      <div class="welcome" v-show = "isShowWelcome">
        <the-welcome></the-welcome>
      </div>

      <a-list v-show="!isShowWelcome"
          item-layout="vertical" size="large"
              :grid="{ gutter: 16, column: 2 }"
              :data-source="ebooks"
      >

        <template #renderItem="{ item }">
          <a-list-item key="item.name">
            <template #actions>
<!--          <span v-for="{ icon, text } in actions" :key="icon">-->
<!--            <component :is="icon" style="margin-right: 8px" />-->
<!--            {{ text }}-->
<!--          </span>-->
              <span>
                <component v-bind:is="FileOutlined" style="margin-right: 8px"/>
                {{item.docCount}}
              </span>
              <span>
                <component v-bind:is="UserOutlined" style="margin-right: 8px"/>
                {{item.viewCount}}
              </span>
              <span>
                <component v-bind:is="LikeOutlined" style="margin-right: 8px"/>
                {{item.voteCount}}
              </span>
            </template>

            <a-list-item-meta :description="item.description">
              <template #title>
                <router-link :to="'/doc?ebookId='+item.id">
                  {{ item.name }}
                </router-link>
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
import {defineComponent, onMounted, ref} from 'vue';
import {StarOutlined, LikeOutlined, MessageOutlined, FileOutlined, UserOutlined} from '@ant-design/icons-vue';
import axios from "axios";
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import TheWelcome from "@/components/the-welcome.vue";
import TheHeader from "@/components/the-header.vue";
import TheFooter from "@/components/the-footer.vue";


const ebooks = ref();
// const ebooks1 = reactive({books:[]});

const level1 = ref();
const isShowWelcome = ref(true);
let categorys: any;

let categoryId2 = 0;
let categoryId = 0;

const handleQueryEbook = () =>{
  axios.get("/ebook/list",{
    params:{
      page:1,
      size:10,
    }
  }).then((response)=>{
    const data = response.data;
    ebooks.value = data.content.list;
    ebooks.value.total = data.content.total;
  });
}
const handleQueryEbookByCategoryId2 = () =>{
  axios.get("/ebook/list",{
    params:{
      page:1,
      size:10,
      category2Id:categoryId2,
    }
  }).then((response)=>{
    const data = response.data;
    ebooks.value = data.content.list;
    ebooks.value.total = data.content.total;
  });
}
const handleQueryEbookByCategoryId = () =>{
  axios.get("/ebook/list",{
    params:{
      page:1,
      size:10,
      categoryId:categoryId,
    }
  }).then((response)=>{
    const data = response.data;
    ebooks.value = data.content.list;
    ebooks.value.total = data.content.total;
  });
}

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
      handleQueryEbook();
    }else {
      message.error(data.message);
    }

  });
};

const handleClick = (value:any) =>{
  // console.log("menu click")
  if (!(value.key === 'welcome')){
    categoryId2 = value.key;
    handleQueryEbookByCategoryId2();
  }else {
    handleQueryEbook();
  }

};

const handleClickItem = (value:number) =>{
  console.log(value);
  categoryId=value;
  handleQueryEbookByCategoryId();
}

const handleClickItem_isShowWelcome = () =>{
  isShowWelcome.value = false;
}



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
