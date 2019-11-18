apache livy如何使用提交代码片的方式执行自己的jar包？

	1. livy可通过jar包方式提交，对jar包代码完全没有侵入性，但是每次运行都是新的sparkContext，启动耗时700-800ms
	2. livy也可以创建交互式shell，但是只能提交代码片
	3. 我们希望可以重复使用spark context，因此只能使用交互式，于是我尝试在代码片里导包，因为在创建session时可以指定一些jar添加到spark环境，但是我自己的jar包无论如何都导不进去，
		但是我发现spark-examples.jar里的类可以导入，最后我编写了一个org.apache.spark.examples包下的自定义类，并加到spark-examples.jar里，居然成功了，也可以复用spark session。
		很神奇。
 
 
 # 创建session时指定jar包
 
 [https://livy.apache.org/docs/latest/rest-api.html](https://livy.apache.org/docs/latest/rest-api.html)
 
 ```json
 {
    "name": "s4",
    "kind": "spark",
    "jars": [
        "/home/jimo/livy/jars/livy-demo.jar",
        "/home/jimo/livy/jars/spark-examples_2.11-2.3.3.jar"
    ]
}
 ```
 启动日志里会有：
 ```java
2019-11-18 20:59:42 INFO  SparkContext:54 - Added JAR file:///home/jimo/livy/jars/livy-demo.jar at spark://host:41550/jars/livy-demo.jar with timestamp 1574081982646
2019-11-18 20:59:42 INFO  SparkContext:54 - Added JAR file:///home/jimo/livy/jars/spark-examples_2.11-2.3.3.jar at spark://host:41550/jars/spark-examples_2.11-2.3.3.jar with timestamp 1574081982646
 ```

# 执行代码片时导包

```json
{
    "code": "import org.apache.spark.examples.InShellJob; val j = new InShellJob(); j.execute(sc);"
}
```

这个InShellJob就是我写的一个位于`org.apache.spark.examples`包下的测试类：

```java
package org.apache.spark.examples;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

public class InShellJob implements Function<Integer, Integer>,
        Function2<Integer, Integer, Integer> {

    /**
     * test
     */
    public double execute(JavaSparkContext sc) {
        System.out.println("test");

        List<Integer> sampleList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            sampleList.add(i + 1);
        }
        final SparkSession spark = SparkSession.builder().config(sc.getConf()).enableHiveSupport().getOrCreate();

        System.out.println("---------------测试hive-------------");

        final Dataset<Row> data = spark.read().json("/jimo.json");
        data.show();
        data.printSchema();

        return 4.0d * sc.parallelize(sampleList).map(this).reduce(this) / 100;
    }

    @Override
    public Integer call(Integer v1) {
        double x = Math.random();
        double y = Math.random();
        return (x * x + y * y < 1) ? 1 : 0;
    }

    @Override
    public Integer call(Integer v1, Integer v2) {
        return v1 + v2;
    }
}
```

# 总结

关于为什么只能在`org.apache.spark.examples`包下才行，还得研究研究，毕竟处于孵化阶段，参考的东西太少。




