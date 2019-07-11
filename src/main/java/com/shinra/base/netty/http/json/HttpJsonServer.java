package com.shinra.base.netty.http.json;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Godzilla
 * @Date 2019/7/11 10:45
 */
public class HttpJsonServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpJsonServer.class);
    private static final int PORT = 8870;
    private EventLoopGroup workGroup = new NioEventLoopGroup(1);
    private EventLoopGroup bossGroup = new NioEventLoopGroup();


    private void connect() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new HttpJsonServerInitializer());

        Channel ch = bootstrap.bind(PORT).sync().channel();
        logger.info("Netty http server listening on port " + PORT);
        ch.closeFuture().sync();

    }


    private void close() {
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }


    public static void main(String[] args) throws InterruptedException {
        HttpJsonServer httpJsonServer = new HttpJsonServer();
        httpJsonServer.connect();

    }


}
