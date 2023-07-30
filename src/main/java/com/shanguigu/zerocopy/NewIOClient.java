package com.shanguigu.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(7001));
        String fileName = "lantern-installer.dmg";
        // 得到文件channel
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        // 准备发送
        long startTime = System.currentTimeMillis();

        // 在linux下，一个 transferTo 方法就可以完成传输
        // 在windows下，一次调用 transferTo 最多只能发送8M的文件，就需要分段传输。而且要注意传输时的位置
        // 传输时的位置
        // transferTo 底层用了零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println("发送的总的字节数 = "+ transferCount + " 耗时："+(System.currentTimeMillis() - startTime));

        fileChannel.close();

    }
}
