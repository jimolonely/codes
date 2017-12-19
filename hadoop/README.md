# hadoop
hadoop修炼笔记.
## 环境准备
```
OS : 位于虚拟机的ubuntu17.10
JDK：openjdk1.8
hadoop：2.9
```
根据官方下载地址：[http://www-us.apache.org/dist/hadoop/common/current/](http://www-us.apache.org/dist/hadoop/common/current)

```shell
[jimo@jimo-pc hadoop]$ wget http://www-us.apache.org/dist/hadoop/common/current/hadoop-2.9.0.tar.gz
...
hadoop-2.9.0.tar.gz     100%[=============================>] 349.75M  1.02MB/s  用时 5m 59s  
2017-11-27 10:22:30 (999 KB/s) - 已保存 “hadoop-2.9.0.tar.gz” [366744329/366744329])

[jimo@jimo-pc hadoop]$ tar -zxf hadoop-2.9.0.tar.gz 
[jimo@jimo-pc hadoop]$ cd hadoop-2.9.0
[jimo@jimo-pc hadoop-2.9.0]$ ls
bin  etc  include  lib  libexec  LICENSE.txt  NOTICE.txt  README.txt  sbin  share
```
找到ubuntu的默认jdk位置，配置到etc/hadoop/hadoop-env.sh里：
```shell
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
```
查看hadoop命令：
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0/bin$ ./hadoop
Usage: hadoop [--config confdir] [COMMAND | CLASSNAME]
  CLASSNAME            run the class named CLASSNAME
 or
  where COMMAND is one of:
  fs                   run a generic filesystem user client
  version              print the version
  jar <jar>            run a jar file
                       note: please use "yarn jar" to launch
                             YARN applications, not this command.
  checknative [-a|-h]  check native hadoop and compression libraries availability
  distcp <srcurl> <desturl> copy file or directories recursively
  archive -archiveName NAME -p <parent path> <src>* <dest> create a hadoop archive
  classpath            prints the class path needed to get the
                       Hadoop jar and the required libraries
  credential           interact with credential providers
  daemonlog            get/set the log level for each daemon
  trace                view and modify Hadoop tracing settings

Most commands print help when invoked w/o parameters.
```

## [1.standalone](./01-standalone.md)
什么都不配的单节点运行MapReduce。

## [2.pseudo-distributed](./02-pseudo-distributed.md)
伪分布式下在本地运行MapReduce。

## [3.YARN-on-single-node.md](./03-YARN-on-single-node.md)
伪分布式下配置YARN运行MapReduce。

## [4.MapReduce.md](./MapReduce.md)
初次从编写MapReduce程序到变异运行的过程，最后理解程序。
