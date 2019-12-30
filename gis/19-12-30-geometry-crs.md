# Geometry

geometry的意思是几何，在地图里可以当作形状，常见的子类有Point、LineString、Polygon，当然，还有Ring和Curve等。

下面展示了其基本用法，创建一些对象：

```java
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import static java.lang.System.*;

public class GeometryDemo {

    /**
     * main
     */
    public static void main(String[] args) throws ParseException {
        // point 1
        final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

        final WKTReader reader = new WKTReader(geometryFactory);
        final Point point = (Point) reader.read("POINT (2 1)");
        out.println(point);

        // point 2
        final Coordinate coord = new Coordinate(2, 1);
        final Point point1 = geometryFactory.createPoint(coord);
        out.println(point1);

        // line 1
        final Geometry line1 = reader.read("LINESTRING(0 2,2 0,8 6)");
        out.println(line1);

        // line 2
        final Coordinate[] coordinates = {new Coordinate(0, 2), new Coordinate(2, 0), new Coordinate(8, 6)};
        LineString line2 = geometryFactory.createLineString(coordinates);
        out.println(line2);

        // polygon
        final Geometry polygon = reader.read("POLYGON((20 10,30 0,30 20,20 10))");
        out.println(polygon);
    }
}
```
结果：
```s
POINT (2 1)
POINT (2 1)
LINESTRING (0 2, 2 0, 8 6)
LINESTRING (0 2, 2 0, 8 6)
POLYGON ((20 10, 30 0, 30 20, 20 10))
```

# 为什么不使用java的形状

因为java的形状不能表示代理空间的关系（相交、相离、相邻等）等

```java
// Create Geometry using other Geometry
Geometry smoke = fire.buffer(10);
Geometry evacuate = cities.intersection(smoke);

// test important relationships
boolean onFire = me.intersects(fire);
boolean thatIntoYou = me.disjoint(you);
boolean run = you.isWithinDistance(fire, 2);

// relationships actually captured as a fancy
// String called an intersection matrix
//
IntersectionMatrix matrix = he.relate(you);
thatIntoYou = matrix.isDisjoint();

// it really is a fancy string; you can do
// pattern matching to sort out what the geometries
// being compared are up to
boolean disjoint = matrix.matches("FF*FF****");
```

# 地理空间坐标系（CRS）

Coordinate Reference System（CRS）。

脱离坐标系谈论坐标是没有意义的，下面是一些常见的坐标系：

## EPSG

European Petroleum Standards Group (EPSG), 他下面又有很多种


### EPSG:4326

WGS84

```java
CRS.decode("EPSG:4326");

DefaultGeographicCRS.WGS84;
```

### EPSG:3785

许多网络制图应用程序使用的“ Google地图”投影的官方代码。假装世界是一个球形很不错（它使您的数学速度非常快）。但是，如果您在奥斯陆画一个正方形，那看起来真的很奇怪。

### EPSG:3005

轴以米为单位进行测量，这对于计算距离或面积非常方便。

# 轴序（Axis Order）

> 这也是我需要公开道歉的地方。 作为计算机科学家，当我们在“他们做错了”的领域工作时，有时会感到厌烦。 地图制作就是一个例子。 当我们到达现场时，地图总是记录纬度，然后是经度。 即以南北轴为第一，东西轴为第二。 当您在屏幕上快速绘制该图形时，由于坐标在我的思维方式中是“ y / x”，因此世界似乎在横摆，并且需要在绘制之前交换它们。

为什么？

通过星际导航时，您可以通过与北极星的夹角来确定纬度-但是您需要根据旅行的天数来猜测经度。 因此，`y / x` 顺序。

## 交换顺序

交换为经度在前，纬度在后：
```java
        // way 1
        final CRSAuthorityFactory factory = CRS.getAuthorityFactory(true);
        final CoordinateReferenceSystem crs = factory.createCoordinateReferenceSystem("EPSG" +
                ":4326");
        out.println(crs);

        // way 2
        System.setProperty("org.geotools.referencing.forceXY", "true");
```

结果：
```java
GEOGCS["WGS 84", 
  DATUM["World Geodetic System 1984", 
    SPHEROID["WGS 84", 6378137.0, 298.257223563, AUTHORITY["EPSG","7030"]], 
    AUTHORITY["EPSG","6326"]], 
  PRIMEM["Greenwich", 0.0, AUTHORITY["EPSG","8901"]], 
  UNIT["degree", 0.017453292519943295], 
  AXIS["Geodetic longitude", EAST], 
  AXIS["Geodetic latitude", NORTH], 
  AUTHORITY["EPSG","4326"]]
```

