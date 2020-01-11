

# 关于DataStore的使用注意事项

[http://docs.geotools.org/stable/userguide/library/data/datastore.html](http://docs.geotools.org/stable/userguide/library/data/datastore.html)

阅读本文，明白DataStore的创建与注意事项。

* DataStore是个很重的对象，东西很多，所以不要频繁创建，或者复制，而应该维护一个单例模式
* 最好使用工厂方法创建DataStore, 因为其实现会经常变，不利于代码维护

# 关于DataStore的类结构

需要看这篇：[http://docs.geotools.org/stable/userguide/library/main/datastore.html](http://docs.geotools.org/stable/userguide/library/main/datastore.html)

读完这篇文档需要明白：

* DataAccess和DataStore的类结构关系，以及他们都有哪些方法
* FeatureSource和它的实现类结构，以及为什么用`instanceOf`来控制其访问级别（只读，可写，线程隔离）
* SimpleFeatureSource和DataStore、FeatureSource的整体关系
* Query和CQL，filter组合的一些查询写法
* SimpleFeatureStore是啥，理解其事务写法

DataAccessFactorySpi：

```java
import org.geotools.data.DataStoreFactorySpi;
import org.geotools.data.DataStoreFinder;

import java.util.Iterator;

import static java.lang.System.out;

public static void main(String[] args) {
	final Iterator<DataStoreFactorySpi> i = DataStoreFinder.getAvailableDataStores();
	i.forEachRemaining(x -> out.println(x + " " + x.getDisplayName()));
}
```
结果：
```s
org.geotools.data.postgis.PostgisNGDataStoreFactory@2b6856dd PostGIS
org.geotools.data.postgis.PostgisNGJNDIDataStoreFactory@327b636c PostGIS (JNDI)
org.geotools.data.shapefile.ShapefileDirectoryFactory@50a7bc6e Directory of spatial files (shapefiles)
org.geotools.data.shapefile.ShapefileDataStoreFactory@5762806e Shapefile
```


