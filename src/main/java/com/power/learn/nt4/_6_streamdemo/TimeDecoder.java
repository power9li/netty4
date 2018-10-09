package com.power.learn.nt4._6_streamdemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder { // (1)
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        int bf = -1;
        if ((bf = in.readableBytes()) < 4) {
            System.out.println("bf < 4, bf="+bf);
            return; // (3)
        }
        System.out.println("bf >= 4, bf= "+bf);
        out.add(in.readBytes(4)); // (4)
    }
}