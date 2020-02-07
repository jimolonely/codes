
指定外部配置目录：
```java
java -jar web.jar --Dspring.config.location=/mnt/config目录
```
或者，也可以放在jar包同一个目录新建一个config文件夹，启动时不指定：
```java
ls
. .. config web.jar

java -jar web.jar
```
