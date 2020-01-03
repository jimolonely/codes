我发现我并没有搞明白cookie这个东西，以及他和跨域的关系。

后端：SpringBoot

前端：Vue

# 后端

```java
        final Cookie cookie = new Cookie(TOKEN_KEY, "sdfdsf");
        // 不同环境不一样
        cookie.setDomain("domain");
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        // 24h
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
```

domain是个很关键的东西。

如果，前端在调用后台时的域名和这个domain不一样，那么前端浏览器是不会存储这个cookie的。

比如：

后台的地址为：192.10.1.11:8080, 而后台设置的domain为`jimo.com`

那么前端直接访问 `192.10.1.11:8080`,是得不到cookie的；当前端在 hosts里配置了 `192.10.1.11 xxx.jimo.com`, 再使用`xxx.jimo.com:8080`访问后台就可以拿到cookie。

# 前端 

但是，如果前端的域名不是 `xxxxxx.jimo.com`, 那么是看不到cookie的，但是能请求成功；而当改成一个`jimo.com`的子域名时，就可以看到这个cookie。

一个是写，一个是读。


# CORS

上面所说的就有问题了，因为如果后台完全开放了Origin的访问，那么任何网站都可以访问该网站了，这就可能导致CORS攻击，因此，后台需要限制Origin，只允许自己的前端访问：

```java
        final String origin = request.getHeader("Origin");
        if (originOk(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        } else {
            response.setHeader("Access-Control-Allow-Origin", "*.jimo.com:80*");
            return false;
        }
```

然后可以看到浏览器请求报错：
```
login:1 Access to XMLHttpRequest at 'http://localhost:8080/base/city' from origin 'http://localhost:8007' has been blocked by CORS policy: The 'Access-Control-Allow-Origin' header contains the invalid value '*.jimo.com:80*'.
```

# 爬虫

然后，这也防止不了爬虫，因为Origin可以随便设置，比如：

```python
        headers = {
            'Referer': 'http://xx.jimo.com',
            'Origin': 'http://kk.jimo.com',
            'Cookie': 'auth_token=XtrkBVPa6UWl7eZ4o3'
        }
        res = requests.get('http://localhost:8080/base/city', headers=headers)
        print(res.text)
```

爬虫防不胜防啊，不过随着法律的完善，很多公司进了监狱，这个烦恼会少一些。目前可以设置IP白名单高度解决。





