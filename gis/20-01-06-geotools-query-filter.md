
本篇来自于：[http://docs.geotools.org/latest/userguide/tutorial/filter/query.html](http://docs.geotools.org/latest/userguide/tutorial/filter/query.html)

# Filter

从 FeatureSource中过滤数据：
```java
Filter filter = CQL.toFilter("POPULATION > 30000");

Filter pointInPolygon = CQL.toFilter("CONTAINS(THE_GEOM, POINT(1 2))");
Filter clickedOn = CQL.toFilter("BBOX(ATTR1, 151.12, 151.14, -33.5, -33.51)";
```
也可以直接通过工厂方法创建：

```java
FilterFactory ff = CommonFactoryFinder.getFilterFactory(null);

Filter filter = ff.propertyGreaterThan(ff.property("POPULATION"), ff.literal(12));

if(filter.evaluate(feature)){
    System.out.println("Selected "+ feature.getId();
}
```

# Expression

表达式就是语句：

```java
ff.property("POPULATION"); // expression used to access the attribute POPULATION from a feature
ff.literal(12);            // the number 12
```
也是一个对象：
```java
CQL.toExpression("buffer(THE_GEOM)");
CQL.toExpression("strConcat(CITY_NAME, POPULATION)");
CQL.toExpression("distance(THE_GEOM, POINT(151.14,-33.51))");
```

# Query

构造一个查询，借助于filter：
```java
Query query = new Query("cities", filter, new String[]{ "THE_GEOM", "POPULATION" });
```
就相当于：从cities表里查询在满足filter情况下的THE_GEOM和POPULATION字段.

# FeatureCollection

一个相似的对比：

```s
GeoTools	JDBC
FeatureSource	View
FeatureStore	Table
FeatureCollection	PreparedStatement
FeatureIterator	ResultSet
```

需要记住以下几点：

1. FeatureCollection是懒加载的，只有在 FeatureIterator迭代时才加载东西
2. FeatureIterator一定要关闭，因为其实际上是从硬盘读取数据，不关会造成资源泄露
3. FeatureCollection是存在内存的，不建议一次性加载

