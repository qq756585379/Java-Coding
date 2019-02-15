package com.coding.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 传统的IO模型每个连接创建成功都需要一个线程来维护,每个线程包含一个while死循环,
 * 那么1w个连接对应1w个线程,继而1w个while死循环带来如下几个问题:
 * 1.线程资源受限:线程是操作系统中非常宝贵的资源,同一时刻有大量的线程处于阻塞状态是非常严重的资源浪费,操作系统耗不起;
 * 2.线程切换效率低下:单机CPU核数固定,线程爆炸之后操作系统频繁进行线程切换,应用性能急剧下降;
 * 3.数据读写是以字节流为单位效率不高:每次都是从操作系统底层一个字节一个字节地读取数据.
 */
public class Netty {

    /**
     * Server服务端首先创建ServerSocket监听8000端口,然后创建线程不断调用阻塞方法serversocket.accept()获取新的连接
     * 当获取到新的连接给每条连接创建新的线程负责从该连接中读取数据,然后读取数据是以字节流的方式
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        new Thread(() -> {
            try {
                //(1)阻塞方法获取新的连接 
                System.out.println("等待连接--------");
                Socket socket = serverSocket.accept();
                //(2)每一个新的连接都创建一个线程,负责读取数据
                new Thread(() -> {
                    try {
                        byte[] data = new byte[1024];
                        InputStream inputStream = socket.getInputStream();
                        System.out.println("开始读取数据--------");
                        while (true) {
                            int len;
                            //(3)按照字节流方式读取数据
                            while ((len = inputStream.read(data)) != -1) {
                                System.out.println(new String(data, 0, len));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}

class IOClient {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        socket.getOutputStream().flush();
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}