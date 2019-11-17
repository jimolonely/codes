spark的driver理解，多种模式理解。

# 相关概念

```sql
          Application                      运行在集群上的用户程序，包含集群上的driver program 和多个executor线程组成；

          Driver program               application运行的main方法，并生成sparkcontext；

          Cluster manager             集群资源管理器 ；

          Deploy mode                   部署模式 用于区别driver program的运行方式:集群模式(cluter mode)，driver在集群内部启动；客户端模式（client mode），driver进程从集群外部启动；   

          Worker node                    工作节点，运行application的节点

          Executor                           work node的上进程，运行task并保持数据交互，每一个application有自己的executor

          Task                                   运行于Executor中的任务单元，Spark应用程序最终被划分为经过优化后的多个任务的集合

          Job                                     由多个转变构建的并行计算任务，具体为Spark中的action操作, 一个action就为一个job
```

# spark有哪些运行模式，区别是什么

[Spark常用三种运行模式](https://blog.csdn.net/Realoyou/article/details/80398424)

[Spark运行模式（Local、Standalone、Yarn）](https://www.jianshu.com/p/b4d3db386925):可以看下基本启动方式。


# 什么是driver，运行在哪里

在不同模式下的driver是什么

# 什么是Executor

executor做的事情有哪些

# 什么是spark context

SparkContext对象可以视为Spark应用程序的入口，主程序被称为driver program，SparkContext可以与不同种类的集群资源管理器(Cluster Manager），例如Hadoop Yarn、Mesos等 进行通信，从而分配到程序运行所需的资源，获取到集群运行所需的资源后，SparkContext将得到集群中其它工作节点（Worker Node） 上对应的Executors （不同的Spark应用程序有不同的Executor，它们之间也是独立的进程，Executor为应用程序提供分布式计算及数据存储功能），之后SparkContext将应用程序代码分发到各Executors，最后将任务（Task）分配给executors执行。

[https://www.cnblogs.com/xia520pi/p/8609602.html](https://www.cnblogs.com/xia520pi/p/8609602.html)

> 这里先摘抄SparkContext源码注释来简单介绍介绍SparkContext，注释的第一句话就是说SparkContext为Spark的主要入口点，简明扼要，如把Spark集群当作服务端那Spark Driver就是客户端，SparkContext则是客户端的核心；如注释所说 SparkContext用于连接Spark集群、创建RDD、累加器（accumlator）、广播变量（broadcast variables），所以说SparkContext为Spark程序的根本都不为过。



# 参考

[Spark的driver理解和executor理解](https://blog.csdn.net/qq_21383435/article/details/78653427)

[spark的组件构成](https://www.csdn.net/gather_26/MtTaYg4sNTA3OC1ibG9n.html)
