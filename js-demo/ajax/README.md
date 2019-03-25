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

1. 2005年，Jesse James Garrett发表的文章： 
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
<summary>8.进度事件：load, process</summary>

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
<summary>9. CORS的实现， 什么是Preflight技术？ 为什么、什么时候出现OPTIONS请求？</summary>

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
<details>
<summary></summary>


</details>