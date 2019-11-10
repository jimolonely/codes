docker容器与主机安全

在docker容器内可以扫描到主机开放了哪些端口：

```java
# nc -zv 172.17.0.1 1-10000
172.17.0.1: inverse host lookup failed: Unknown host
(UNKNOWN) [172.17.0.1] 82 (?) open
(UNKNOWN) [172.17.0.1] 81 (?) open
(UNKNOWN) [172.17.0.1] 80 (?) open
(UNKNOWN) [172.17.0.1] 22 (?) open
```

这样看上去不太好。

# 查看iptable规则

http://www.west999.com/info/html/caozuoxitong/Linux/20190923/4659433.html

在宿主机上检查，如果docker使用了`-p`映射端口的话就会有：

```s
iptables --list
```

# 2375端口

http://www.dockerinfo.net/1416.html

# python扫描验证

http://cn.voidcc.com/question/p-nyotrvrk-bhn.html


# 防止nmap扫描

https://www.zhl123.com/index/article/show/id/99.html

# docker的iptable链

https://blog.csdn.net/taiyangdao/article/details/88844558


