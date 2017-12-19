## MapReduce编程
这是必经之路，按照官方文档：[http://hadoop.apache.org/docs/current/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html](http://hadoop.apache.org/docs/current/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html)进行实施。

## 运行WordCount v1
这一节是从我们自己编写MapReduce程序到打包运行的过程。在02的配置基础上运行的。
### 编写源程序
后面再解释程序细节，先跟着走：
```java
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {
    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                word.set(itr.nextToken());
                context.write(word, one);
            }
        }
    }

    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCount.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
```
为了方便，将此文件放在hadoop目录下。

如何编写这部分代码，可以在IDE里进行，使用maven构建，需要的jar包为，这里只是检查语法错误，并不是用这里的jar包编译：
```maven
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-common -->
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-common</artifactId>
            <version>2.9.0</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-mapreduce-client-core -->
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-mapreduce-client-core</artifactId>
            <version>2.9.0</version>
        </dependency>
```
### 编译源文件
先配置环境变量：
位于～/.bashrc
```shell
export JAVA_HOME=/usr/java/default
export PATH=${JAVA_HOME}/bin:${PATH}
export HADOOP_CLASSPATH=${JAVA_HOME}/lib/tools.jar
```
```shell
jimo@jimo-slave:~$ echo $HADOOP_CLASSPATH
/usr/lib/jvm/java-8-openjdk-amd64/lib/tools.jar
```

需要javac，而javac是jdk提供的，上次只装了jre，所以需要补上：
```shell
$ sudo apt install default-jdk
```
根据官方文档，需要javac的全路径：
```shell
jimo@jimo-slave:~/hadoop/hadoop-2.9.0$ bin/hadoop javac WordCount.java
错误: 找不到或无法加载主类 javac

jimo@jimo-slave:~/hadoop/hadoop-2.9.0$ bin/hadoop com.sun.tools.javac.Main WordCount.java

jimo@jimo-slave:~/hadoop/hadoop-2.9.0$ ls
bin      lib          NOTICE.txt  WordCount.class
etc      libexec      README.txt  WordCount$IntSumReducer.class
include  LICENSE.txt  sbin        WordCount.java
input    logs         share       WordCount$TokenizerMapper.class

# 打包
jimo@jimo-slave:~/hadoop/hadoop-2.9.0$ jar cf wc.jar WordCount*.class
jimo@jimo-slave:~/hadoop/hadoop-2.9.0$ ls
bin      libexec      sbin                           WordCount.java
etc      LICENSE.txt  share                          WordCount$TokenizerMapper.class
include  logs         wc.jar
input    NOTICE.txt   WordCount.class
lib      README.txt   WordCount$IntSumReducer.class
```
对语句：bin/hadoop com.sun.tools.javac.Main WordCount.java的理解
```
WordCount里所引用的类的jar包全在hadoop目录【hadoop-2.9.0/share/hadoop/】下
```
### 准备输入文件
先建立用户空间：
```shell
jimo@jimo-slave:~/hadoop/hadoop-2.9.0$ bin/hdfs dfs -mkdir /user
jimo@jimo-slave:~/hadoop/hadoop-2.9.0$ bin/hdfs dfs -mkdir /user/jimo
```
把当前的input目录传入里面：
```shell
jimo@jimo-slave:~/hadoop/hadoop-2.9.0$ bin/hadoop fs -put input /user/jimo

jimo@jimo-slave:~/hadoop/hadoop-2.9.0$ bin/hadoop fs -ls -R /user/jimo
drwxr-xr-x   - jimo supergroup          0 2017-11-30 16:14 /user/jimo/input
-rw-r--r--   1 jimo supergroup         21 2017-11-30 16:14 /user/jimo/input/file1
-rw-r--r--   1 jimo supergroup         15 2017-11-30 16:14 /user/jimo/input/file2
```
### 运行程序
注意需要指定启动类：wc.jar WordCount
```shell
jimo@jimo-slave:~/hadoop/hadoop-2.9.0$ bin/hadoop jar wc.jar WordCount /user/jimo/input /user/jimo/output
```
结果：
```shell
jimo@jimo-slave:~/hadoop/hadoop-2.9.0$ bin/hadoop fs -cat /user/jimo/output/*
Bye 1
Goodbye 1
Hadoop 2
Hello 2
World 2
```
## 对程序的理解
总的来说我们重写了2个方法，map和reduce：可以认为map是拆分成一个个的单词，reduce将他们聚合成结果。
```java
class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable>
class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable>
```
**Map操作**

Mapper的定义：2个输入，2个输出，分别是key和value：
```java
class Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
```
Mapper的原map方法只是简单的把输入输出。我们重写如下：
```java
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                word.set(itr.nextToken());
                context.write(word, one);
            }
        }
```
StringTokenizer把所有单词按空格分开，然后加入一个map中，最后得到的结果：
file1：
```
< Hello, 1>
< World, 1>
< Bye, 1>
< World, 1>
```
file2：
```
< Hello, 1>
< Hadoop, 1>
< Goodbye, 1>
< Hadoop, 1>
```
两个文件，使用了2个map，对于map的细粒度，后面再讨论。

**Reduce操作**

reduce的原方法只是原样输出，我们需要计数：
```java
        public void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
```
输入了2个map，返回也是2个：注意是按key排序的
map1：
```
< Bye, 1>
< Hello, 1>
< World, 2>
```
map2：
```
< Goodbye, 1>
< Hadoop, 2>
< Hello, 1>
```
他们怎么聚合在一起呢？我查看源码发现还有一个run方法，返回调用reduce，应该是这个，让这些结果反复聚合：
```java
    public void run(Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>.Context context) throws IOException, InterruptedException {
        this.setup(context);

        try {
            while(context.nextKey()) {
                this.reduce(context.getCurrentKey(), context.getValues(), context);
                Iterator<VALUEIN> iter = context.getValues().iterator();
                if(iter instanceof ValueIterator) {
                    ((ValueIterator)iter).resetBackupStore();
                }
            }
        } finally {
            this.cleanup(context);
        }

    }
```
**main方法**

这个很简单，设置类和输入输出路径参数：
```java
FileInputFormat.addInputPath(job, new Path(args[0]));
FileOutputFormat.setOutputPath(job, new Path(args[1]));
```

## 小结
1. 这是我根据官方文档的理解，可能有错误，会在不断熟悉后发现；
2. 总的来说还是很简单，底层怎么实现是关键。