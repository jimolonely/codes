package com.jimo.jetty.handler;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义处理器
 *
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/30 17:04
 */
public class MyJsonHandler extends AbstractHandler {

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        final String uri = request.getRequestURI();

        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        response.getWriter().println("服务器回复：" + uri);

        baseRequest.setHandled(true);
    }

}
