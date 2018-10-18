package com.power.learn.nt4._6_streamdemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * 处理服务端 channel.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws InterruptedException { // (1)
        /**
         * 修改长度从 4 到 5
         */
        final ByteBuf time = ctx.alloc().buffer(1); // (2)
        int i = (int) (System.currentTimeMillis() / 1000L + 2208988800L);
        System.out.println("i = " + i);

//        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        time.writeByte(1);
        ctx.writeAndFlush(time); // (3)
        TimeUnit.MICROSECONDS.sleep(500L);

        time.writeByte(1);
        ctx.writeAndFlush(time); // (3)
        TimeUnit.MICROSECONDS.sleep(500L);

        time.writeByte(1);
        ctx.writeAndFlush(time); // (3)
        TimeUnit.MICROSECONDS.sleep(500L);

        time.writeByte(1);
        final ChannelFuture f = ctx.writeAndFlush(time);

        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        }); // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

