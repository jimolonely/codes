# Ajax
今天将学会什么： 
1. 如何使用原生js代码写Ajax
2. 为什么我们前端会发2次请求，附带的OPTIONS ？
3. 学会前后端参数传递，这不是一个人的事

<details>
<summary>1. 什么是Ajax？ 和jquery的ajax()方法有什么关系？（1min）</summary>

Asynchronous JavaScript + XML 的缩写， 关键词异步，XML。
jquery的ajax()方法是对Ajax技术（XHR）的封装，更易用。(观摩源码)
</details>

<details>
<summary>2. 什么时候出现？谁让它出现的？为什么会出现Ajax？（1min）</summary>

1. 2005年，[Jesse James Garrett发表的文章](https://adaptivepath.org/ideas/ajax-new-approach-web-applications/)
2. 因为同步等待的时光一去不返，值得留恋
</details>

<details>
<summary>3. Ajax的核心是哪个对象？ 如何创建？（1min）</summary>

XmlHttpRequest对象， 后面简称XHR。

创建方式：
```js
// IE7 以前
xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");

// 现在
xmlhttp=new XMLHttpRequest();
```
</details>

<details>
<summary>4. XHR是标准吗？（1min）</summary>

[参见](http://www.w3school.com.cn/xml/xml_http.asp)
```
XMLHttpRequest 对象是 W3C 的标准吗？
任何 W3C 推荐标准均未规定 XMLHttpRequest 对象。

不过，W3C DOM Level 3 的 "Load and Save" 规范包含了一些相似的功能性，但是还没有任何浏览器实现它们。
```
</details>

<details>
<summary>5. 如何使用XHR发送Get，Post等请求？（5min）</summary>

Get:
```js
    function sendGet(url, callback) {
        let xhr = createXHR();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    console.log(xhr);
                    callback(xhr.responseText);
                } else {
                    console.error("Failed Get: ", xhr.status);
                }
            }
        };
        xhr.open("get", url, true);
        xhr.send(null);
    }
```
Post:
```js
    function sendPost(url, contentType, body, callback) {
        let xhr = createXHR();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    console.log(xhr);
                    callback(xhr.responseText);
                } else {
                    console.error("Failed Get: ", xhr.status);
                }
            }
        };

        xhr.open("post", url, true);
        if (contentType) {
            xhr.setRequestHeader("Content-Type", contentType);
        } else {
            // default: text/plain
        }
        xhr.send(body);
    }
```
注意点： onreadystatechange会反复触发。

注意： 常见的3种前后端传参类型。
</details>

<details>
<summary>6. Http请求头简介（3min）</summary>

访问一个网站。注意Referer这个字段，Http标准拼错了，只能说写标准的人语文跟我一样差。
</details>

<details>
<summary>7. XHR2级：FormData，超时，MIME类型重写（5min）</summary>

示例
</details>

<details>
<summary>8.进度事件：load, process(6min)</summary>

load:
```js
let xhr = createXHR();
xhr.onload = function () {
    if (xhr.status === 200) {
        callback(xhr.responseText);
    } else {
        console.error("Failed Get: ", xhr.status);
    }
};
```
process:
```js
    function sendProcess() {
        let xhr = createXHR();
        xhr.onload = function () {
            if (xhr.status === 200) {
                let data = xhr.response;
                let audio = document.createElement('audio');
                audio.onload = function () {
                    URL.revokeObjectURL(audio.src);
                };
                audio.src = window.URL.createObjectURL(data);
                console.log(audio);
                audio.setAttribute('controls', '');
                document.getElementsByTagName("body")[0].appendChild(audio);
            } else {
                console.error("Failed Get: ", xhr.status);
            }
        };
        xhr.onprogress = function (event) {
            process.innerHTML = "lengthComputable:" + event.lengthComputable +
                "position:" + event.loaded + "totalSize:" + event.total;
            /*console.log("lengthComputable:", event.lengthComputable,
                "position:", event.position, "totalSize:", event.totalSize);*/
        };
        xhr.open("get", "https://demo.xiaohuochai.site/myocean.mp3", true);
        xhr.responseType = 'blob';
        xhr.send(null);
    }
```
</details>

<details>
<summary>9. CORS的实现， 什么是Preflight技术？ 为什么、什么时候出现OPTIONS请求？(5min)</summary>

1. 实现CORS： 见书
2. 示例演示OPTIONS预检请求的出现(见示例5): [参考](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Access_control_CORS#%E5%8A%9F%E8%83%BD%E6%A6%82%E8%BF%B0)
    1. 自定义header
    2. 简单请求之外的请求
    3. 不同类型的body
</details>

<details>
<summary>10. 其他实现CORS的方式（了解-5min）</summary>

1. Image src
2. JSONP: http://freegeoip.net/json/?callback=handleResponse, 
3. Comet: 轮询；长连接。[demo](https://www.cnblogs.com/xiaoMzjm/p/3896108.html)，[demo2](https://blog.csdn.net/xiao__gui/article/details/38331225)
4. websocket: 双向通信（比如聊天室）
```js
<label for="text">input</label><input type="text" id="text">
<button id="btnSend">send message</button>
<div id="msg"></div>
<script>
    let output = document.getElementById("msg");

    let userId = "jimo";
    let websocket = new WebSocket("ws://localhost:8088/api/ID=" + userId);

    websocket.onopen = function () {
        console.log("connected");
    };
    websocket.onclose = function () {
        console.log("closing");
    };
    websocket.onmessage = function (msg) {
        console.log(msg);
        let old = output.innerText;
        output.innerText = old + "\n >>> " + msg.data;
    };

    let btnSend = document.getElementById("btnSend");
    let input = document.getElementById("text");
    btnSend.onclick = function () {
        if (input) {
            websocket.send(input.value);
        }
    };
</script>
```
</details>

<details>
<summary>最后： 实践教学，前后端参数请求协同</summary>

# GET请求
get请求很简单，没有请求体，传参放在url后： `host/uri/?key=val`

后台接受也很简单，可以放在参数里：
```java
get(String key){}
```
但是，鉴于GET请求是使用最多的，并且RESTful形式兴起，我们还是建议使用get， 毕竟速度快，简洁。

## 原来的key-value形式
```js
    net.get("/user", {name: "kkkkk"}, function (data) {
        console.log(data);
    });
```
java:
```java
    @GetMapping("/user")
    public User getUser(String name) {
        if (name == null) {
            return new User("hehe", "wuqyeewiu");
        }
        return new User(name, "123456");
    }
```
## RESTful形式
js:
```js
    net.get("/user/jimojimo", {name: "kkkkk"}, function (data) {
        console.log(data);
    });
```
java:
```java
    @GetMapping("/user/{name}")
    public User getUser2(@PathVariable String name) {
        return new User(name, "123456");
    }
```
虽然在data里也给了name参数，但最后name=jimojimo，因为请求的url变成了：`http://localhost:8088/user/jimojimo?name=kkkkk`

# post默认请求
后台什么都不加：
```java
    @PostMapping("/user")
    public User postUser(User user, String other) {
        System.out.println("------------" + other);
        return user;
    }
```
前端也默认：
```js
    function sendFormType(url) {
        // 默认格式，发送js对象
        net.post(url, {username: "hehe", password: "123324324", other: "hehehehe"}, function (data) {
            console.log(data);
        });

        // 默认格式，发送form序列化结果
        let serialize = $("#form").serialize();
        console.log(serialize);
        net.post(url, serialize, function (data) {
            console.log(data);
        });
    }
```
那么：前面都可以正常请求，默认`Content-Type: application/x-www-form-urlencoded; charset=UTF-8`.

后端没有的参数为默认值（null, 0等）。

# 后端使用@RequestParam
@RequestParam注解用来处理Content-Type: 为 application/x-www-form-urlencoded编码的内容。提交方式为get或post。（Http协议中，form的enctype属性为编码方式，常用有两种：application/x-www-form-urlencoded和multipart/form-data，默认为application/x-www-form-urlencoded）；

java:
```java
    @PostMapping("/userRequest")
    public User postUserRequest(@RequestParam(name = "user") User user) {
        return user;
    }
```
js:
```js
    // 默认格式，发送js对象
    net.post(url, {user: {username: "hehe", password: "123324324"}}, function (data) {
        console.log(data);
    });
```
你可能以为这样可以，但是@RequestParam本身就只支持单个参数(可以使用Map接收)， 所以会报400，后台报错如下：
```java
DefaultHandlerExceptionResolver : 
Resolved [org.springframework.web.bind.MissingServletRequestParameterException: Required User parameter 'user' is not present]
```

使用了@RequestParam,代表后台一定要这个参数，不传的话前端就不对了。所以，为了达到这个目的，需要@RequestBody
# 后台使用@RequestBody
@RequestBody注解用来处理HttpEntity（请求体）传递过来的数据，一般用来处理`非Content-Type: application/x-www-form-urlencoded`编码格式的数据；

GET请求中，因为没有HttpEntity，所以@RequestBody并不适用；

我们可以看一下Postman里非Content-Type: application/x-www-form-urlencoded格式有哪些。

所以，当使用Content-Type=application/x-www-form-urlencoded去请求这个接口就会报错了：
`415: Unsupported Media Type; Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported`

所以，很显然，我们需要换成其他类型，这里就换成`application/json`：
```js
    function sendJsonType(url) {
        // 传对象
        net.postJson(url, {username: "hehe", password: "123324324"}, function (data) {
            console.log(data);
        });

        // 传序列化后的字符串
        let param = JSON.stringify({username: "hehe", password: "123324324"});
        console.log(param);
        net.postJson(url, param, function (data) {
            console.log(data);
        });
    }
```
java:
```java
    @PostMapping("/userBody")
    public User postUserBody(@RequestBody User user) {
        return user;
    }
```
会发现传对象是400错误，原因很简单： 那不是正确的json字符串，后台解析失败了：
```java
HttpMessageNotReadableException: JSON parse error: 
Unrecognized token 'username': was expecting ('true', 'false' or 'null'); 
nested exception is com.fasterxml.jackson.core.JsonParseException: 
Unrecognized token 'username': was expecting ('true', 'false' or 'null')
 at [Source: (PushbackInputStream); line: 1, column: 10]]
```


最后就是，不可能使用@RequestBody传递多个参数：
```java
(@RequestBody User user, @RequestBody String other)
```
原因很简单： 一个请求只有一个body。

# 使用建议

如果有多个分开的基本类型参数（String，Int等），使用@RequestParam, 或不用，但推荐@RequestParam，因为可以发现前端的错误，后端也不用再进行null的参数校验。

对于一个整体对象的参数，使用@RequestBody。

如下表格：
| 前端请求| 参数形式 | 后端接收 |
| --- | --- | --- |
| get() | /path/var/var2 | @PathVariable type var |
| post() | js对象/序列化form(key=val&key2=val2) | 无修饰 |
| post() | 序列化form("p1=xx&p2=xxx") | @RequestParam p1,@RequestParam p2 |
| postJson() | JSON.stringify(obj) | @RequestBody Obj obj |

</details>
<details>
<summary></summary>


</details>
<details>
<summary></summary>


</details>
<details>
<summary></summary>


</details>
<details>
<summary></summary>


</details>
<details>
<summary></summary>


</details>