
# hbase合并region工具

使用方式：

1. 打包： `mvn package`
2. 运行： `java -jar hbase-merge-tool.jar zk.quorum tablePattern [isRegex=false]`

可以合并某张表，也可以使用正则匹配多张表。


