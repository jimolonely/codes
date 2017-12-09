## nginx
nginx服务器配置

## 安装
参考官方文档:[https://www.nginx.com/resources/wiki/start/topics/tutorials/install/](https://www.nginx.com/resources/wiki/start/topics/tutorials/install/)

一般linux软件库都有.
## 基本使用
```shell
# 版本
[jimo@jimo-pc tmp]$ nginx -v
nginx version: nginx/1.12.2
# 检查配置
[jimo@jimo-pc tmp]$ sudo nginx -t
2017/12/09 17:28:01 [warn] 15233#15233: could not build optimal types_hash, you should increase either types_hash_max_size: 1024 or types_hash_bucket_size: 64; ignoring types_hash_bucket_size
nginx: the configuration file /etc/nginx/nginx.conf syntax is ok
nginx: configuration file /etc/nginx/nginx.conf test is successful
# 启动
[jimo@jimo-pc tmp]$ sudo nginx
```
现在在浏览器输入localhost可以看到welcome首页.
由于默认是80端口,可能会冲突,下面可以修改配置.

另外几个基本操作:
```shell
# 重载配置文件
$ nginx -s reload
# 重启
$ nginx -s reopen
# 关闭
$ nginx -s stop
```
## 配置
首先确定配置文件在哪,默认安装的在:/etc/nginx/nginx.conf

```sh

#user html;
worker_processes  4;# 和电脑cpu核心一样

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
error_log  /var/log/nginx/error.log  info; #设置日志位置和级别

#pid        logs/nginx.pid;

events {
    worker_connections  1024;
}

http {
    include       mime.types;
    default_type  application/octet-stream;

    # 日志格式,用默认的就行
    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    # 一些大小配置
    server_names_hash_bucket_size 128;
    client_header_buffer_size 32k;
    large_client_header_buffers 4 32k;
    client_max_body_size 8m;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;

    # 一个虚拟服务器相当于一个应用
    server {
        listen       8080; # 端口
        server_name  localhost;  #域名

        charset utf-8; # 编码

        #access_log  logs/host.access.log  main;

        location / {
            root   /usr/share/nginx/html; # 应用目录
            index  index.html index.htm; # 访问入口
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   /usr/share/nginx/html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

}
```