
# 伪分布式
即只有一个节点，每个hadoop守护进程都运行在分离的java进程里。
## 1.配置文件
etc/hadoop/core-site.xml:
```shell
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://localhost:9000</value>
    </property>
</configuration>
```
etc/hadoop/hdfs-site.xml:
```shell
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
</configuration>
```
## 2.检查ssh
正常：
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ ssh localhost 
Welcome to Ubuntu 17.10 (GNU/Linux 4.13.0-17-generic x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/advantage

 * Ubuntu Community Appreciation Day
   On November 20th we celebrate the diverse community talents that complement
   Canonical's work on Ubuntu and make it so much more interesting than just an
   enterprise Linux. Together, we make Ubuntu amazing.
   - https://ubu.one/ucaday
```
如果出现：
```shell
localhost: ssh: connect to host localhost port 22: Connection refused
```
在ubuntu上可能是因为没有sshd，只需要安装完整即可：
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ sudo apt install openssh-server 
```
如果是密码问题，官方做法：
```shell
  $ ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
  $ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
  $ chmod 0600 ~/.ssh/authorized_keys
```
## 3.启动HDFS环境
先格式化文件系统：
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hdfs namenode -format
17/11/28 14:57:50 INFO namenode.NameNode: STARTUP_MSG: 
/************************************************************
STARTUP_MSG: Starting NameNode
STARTUP_MSG:   host = jimo-ubuntu/10.0.2.15
STARTUP_MSG:   args = [-format]
STARTUP_MSG:   version = 2.9.0
...
17/11/28 14:57:52 INFO namenode.NameNode: SHUTDOWN_MSG: 
/************************************************************
SHUTDOWN_MSG: Shutting down NameNode at jimo-ubuntu/10.0.2.15
************************************************************/
```
再启动：
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ sbin/start-dfs.sh 
Starting namenodes on [localhost]
localhost: starting namenode, logging to /home/jimo/hadoop/hadoop-2.9.0/logs/hadoop-jimo-namenode-jimo-ubuntu.out
localhost: starting datanode, logging to /home/jimo/hadoop/hadoop-2.9.0/logs/hadoop-jimo-datanode-jimo-ubuntu.out
Starting secondary namenodes [0.0.0.0]
0.0.0.0: starting secondarynamenode, logging to /home/jimo/hadoop/hadoop-2.9.0/logs/hadoop-jimo-secondarynamenode-jimo-ubuntu.out
```
现在打开浏览器：http://localhost:50070/ 就知道结果正确了。
## 4.执行MapReduce任务
1. 创建HDFS文件夹
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hdfs dfs -mkdir /user
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hdfs dfs -mkdir /user/first

jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hdfs dfs -ls -R /
drwxr-xr-x   - jimo supergroup          0 2017-11-28 15:24 /user
drwxr-xr-x   - jimo supergroup          0 2017-11-28 15:24 /user/first
```
2. 准备输入数据，还是使用standalone的例子，先清除output目录

执行：
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.9.0.jar wordcount input output
```
当然，你会得到以下错误：
```shell
org.apache.hadoop.mapreduce.lib.input.InvalidInputException: Input path does not exist: hdfs://localhost:9000/user/jimo/input
```
即user/first下没有input目录，所以需要放进去：
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hdfs dfs -put etc/hadoop/* input
put: `input': No such file or directory
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hdfs dfs -mkdir /user/first/input
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hdfs dfs -put etc/hadoop/* /user/first/input
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hdfs dfs -ls -R /
drwxr-xr-x   - jimo supergroup          0 2017-11-28 15:24 /user
drwxr-xr-x   - jimo supergroup          0 2017-11-28 15:35 /user/first
drwxr-xr-x   - jimo supergroup          0 2017-11-28 15:35 /user/first/input
-rw-r--r--   1 jimo supergroup       7861 2017-11-28 15:35 /user/first/input/capacity-scheduler.xml
-rw-r--r--   1 jimo supergroup       1335 2017-11-28 15:35 /user/first/input/configuration.xsl
...
```
然后重新执行命令：
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.9.0.jar wordcount /user/first/input output
```
注意是：/user/first/input，而output是默认目录，可以这样查看：
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hdfs dfs -ls 
Found 1 items
drwxr-xr-x   - jimo supergroup          0 2017-11-28 15:38 output
```
现在可以把它导出来，也可以直接查看结果：
```shell
# 导出来
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hdfs dfs -get output output
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ ls
bin  etc  include  input  lib  libexec  LICENSE.txt  logs  NOTICE.txt  output  README.txt  sbin  share
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ cat output/* | head -n 5
!=	3
""	9
"".	4
"$HADOOP_CLASSPATH"	2
"$HADOOP_HEAPSIZE"	2

# 直接查看
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hdfs dfs -cat output/* | head -n 5
!=	3
""	9
"".	4
"$HADOOP_CLASSPATH"	2
"$HADOOP_HEAPSIZE"	2
cat: Unable to write to output stream.
```
## 5.关闭服务
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ sbin/stop-dfs.sh
Stopping namenodes on [localhost]
localhost: stopping namenode
localhost: stopping datanode
Stopping secondary namenodes [0.0.0.0]
0.0.0.0: stopping secondarynamenode
```
可以看到停止的顺序。