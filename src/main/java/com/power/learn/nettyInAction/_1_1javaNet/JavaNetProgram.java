package com.power.learn.nettyInAction._1_1javaNet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : Power
 * @date : 2018/10/8
 *
 *
 * ===========[测试步骤]============
 * 1. 启动main方法
 * 2. telnet localhost 9000
 *    连接后,输入字符,会看到响应
 * 3. 输入 done,结束会话
 *
 *
 */
public class JavaNetProgram {

    public static void main(String[] args) throws IOException{
        int portNumber = 9000;
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();//accept()方法将阻塞到一个连接建立
        BufferedReader in = new BufferedReader((new InputStreamReader(clientSocket.getInputStream())));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String request , response;
        while ((request = in.readLine()) != null) {
            if ("Done".equalsIgnoreCase(request)) {
                break;
            }
            response = processRequest(request);
            out.println(response);
        }
    }

    private static String processRequest(String request) {
        return "resp : " + request;
    }
}
