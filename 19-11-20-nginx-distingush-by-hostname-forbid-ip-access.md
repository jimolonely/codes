
从某种程度上说，有一个集群环境，里面有一些端口不能被外部访问，但是80端口可以，作为开发者，希望能代理一下，但是因为那些端口都有界面，有静态资源。

因此，在一个nginx里要区分他们，有几个方案。

1. 如果那几个web环境有特殊标志，比如header不一样，可以用nginx的参数区分再代理；
2. 如果他们都有一个/static/xx的uri，那就可以通过hostname的不同

作为开发者，对同一个IP设多个hostname没啥。

# 用hostname区分

```s
server{

    listen 80;
    server_name app1;

    location / {
	    proxy_pass http://192.10.1.11:8009;
    }
}

server{

    listen 80;
    server_name app2;

    location / {
	    proxy_pass http://192.10.1.11:8008;
    }
}
```

再我们的机器上配一下：

hosts
```s
111.12.23.45 app1
111.12.23.45 app2
```
这样，通过 http://app1, http://app2 就可以了。

# 禁用IP访问

但是这样对于知道IP的人依然可以访问，如果不想这样（比如，写一个复杂的域名当作密钥用），那么就禁掉

```s
server {
listen 80 default;
server_name _;
return 403;
}
```






