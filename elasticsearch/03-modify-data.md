# Modifying Your Data
从[modifying_your_data.html](https://www.elastic.co/guide/en/elasticsearch/reference/current/_modifying_your_data.html)开始

再把数据建回来：
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XPUT 'localhost:9200/customer/doc/1?pretty&pretty' -H 'Content-Type: application/json' -d'
> {
>   "name": "John Doe"
> }
> '
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
当我们再次执行：
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XPUT 'localhost:9200/customer/doc/1?pretty&pretty' -H 'Content-Type: application/json' -d' { "name": "John Doe" } '
{
  "_index" : "customer",
  "_type" : "doc",
  "_id" : "1",
  "_version" : 2,
  "result" : "updated",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 1,
  "_primary_term" : 1
}
```
会发现版本、序列号变了，结果变为updated。

我们再放一个ID=2的：
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XPUT 'localhost:9200/customer/doc/2?pretty&pretty' -H 'Content-Type: application/json' -d' { "name": "Jane Doe" } '
{
  "_index" : "customer",
  "_type" : "doc",
  "_id" : "2",
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
当我们不指定ID时，以POST方式提交，会有个随机ID值：
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XPOST 'localhost:9200/customer/doc?pretty&pretty' -H 'Content-Type: application/json' -d' { "name": "Jane Doe" } '
{
  "_index" : "customer",
  "_type" : "doc",
  "_id" : "KcZ4F2ABxI3idTrh9-Z0",
  "_version" : 1,
  "result" : "created",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 1,
  "_primary_term" : 1
}
```
## Updating Documents
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XPOST 'localhost:9200/customer/doc/1/_update?pretty&pretty' -H 'Content-Type: application/json' -d' { "doc": { "name": "Jane Doe" } } '
{
  "_index" : "customer",
  "_type" : "doc",
  "_id" : "1",
  "_version" : 3,
  "result" : "updated",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 2,
  "_primary_term" : 1
}
# 增加字段
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XPOST 'localhost:9200/customer/doc/1/_update?pretty&pretty' -H 'Content-Type: application/json' -d' { "doc": { "name": "Jane Doe", "age": 20 } } '
# 做运算
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XPOST 'localhost:9200/customer/doc/1/_update?pretty&pretty' -H 'Content-Type: application/json' -d' { "script" : "ctx._source.age += 5" } '  
# 现在的结果
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XGET 'localhost:9200/customer/doc/1?pretty&pretty'
{
  "_index" : "customer",
  "_type" : "doc",
  "_id" : "1",
  "_version" : 5,
  "found" : true,
  "_source" : {
    "name" : "Jane Doe",
    "age" : 25
  }
}
```
## Deleting Documents
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XDELETE 'localhost:9200/customer/doc/2?pretty&pretty'
{
  "_index" : "customer",
  "_type" : "doc",
  "_id" : "2",
  "_version" : 2,
  "result" : "deleted",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 2,
  "_primary_term" : 1
}
```
## Batch Processing
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XPOST 'localhost:9200/customer/doc/_bulk?pretty&pretty' -H 'Content-Type: application/json' -d'
> {"index":{"_id":"1"}}
> {"name": "John Doe" }
> {"index":{"_id":"2"}}
> {"name": "Jane Doe" }
> '
{
  "took" : 38,
  "errors" : false,
  "items" : [
    {
      "index" : {
        "_index" : "customer",
        "_type" : "doc",
        "_id" : "1",
        "_version" : 6,
        "result" : "updated",
        "_shards" : {
          "total" : 2,
          "successful" : 1,
          "failed" : 0
        },
        "_seq_no" : 5,
        "_primary_term" : 1,
        "status" : 200
      }
    },
    {
      "index" : {
        "_index" : "customer",
        "_type" : "doc",
        "_id" : "2",
        "_version" : 1,
        "result" : "created",
        "_shards" : {
          "total" : 2,
          "successful" : 1,
          "failed" : 0
        },
        "_seq_no" : 3,
        "_primary_term" : 1,
        "status" : 201
      }
    }
  ]
}
```
下面再更新和删除：
```shell
[jimo@jimo-pc elasticsearch-6.0.0]$ curl -XPOST 'localhost:9200/customer/doc/_bulk?pretty&pretty' -H 'Content-Type: application/json' -d'
> {"update":{"_id":"1"}}
> {"doc": { "name": "John Doe becomes Jane Doe" } }
> {"delete":{"_id":"2"}}
> '
{
  "took" : 55,
  "errors" : false,
  "items" : [
    {
      "update" : {
        "_index" : "customer",
        "_type" : "doc",
        "_id" : "1",
        "_version" : 7,
        "result" : "updated",
        "_shards" : {
          "total" : 2,
          "successful" : 1,
          "failed" : 0
        },
        "_seq_no" : 6,
        "_primary_term" : 1,
        "status" : 200
      }
    },
    {
      "delete" : {
        "_index" : "customer",
        "_type" : "doc",
        "_id" : "2",
        "_version" : 2,
        "result" : "deleted",
        "_shards" : {
          "total" : 2,
          "successful" : 1,
          "failed" : 0
        },
        "_seq_no" : 4,
        "_primary_term" : 1,
        "status" : 200
      }
    }
  ]
}
```



