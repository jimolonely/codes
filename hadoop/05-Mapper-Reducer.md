# 理解MapRecude
下面是我用自己的话理解的[原文文档的MapReduce - User Interfaces一节](http://hadoop.apache.org/docs/current/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html)

1. 先是Mapper和Reducer接口，应用程序通常实现它们以提供映射和减少方法。
2. 其他核心接口，包括Job，Partitioner，InputFormat，OutputFormat等。
3. 最后，讨论框架的一些有用的功能，如DistributedCache，IsolationRunner等。

## Mapper

1. 自定义Mapper
    * 重写map方法
    * context.write(WritableComparable, Writable)用于收集输出对
    * 重写cleanup方法
2. Job.setMapperClass
3. Partitioner分区方式
4. Job.setGroupingComparatorClass（Class）指定分组方式
5. Job.setCombinerClass(Class)中间输出存储
6. 通过配置指定是否压缩中间结果：CompressionCodec
7. mapper结果给reducer

**Map的数量**
```java
//10TB ， blocksize 128MB, 82,000 maps
Configuration.set(MRJobConfig.NUM_MAPS, int) //更高
```
## Reducer

1. reducer数量设置： Job.setNumReduceTasks(int)
2. 自定义reducer
    * reduce(WritableComparable, Iterable<Writable>, Context) 
    * cleanup(Context)
3.  Job.setReducerClass(Class) 

下面是reduce详细过程：

1. Shuffle: mapper的分区后的输出，reducer的输入
2. Sort：与shuffle同步，根据key排序
3. Secondary Sort：key相同时的备用排序，可根据Job.setSortComparatorClass(Class)指定
4. Reduce：
    * 对分组的输入执行reduce(WritableComparable, Iterable<Writable>, Context)
    * Context.write(WritableComparable, Writable)将reduce task写入文件系统
    * 输出并不存储
5. 设置多少Reduce：0.95或1.75乘以（<节点数> * <每个节点最大容器数量>）
6. Reducer NONE：可以为0

## Partitioner
对mapper的结果分组，分组后的数量就是reducer的数量，默认分组器：HashPartitioner 
## Counter
实现它可以得到数据统计报告


