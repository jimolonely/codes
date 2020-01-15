

删除cookie

```java
    private void setCookieGone(HttpServletResponse response) {
		// value必须设成null
        final Cookie cookie = new Cookie(KEY, null);
        // 不同环境不一样
        cookie.setDomain(frontDomain);
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        // delete cookie
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
```

[https://stackoverflow.com/questions/9821919/delete-cookie-from-a-servlet-response](https://stackoverflow.com/questions/9821919/delete-cookie-from-a-servlet-response)

