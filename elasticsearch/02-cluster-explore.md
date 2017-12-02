# 探索集群
elasticearch提供了标准的接口供访问。

安装很简单，先把集群跑起来：
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ ls
bin  config  lib  LICENSE.txt  modules  NOTICE.txt  plugins  README.textile
[jimo@jimo-pc elasticsearch-6.0.0]$ bin/elasticsearch
```
## Cluster Health
健康状态：
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl http://localhost:9200/_cat/health?v
epoch      timestamp cluster       status node.total node.data shards pri relo init unassign pending_tasks max_task_wait_time active_shards_percent
1512218799 20:46:39  elasticsearch green           1         1      0   0    0    0        0             0                  -                100.0%
```
注意的就是颜色：
* Green - everything is good (cluster is fully functional)
* Yellow - all data is available but some replicas are not yet allocated (cluster is fully functional)
* Red - some data is not available for whatever reason (cluster is partially functional)

获得节点列表：
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl http://localhost:9200/_cat/nodes?v
ip        heap.percent ram.percent cpu load_1m load_5m load_15m node.role master name
127.0.0.1           22          44   8    0.49    0.45     0.45 mdi       *      I9nSfCF
```
## List All Indices
列出所有索引：
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl http://localhost:9200/_cat/indices?v
health status index uuid pri rep docs.count docs.deleted store.size pri.store.size
```
看起来一个都没有。
## Create an Index
既然没有就创建一个：采用PUT方式
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XPUT 'localhost:9200/customer?pretty&pretty'
{
  "acknowledged" : true,
  "shards_acknowledged" : true,
  "index" : "customer"
}
# 结果
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XGET 'localhost:9200/_cat/indices?v&pretty'
health status index    uuid                   pri rep docs.count docs.deleted store.size pri.store.size
yellow open   customer AmBi42pqTlOf9UbIMgvWWw   5   1          0            0      1.1kb          1.1kb
```
可以看到上面pri（primary shard）和rep（replica）的默认数量。该Index还没有document关联。
## Index and Query a Document
```shell
# -d 是指定数据，下面创建了一篇ID为1的document
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XPUT 'localhost:9200/customer/doc/1?pretty&pretty' -H 'Content-Type: application/json' -d' { "name": "John Doe" } '
{
  "_index" : "customer",
  "_type" : "doc",
  "_id" : "1",
  "_version" : 1,
  "result" : "created",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 0,
  "_primary_term" : 1
}
```
查询：
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XGET 'localhost:9200/customer/doc/1?pretty&pretty'
{
  "_index" : "customer",
  "_type" : "doc",
  "_id" : "1",
  "_version" : 1,
  "found" : true,
  "_source" : {
    "name" : "John Doe"
  }
}
```
## Delete an Index
删除索引：
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XDELETE 'localhost:9200/customer?pretty&pretty'
{
  "acknowledged" : true
}
# 结果：
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XGET 'localhost:9200/_cat/indices?v&pretty'
health status index uuid pri rep docs.count docs.deleted store.size pri.store.size
```
## 小结
API命令的格式：
```shell
PUT /customer
PUT /customer/doc/1
{
  "name": "John Doe"
}
GET /customer/doc/1
DELETE /customer
```
模式：
```shell
<REST Verb> /<Index>/<Type>/<ID>
```
