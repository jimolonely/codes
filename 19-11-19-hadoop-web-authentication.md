
hadoop web访问的访问控制

1. yarn web：host:8088
2. hadoop: host:50070

官方说明：[https://hadoop.apache.org/docs/r1.2.1/HttpAuthentication.html](https://hadoop.apache.org/docs/r1.2.1/HttpAuthentication.html)


# 修改配置
修改 `core-site.xml`

```xml
<property>
  <name>hadoop.http.filter.initializers</name>
  <value>org.apache.hadoop.security.AuthenticationFilterInitializer</value>
</property>
<property>
  <name>hadoop.http.authentication.type</name>
  <value>simple</value>
</property>
<property>
  <name>hadoop.http.authentication.signature.secret.file</name>
  <value>/hadoop-2.7.7/hadoop-http-auth-signature-secret</value>
</property>
<property>
  <name>hadoop.http.authentication.simple.anonymous.allowed</name>
  <value>false</value>
</property>
</configuration>
```

在`/hadoop-2.7.7/hadoop-http-auth-signature-secret`文件里存储一个复杂的密钥，比如：`dhjfkdhsjkfs`。

# web访问

在url后跟上参数：`host:8088?user.name=dhjfkdhsjkfs`


# 特别说明

这并不是一种用户名密码的验证方式，在`user.name=xxx`后随便加一个名字也能访问，所以只是一个规则，不能广泛使用。



