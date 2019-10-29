
今天遇到一个问题，关于mybatis的sqlsession和mysql的事务超时问题

http://objcoding.com/2019/03/20/mybatis-sqlsession/

mysql锁机制：https://juejin.im/post/5b82e0196fb9a019f47d1823#heading-16


```shell
show variables like 'innodb_lock_wait_timeout';
+--------------------------+---------+
| Variable_name            |   Value |
|--------------------------+---------|
| innodb_lock_wait_timeout |      50 |
+--------------------------+---------+
```


开启通用日志（注意，量很大）：
```shell
mysql> show variables like 'general%';
+------------------+------------------------------------+
| Variable_name    | Value                              |
+------------------+------------------------------------+
| general_log      | OFF                                |
| general_log_file | /var/lib/mysql/xxx.log |
+------------------+------------------------------------+
2 rows in set (0.00 sec)

mysql> set global general_log=1;
Query OK, 0 rows affected (0.00 sec)

mysql> show variables like 'general%';
+------------------+------------------------------------+
| Variable_name    | Value                              |
+------------------+------------------------------------+
| general_log      | ON                                 |
| general_log_file | /var/lib/mysql/xxx.log |
+------------------+------------------------------------+
```

查询进行中的事务：
```s
mysql> SELECT * FROM information_schema.INNODB_TRX;
```


