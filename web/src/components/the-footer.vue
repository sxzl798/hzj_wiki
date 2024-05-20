<template>
  <a-layout-footer style="text-align: center"  class="footer">
    <P size="small" v-show="!!user.id">
      欢迎，{{user.name}}
    </P>
    Design ©2024 Created by Hzj
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


<style scoped>
.content-wrapper {
  margin-bottom: 60px; /* 底部 footer 的高度 */
}

.footer {
  padding: 20px 0;
  background: #F5EFE6;
  margin-top: auto; /* 让 footer 在 flex 布局中自动对齐到容器底部 */
}


</style>