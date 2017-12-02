# 基础概念
来自：[http://www.elastic.co/guide/en/elasticsearch/reference/current/_basic_concepts.html](http://www.elastic.co/guide/en/elasticsearch/reference/current/_basic_concepts.html)

## Near Realtime (NRT)
有大约1秒的延时
## Cluster
node的集合
## node
单一的数据存储和搜索的服务器
## Index
类似于数据库的索引
## Type
索引的类别，逐渐被淘汰
## Document
能够被索引的最小单元，以json格式描述和表示。
## Shards & Replica
Shard：碎片，构成索引的单元，让大的索引可分割，以实现横向拓展；
Replica：Shard的备份，为了应对Shard节点的掉线，同时可并行化。

默认：每个Index有5个shard和1个replica，如果将这个Index部署在2个节点上，则共有10个shard。