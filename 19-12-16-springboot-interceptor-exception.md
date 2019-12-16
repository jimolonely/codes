springboot

使用`@ControllerAdvice`+`@ExceptionHandler`可以捕获全局异常。

但是不会捕获拦截器里的异常，于是被springboot默认的异常捕获处理了。

这对于统一异常返回不太好。

暂时想到的办法是：在拦截器里捕获所有异常，然后写统一的返回结果：

```java
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            throw new UnauthorizedException("无权访问该页面");
        } catch (Exception e) {
            try {
                if (e instanceof IllegalArgumentException) {
                    return writeResult(HttpStatus.SC_BAD_REQUEST, e.getMessage(), response);
                }
                return writeResult(HttpStatus.SC_FORBIDDEN, e.getMessage(), response);
            } catch (IOException ex) {
                log.error("写回客户端错误：", ex);
            }
            return false;
        }
    }

    private boolean writeResult(int code, String msg, HttpServletResponse response) throws IOException {
        final ResponseResult<Object> res = ResponseResult.create(code, msg);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().write(JSON.toJSONString(res));
        return false;
    }
```

