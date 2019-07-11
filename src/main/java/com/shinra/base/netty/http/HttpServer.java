package com.shinra.base.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author Godzilla
 * @Date 2019/7/10 16:02
 */
public class HttpServer {

    private static final int PORT = 8909;
    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workGroup = new NioEventLoopGroup();


    private void open() throws InterruptedException {
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new HttpInitializer());
        Channel channel = serverBootstrap.bind(PORT).sync().channel();
        System.out.println("访问地址 http://127.0.0.1:%d/'"+PORT);
        channel.closeFuture().sync();
    }

    public void close(){
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }


    public static void main(String[] args) {

        HttpServer httpServer=new HttpServer();
        try {
            httpServer.open();
        } catch (InterruptedException e) {
            System.out.println("http 服务启动失败");
        }
        httpServer.close();
    }


}
