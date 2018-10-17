package com.power.learn.netInAction.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group)   //指定EventLoopGroup 以处理客户端的时间；需要适用于NIO的实现
                .channel(NioSocketChannel.class)  //适用于NIO传输的Channel类型
                .remoteAddress(new InetSocketAddress(host, port))
                //在创建Channel时，向ChannelPipeline中添加一个EchoClientHandler实例
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new EchoClientHandler());
                    }
                });
            ChannelFuture f = b.connect().sync(); //连接到远程节点，阻塞等待知道连接完成
            f.channel().closeFuture().sync();  //阻塞，知道Channel关闭
        }finally {
            group.shutdownGracefully().sync(); //关闭线程池并且释放所有的资源
        }
    }

    public static void main(String[] args) throws Exception{
        if (args.length < 1) {
            System.err.println("Usage: " + EchoClient.class.getSimpleName() + " <host> <port>");
            return;
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        new EchoClient(host, port).start();
    }
}
