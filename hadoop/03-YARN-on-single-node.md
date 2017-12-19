# 03-YARN-on-single-node.md
将MapReduce以伪分布模式运行在YARN上。

[http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/SingleCluster.html#YARN_on_Single_Node](http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/SingleCluster.html#YARN_on_Single_Node)

## 配置
在上一节配置文件的基础上做。

etc/hadoop/mapred-site.xml，默认只有etc/hadoop/mapred-site.xml.template,复制一份改个名：
```shell
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
</configuration>
```
etc/hadoop/yarn-site.xml:
```shell
<configuration>
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
</configuration>
```

## 启动yarn
在此之前需要确保hdfs在运行，如果没有运行，肯定不能执行任务的。
否则运行mapreduce会出现：
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.9.0.jar wordcount /user/first/input count
17/11/28 16:23:12 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032
java.net.ConnectException: Call From jimo-ubuntu/10.0.2.15 to localhost:9000 failed on connection exception: java.net.ConnectException: 拒绝连接; For more details see:  http://wiki.apache.org/hadoop/ConnectionRefused
```
然后再启动yarn：
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ sbin/start-yarn.sh
starting yarn daemons
starting resourcemanager, logging to /home/jimo/hadoop/hadoop-2.9.0/logs/yarn-jimo-resourcemanager-jimo-ubuntu.out
localhost: starting nodemanager, logging to /home/jimo/hadoop/hadoop-2.9.0/logs/yarn-jimo-nodemanager-jimo-ubuntu.out
```
可以在http://localhost:8088 看到
## 运行MapReduce任务
依然是那个例子，换个输出文件名：
```shell
jimo@jimo-ubuntu:~/hadoop/hadoop-2.9.0$ bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.9.0.jar wordcount /user/first/input count
```
运行失败了：
```shell
17/11/28 16:30:09 INFO mapreduce.Job: Job job_1511857691821_0001 failed with state FAILED due to: Task failed task_1511857691821_0001_m_000001
Job failed as tasks failed. failedMaps:1 failedReduces:0
```
看日志是内存不够用了：
```shell
Container [pid=17063,containerID=container_1511857691821_0002_01_000026] is running beyond virtual memory limits. Current usage: 90.9 MB of 1 GB physical memory used; 2.1 GB of 2.1 GB virtual memory used. Killing container
```
将虚拟机内存开大一些就没问题了。

