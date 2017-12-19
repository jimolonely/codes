# zookeeper
[http://zookeeper.apache.org/](http://zookeeper.apache.org/)

学习参考：
[https://www.w3cschool.cn/zookeeper/](https://www.w3cschool.cn/zookeeper/)

## 1.下载
```shell
[jimo@jimo-pc zookeeper]$ wget http://ftp.cuhk.edu.hk/pub/packages/apache.org/zookeeper/stable/zookeeper-3.4.10.tar.gz

[jimo@jimo-pc zookeeper]$ tar -zxf zookeeper-3.4.10.tar.gz

[jimo@jimo-pc zookeeper-3.4.10]$ mkdir data
```
## 2.配置
```shell
[jimo@jimo-pc zookeeper-3.4.10]$ vim conf/zoo.cfg

tickTime = 2000
dataDir = /home/jimo/workspace/temp/zookeeper/zookeeper-3.4.10/data
clientPort = 2181
initLimit = 5
syncLimit = 2
```
## 3.启动zookeeper服务器
```shell
[jimo@jimo-pc zookeeper-3.4.10]$ bin/zkServer.sh start
ZooKeeper JMX enabled by default
Using config: /home/jimo/workspace/temp/zookeeper/zookeeper-3.4.10/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED
```
当然停止是stop
## 4.启动客户端
```shell
[jimo@jimo-pc zookeeper-3.4.10]$ bin/zkCli.sh 
Connecting to localhost:2181
...
Welcome to ZooKeeper!
...
WATCHER::

WatchedEvent state:SyncConnected type:None path:null
[zk: localhost:2181(CONNECTED) 0] 1
ZooKeeper -server host:port cmd args
	stat path [watch]
	set path data [version]
	ls path [watch]
	delquota [-n|-b] path
	ls2 path [watch]
	setAcl path acl
	setquota -n|-b val path
	history 
	redo cmdno
	printwatches on|off
	delete path [version]
	sync path
	listquota path
	rmr path
	get path [watch]
	create [-s] [-e] path data acl
	addauth scheme auth
	quit 
	getAcl path
	close 
	connect host:port
```
## 5.使用客户端
客户端的命令如上，可以：
```
1、创建znode
2、获取数据
3、监视znode的变化
4、设置数据
5、创建znode的子节点
6、列出znode的子节点
7、检查状态
8、移除/删除znode
```
### 5.1 创建节点
有3种节点，默认是持久。
```shell
[zk: localhost:2181(CONNECTED) 4] create /FirstZnode "first-app" 
Created /FirstZnode
[zk: localhost:2181(CONNECTED) 5] create -s /FirstZnode "second-data"
Created /FirstZnode0000000001
[zk: localhost:2181(CONNECTED) 6] create -e /FirstZnode "three-data" 
Node already exists: /FirstZnode
[zk: localhost:2181(CONNECTED) 7] create -e /SecondZnode "three-data"
Created /SecondZnode
```
### 5.2 获取数据
```shell
[zk: localhost:2181(CONNECTED) 8] get /FirstZnode
first-app
cZxid = 0x2
ctime = Mon Nov 20 11:56:16 CST 2017
mZxid = 0x2
mtime = Mon Nov 20 11:56:16 CST 2017
pZxid = 0x2
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 9
numChildren = 0
[zk: localhost:2181(CONNECTED) 9] get /FirstZnode0000000001
second-data
cZxid = 0x3
ctime = Mon Nov 20 11:57:03 CST 2017
mZxid = 0x3
mtime = Mon Nov 20 11:57:03 CST 2017
pZxid = 0x3
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 11
numChildren = 0
```
### 5.3 watch
```shell
[zk: localhost:2181(CONNECTED) 10] get /FirstZnode 1   
```
### 5.4 设置数据
```shell
[zk: localhost:2181(CONNECTED) 11] set /SecondZnode Data-updated
cZxid = 0x5
ctime = Mon Nov 20 11:57:58 CST 2017
mZxid = 0x6
mtime = Mon Nov 20 12:03:02 CST 2017
pZxid = 0x5
cversion = 0
dataVersion = 1
aclVersion = 0
ephemeralOwner = 0x15fd2e4c8970000
dataLength = 12
numChildren = 0
```
### 5.5 创建子项
```shell
[zk: localhost:2181(CONNECTED) 13] create /FirstZnode/Child1 firstChild
Created /FirstZnode/Child1
[zk: localhost:2181(CONNECTED) 14] create /FirstZnode/Child2 secondChild
Created /FirstZnode/Child2
```
### 5.6 列出子项
```shell
[zk: localhost:2181(CONNECTED) 15] ls /FirstZnode
[Child2, Child1]
```
### 5.7 检查状态
```shell
[zk: localhost:2181(CONNECTED) 16] stat /FirstZnode
cZxid = 0x2
ctime = Mon Nov 20 11:56:16 CST 2017
mZxid = 0x2
mtime = Mon Nov 20 11:56:16 CST 2017
pZxid = 0x8
cversion = 2
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 9
numChildren = 2
```
### 5.8 移除节点
```shell
[zk: localhost:2181(CONNECTED) 18] rmr /FirstZnode

WATCHER::
[zk: localhost:2181(CONNECTED) 19] 
WatchedEvent state:SyncConnected type:NodeDeleted path:/FirstZnode

[zk: localhost:2181(CONNECTED) 19] get /FirstZnode          
Node does not exist: /FirstZnode
```
## 6.zookeeper客户端
使用Java或C#调用zookeeper的API充当客户端，具体使用见：
[https://www.w3cschool.cn/zookeeper/zookeeper_api.html](https://www.w3cschool.cn/zookeeper/zookeeper_api.html)
## 7.实例分析
建一个java project，导入zookeeper的jar包，然后运行这个生产消费者模型：
[https://cwiki.apache.org/confluence/display/ZOOKEEPER/Tutorial](https://cwiki.apache.org/confluence/display/ZOOKEEPER/Tutorial)


