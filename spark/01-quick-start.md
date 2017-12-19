# start
来自[https://spark.apache.org/docs/latest/quick-start.html](https://spark.apache.org/docs/latest/quick-start.html)

注意版本就行了。

## 命令行操作
```shell
[jimo@jimo-pc spark-2.2.0-bin-hadoop2.7]$ bin/spark-shell 
Using Spark's default log4j profile: org/apache/spark/log4j-defaults.properties
...
Spark context Web UI available at http://192.168.1.109:4040
Spark context available as 'sc' (master = local[*], app id = local-1512393157767).
Spark session available as 'spark'.
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 2.2.0
      /_/
         
Using Scala version 2.11.8 (OpenJDK 64-Bit Server VM, Java 1.8.0_144)
Type in expressions to have them evaluated.
Type :help for more information.
```
## 自定义程序
整个目录结构：
```shell
[jimo@jimo-pc SimpleApp]$ find .
.
./build.sbt
./src
./src/main
./src/main/scala
./src/main/scala/SimpleApp.scala
```
SimpleApp.scala
```scala
import org.apache.spark.sql.SparkSession

object SimpleApp {
  def main(args: Array[String]) {
    val logFile = "YOUR_SPARK_HOME/README.md" // 随便一个文件
    val spark = SparkSession.builder.appName("Simple Application").getOrCreate()
    val logData = spark.read.textFile(logFile).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")
    spark.stop()
  }
}
```
build.sbt,版本很重要
```sbt
name := "Simple Project"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.2.0"
```
打包：可以由于国内网速比较慢，可以配下sbt的镜像
```shell
$ sbt package
...
[info] Packaging {..}/{..}/target/scala-2.11/simple-project_2.11-1.0.jar
```
运行spark,上面程序的作用就是统计含有a和b字母的行数
```shell
[jimo@jimo-pc spark-2.2.0-bin-hadoop2.7]$ bin/spark-submit --class "SimpleApp" --master local[4] /home/jimo/workspace/SimpleApp/target/scala-2.11/simple-project_2.11-1.0.jar 

Using Spark's default log4j profile: org/apache/spark/log4j-defaults.properties
...
Lines with a: 61, Lines with b: 30
...
```


