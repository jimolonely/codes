# js无法删除cookie问题

参考此文：https://www.cnblogs.com/gossip/archive/2011/12/06/2278282.html

原来cookie除了 name一样外，还有其他部分也需要相同才能定位一个cookie，如下：

```js
document.cookie = "name=;expire=0;path=/;domain=.xxx.com";
```
