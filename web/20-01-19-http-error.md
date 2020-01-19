# 轮询操作造成的异常

[https://blog.csdn.net/jobjava/article/details/28596835](https://blog.csdn.net/jobjava/article/details/28596835)

**java.net.NoRouteToHostException: Cannot assign requested address**

在进行循环里不断发出http请求时也会出现这种情况。

```java
while(notOk){
  doHttpRequest();
}
```

解决办法：

执行命令修改如下2个内核参数 

sysctl -w net.ipv4.tcp_timestamps=1  开启对于TCP时间戳的支持,若该项设置为0，则下面一项设置不起作用

sysctl -w net.ipv4.tcp_tw_recycle=1  表示开启TCP连接中TIME-WAIT sockets的快速回收
