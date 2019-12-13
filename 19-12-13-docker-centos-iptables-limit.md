因为涉及安全问题，禁止了容器访问外网，配置步骤如下：

1. 需要安装iptables-services, centos7默认使用firewalld防火墙
2. 启动iptable防火墙(注意先停掉firewalld)：`systemctl start iptables`
3. 重启docker服务，加载其规则链：`systemctl restart docker`
4. 当容器跑起来后，查看使用的桥接网络网卡：这里是br-xxx，不同网络可能不一样
```s
    # ip a
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host 
       valid_lft forever preferred_lft forever
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1450 qdisc mq state UP group default qlen 1000
	xxx
3: docker0: <NO-CARRIER,BROADCAST,MULTICAST,UP> mtu 1500 qdisc noqueue state DOWN group default 
    link/ether 02:42:14:84:5b:53 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 scope global docker0
       valid_lft forever preferred_lft forever
    inet6 fe80::42:14ff:fe84:5b53/64 scope link 
       valid_lft forever preferred_lft forever
4: br-xxx: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default 
    link/ether 02:42:17:4a:bd:9c brd ff:ff:ff:ff:ff:ff
    inet 172.18.0.1/16 scope global br-xxx
       valid_lft forever preferred_lft forever
    inet6 fe80::42:17ff:fe4a:bd9c/64 scope link 
       valid_lft forever preferred_lft forever
```
现在限制容器访问外网：基本思路是先限制所有到docker网卡的流量，然后开放特定几个允许访问用于业务
```s
# 允许内网IP段访问(假设你的就是这个), 不然其他机器不能访问它
iptables -I INPUT -s 192.168.1.1/16 -j ACCEPT

# 禁止所有非docker网卡到docker网卡的流量
iptables -A DOCKER ! -i br-xxx -o br-xxx -j DROP

# 然后允许特定IP到docker网卡, 注意，这2条一定在禁止那条之前(所以用`-I`)，否则不生效
iptables -I DOCKER -s 192.168.1.1/16 -o br-xxx -j ACCEPT
iptables -I DOCKER -d 192.168.1.1/16 -o br-xxx -j ACCEPT
```
