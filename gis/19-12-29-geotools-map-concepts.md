# 在地图中，什么是Feature、FeatureType？

参考geotools的解释：[http://docs.geotools.org/latest/userguide/tutorial/feature/csv2shp.html](http://docs.geotools.org/latest/userguide/tutorial/feature/csv2shp.html)

到最后feature那一节：


> A feature is something that can be drawn on a map. The strict definition is that a feature is something in the real world – a feature of the landscape - Mt Everest, the Eiffel Tower, or even something that moves around like your great aunt Alice.

> Explaining the concept to Java developers is easy - a feature is an Object.

> Like a java object features can contain some information about the real world thing that they represent. This information is organized into attributes just as in Java information is slotted into fields.

> Occasionally you have two features that have a lot in common. You may have the LAX airport in Los Angeles and the SYD airport in Sydney. Because these two features have a couple of things in common it is nice to group them together - in Java we would create a Class called Airport. On a map we will create a FeatureType called Airport.


并且提供了一个地理空间术语和java术语的类比：

```java
Java	Geospatial
Object	Feature
Class	FeatureType
Field	Attribute
Method	Operation
```

但是，地图比java代码更疯狂，因为它把Attribute和Operation也当作Feature。



# JTS是什么

其采用了递归缩写：` JTS Topology Suite`.

是一个java库，代表了Geometry的一系列疑难杂症封装。

比如：从WKT创建点：
```java
GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory( null );

WKTReader reader = new WKTReader( geometryFactory );
Point point = (Point) reader.read("POINT (1 1)");
```

# 弄清楚DataStore,FeatureSource,FeatureStore,Feature,FeatureType之间的含义和关系

* Feature：所有可以在地图上绘制的东西，你的房子，我的车子，马路，公路，桥，甚至是移动的人，在java代码里就是一个对象
* FeatureType：就是具有相同属性的Feature的分组，比如各种类型的车，铁路，公路都可以分类，同一个类就是一个FeatureType
* DataStore: 任何存有时空数据的文件、数据库、服务
* FeatureSource: 读feature
* FeatureStore：检查是否有读写Feature的权限，通过`instanceof`的方式。

```java 
String typeNames = dataStore.getTypeNames()[0];
SimpleFeatureSource source = store.getfeatureSource( typeName );
if( source instanceof SimpleFeatureStore){
   SimpleFeatureStore store = (SimpleFeatureStore) source; // write access!
   store.addFeatures( featureCollection );
   store.removeFeatures( filter ); // filter is like SQL WHERE
   store.modifyFeature( attribute, value, filter );
}
```

