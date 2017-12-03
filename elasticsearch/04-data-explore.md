# Exploring Your Data
来自[https://www.elastic.co/guide/en/elasticsearch/reference/current/_exploring_your_data.html](https://www.elastic.co/guide/en/elasticsearch/reference/current/_exploring_your_data.html)

下载数据：
```shell
[jimo@jimo-pc my-data]$ wget https://raw.githubusercontent.com/elastic/elasticsearch/master/docs/src/test/resources/accounts.json
```
导入数据：
```shell
[jimo@jimo-pc my-data]$ curl -H "Content-Type: application/json" -XPOST 'localhost:9200/bank/account/_bulk?pretty&refresh' --data-binary "@accounts.json"
# 查看结果
[jimo@jimo-pc my-data]$ curl 'localhost:9200/_cat/indices?v'
health status index    uuid                   pri rep docs.count docs.deleted store.size pri.store.size
yellow open   customer 9nM78Xu0Rqe6YDDaQJOSAg   5   1          3            0     11.6kb         11.6kb
yellow open   bank     JOqLgpkAQ7yxooKSePCBVA   5   1       1000            0    487.9kb        487.9kb
```
## The Search API
```shell
[jimo@jimo-pc my-data]$ curl -XGET 'localhost:9200/bank/_search?q=*&sort=account_number:asc&pretty&pretty'
{
  "took" : 85,
  "timed_out" : false,
  "_shards" : {
    "total" : 5,
    "successful" : 5,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : 1000,
    "max_score" : null,
    "hits" : [
      {
        "_index" : "bank",
        "_type" : "account",
        "_id" : "0",
        "_score" : null,
        "_source" : {
          "account_number" : 0,
          "balance" : 16623,
          "firstname" : "Bradshaw",
          "lastname" : "Mckenzie",
          "age" : 29,
          "gender" : "F",
          "address" : "244 Columbus Place",
          "employer" : "Euron",
          "email" : "bradshawmckenzie@euron.com",
          "city" : "Hobucken",
          "state" : "CO"
        },
        "sort" : [
          0
        ]
      },
...
```
查询解析：localhost:9200/bank/_search?q=*&sort=account_number:asc&pretty&pretty
以&分开，q=*代表所有，asc升序。

等价与：
```shell
curl -XGET 'localhost:9200/bank/_search?pretty' -H 'Content-Type: application/json' -d' { "query": { "match_all": {} }, "sort": [ { "account_number": "asc" } ] } '
```
##  Introducing the Query Language

```shell
# 可以传入数量，只查一个，默认size=10
curl -XGET 'localhost:9200/bank/_search?pretty' -H 'Content-Type: application/json' -d' { "query": { "match_all": {} }, "size": 1 } '
# 指定开始
[jimo@jimo-pc my-data]$ curl -XGET 'localhost:9200/bank/_search?pretty' -H 'Content-Type: application/json' -d' { "query": { "match_all": {} }, "size": 2 ,"from":10} '
# 指定排序
[jimo@jimo-pc my-data]$ curl -XGET 'localhost:9200/bank/_search?pretty' -H 'Content-Type: application/json' -d' { "query": { "match_all": {} }, "sort": { "balance": { "order": "desc" } } } '
```
## Executing Searches
使用_source过滤字段：
```shell
[jimo@jimo-pc my-data]$ curl -XGET 'localhost:9200/bank/_search?pretty' -H 'Content-Type: application/json' -d' { "query": { "match_all": {} }, "_source": ["account_number", "balance"] } '
{
  "took" : 12,
  "timed_out" : false,
  "_shards" : {
    "total" : 5,
    "successful" : 5,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : 1000,
    "max_score" : 1.0,
    "hits" : [
      {
        "_index" : "bank",
        "_type" : "account",
        "_id" : "25",
        "_score" : 1.0,
        "_source" : {
          "account_number" : 25,
          "balance" : 40540
        }
      },
...
```
精确查找：
```shell
[jimo@jimo-pc my-data]$ curl -XGET 'localhost:9200/bank/_search?pretty' -H 'Content-Type: application/json' -d' { "query": { "match": { "account_number": 20 } } } '
{
  "took" : 20,
  "timed_out" : false,
  "_shards" : {
    "total" : 5,
    "successful" : 5,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : 1,
    "max_score" : 1.0,
    "hits" : [
      {
        "_index" : "bank",
        "_type" : "account",
        "_id" : "20",
        "_score" : 1.0,
        "_source" : {
          "account_number" : 20,
          "balance" : 16418,
          "firstname" : "Elinor",
          "lastname" : "Ratliff",
          "age" : 36,
          "gender" : "M",
          "address" : "282 Kings Place",
          "employer" : "Scentric",
          "email" : "elinorratliff@scentric.com",
          "city" : "Ribera",
          "state" : "WA"
        }
      }
    ]
  }
}
```
以空格分开代表OR，不区分大小写：
```json
{
  "query": { "match": { "address": "mill lane" } }
}
```
那要包含这两个则使用：match_phrase
```json
{
  "query": { "match_phrase": { "address": "mill lane" } }
}
```
**逻辑查询**
```json
AND
{
  "query": {
    "bool": {
      "must": [
        { "match": { "address": "mill" } },
        { "match": { "address": "lane" } }
      ]
    }
  }
}
OR
{
  "query": {
    "bool": {
      "should": [
        { "match": { "address": "mill" } },
        { "match": { "address": "lane" } }
      ]
    }
  }
}
AND NOT
{
  "query": {
    "bool": {
      "must_not": [
        { "match": { "address": "mill" } },
        { "match": { "address": "lane" } }
      ]
    }
  }
}
组合
{
  "query": {
    "bool": {
      "must": [
        { "match": { "age": "40" } }
      ],
      "must_not": [
        { "match": { "state": "ID" } }
      ]
    }
  }
}
```
## Executing Filters
有个_score评分排名项：
```json
"hits" : [
      {
        "_index" : "bank",
        "_type" : "account",
        "_id" : "253",
        "_score" : 1.0,
        "_source" : {
...
```
可以使用filter来过滤：
```json
{
  "query": {
    "bool": {
      "must": { "match_all": {} },
      "filter": {
        "range": {
          "balance": {
            "gte": 20000,
            "lte": 30000
          }
        }
      }
    }
  }
}
```
## Executing Aggregations
聚合类似于SQL的Group By
```shell
[jimo@jimo-pc my-data]$ curl -XGET 'localhost:9200/bank/_search?pretty' -H 'Content-Type: application/json' -d'
> {
>   "size": 0,
>   "aggs": {
>     "group_by_state": {
>       "terms": {
>         "field": "state.keyword"
>       }
>     }
>   }
> }
> '
{
  "took" : 38,
  "timed_out" : false,
  "_shards" : {
    "total" : 5,
    "successful" : 5,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : 1000,
    "max_score" : 0.0,
    "hits" : [ ]
  },
  "aggregations" : {
    "group_by_state" : {
      "doc_count_error_upper_bound" : 20,
      "sum_other_doc_count" : 770,
      "buckets" : [
        {
          "key" : "ID",
          "doc_count" : 27
        },
        {
          "key" : "TX",
          "doc_count" : 27
        },
        {
          "key" : "AL",
          "doc_count" : 25
        },
        {
          "key" : "MD",
          "doc_count" : 25
        },
```
等价于：
```sql
SELECT state, COUNT(*) FROM bank GROUP BY state ORDER BY COUNT(*) DESC
```
再进行聚合求他们balance的均值：
```shell
[jimo@jimo-pc my-data]$ curl -XGET 'localhost:9200/bank/_search?pretty' -H 'Content-Type: application/json' -d'
> {
>   "size": 0,
>   "aggs": {
>     "group_by_state": {
>       "terms": {
>         "field": "state.keyword"
>       },
>       "aggs": {
>         "average_balance": {
>           "avg": {
>             "field": "balance"
>           }
>         }
>       }
>     }
>   }
> }
> '
{
  "took" : 15,
  "timed_out" : false,
  "_shards" : {
    "total" : 5,
    "successful" : 5,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : 1000,
    "max_score" : 0.0,
    "hits" : [ ]
  },
  "aggregations" : {
    "group_by_state" : {
      "doc_count_error_upper_bound" : 20,
      "sum_other_doc_count" : 770,
      "buckets" : [
        {
          "key" : "ID",
          "doc_count" : 27,
          "average_balance" : {
            "value" : 24368.777777777777
          }
        },
        {
          "key" : "TX",
          "doc_count" : 27,
          "average_balance" : {
            "value" : 27462.925925925927
          }
        },
        {
          "key" : "AL",
          "doc_count" : 25,
          "average_balance" : {
            "value" : 25739.56
          }
        },
...
```
再按均值降序排列：
```json
{
  "size": 0,
  "aggs": {
    "group_by_state": {
      "terms": {
        "field": "state.keyword",
        "order": {
          "average_balance": "desc"
        }
      },
      "aggs": {
        "average_balance": {
          "avg": {
            "field": "balance"
          }
        }
      }
    }
  }
}
```
下面是先对不同年龄段的人聚类，再按性别，再求平均收支的结果：
```json
操作：
{
  "size": 0,
  "aggs": {
    "group_by_age": {
      "range": {
        "field": "age",
        "ranges": [
          {
            "from": 20,
            "to": 30
          },
          {
            "from": 30,
            "to": 40
          },
          {
            "from": 40,
            "to": 50
          }
        ]
      },
      "aggs": {
        "group_by_gender": {
          "terms": {
            "field": "gender.keyword"
          },
          "aggs": {
            "average_balance": {
              "avg": {
                "field": "balance"
              }
            }
          }
        }
      }
    }
  }
}

结果：
  "aggregations" : {
    "group_by_age" : {
      "buckets" : [
        {
          "key" : "20.0-30.0",
          "from" : 20.0,
          "to" : 30.0,
          "doc_count" : 451,
          "group_by_gender" : {
            "doc_count_error_upper_bound" : 0,
            "sum_other_doc_count" : 0,
            "buckets" : [
              {
                "key" : "M",
                "doc_count" : 232,
                "average_balance" : {
                  "value" : 27374.05172413793
                }
              },
              {
                "key" : "F",
                "doc_count" : 219,
                "average_balance" : {
                  "value" : 25341.260273972603
                }
              }
            ]
          }
        },
...
```
