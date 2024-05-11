<template>
  <a-layout-footer style="text-align: center">
    <P size="small" v-show="!!user.id">
      你好，{{user.name}}
    </P>
    Design ©2023 Created by Hzzzj
  </a-layout-footer>
</template>

<script lang="ts">
import {defineComponent,computed,onMounted} from "vue";
import store from "@/store";
import {Tool} from "@/util/tool";
import {message}  from "ant-design-vue";

export default defineComponent({
  name:'the-footer',
  setup(){
    const user = computed(()=> store.state.user);

    let websocket:any;
    let token:any;
    const onOpen = () => {
      console.log('websocket连接成功，状态码：', websocket.readyState);
    };

    const onMessage = (event:any) => {
      console.log('websocket接收到消息：', event.data);
      message.success('收到消息：'+event.data);
    };

    const onError = () => {
      console.log('websocket连接出错,状态码：',websocket.readyState);
    };

    const onClose = () => {
      console.log('websocket连接关闭,状态码：',websocket.readyState);
    };

    const initWebSocket = () => {
      //连接成功
      websocket.onopen = onOpen;
      //接收到消息
      websocket.onmessage = onMessage;
      //连接出错
      websocket.onerror = onError;
      //连接关闭
      websocket.onclose = onClose;
    };

    onMounted(() => {
      //WebSocket连接
      if ('WebSocket' in window){
        //获取token
        token = Tool.uuid(10);
        //连接地址：ws://localhost:8080/ws/xxx
        websocket = new WebSocket(process.env.VUE_APP_WS_SERVER + '/ws/'+token);
        initWebSocket();
        //关闭
        //websocket.close();
      }else {
        alert('当前浏览器不支持WebSocket')
      }
    });

    return{
      user
    }
  }
});

</script>