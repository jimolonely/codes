package com.jimo.jetty;

import com.jimo.jetty.handler.MyJsonHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/30 16:58
 */
public class Main {

    public static void main(String[] args) throws Exception {

        final Server server = new Server();
        final ServerConnector connector = new ServerConnector(server);
        connector.setPort(8081);
        server.setConnectors(new Connector[]{connector});

        final HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{new MyJsonHandler(), new DefaultHandler()});
        server.setHandler(handlerList);

        server.start();

        System.out.println(server.dump());

        server.join();
    }
}
