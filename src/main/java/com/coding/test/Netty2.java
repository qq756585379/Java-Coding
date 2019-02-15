package com.coding.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class Netty2 {

    /**
     * serverSelector负责轮询是否有新的连接,clientSelector负责轮询连接是否有数据可读.
     * 服务端监测到新的连接不再创建一个新的线程,而是直接将新连接绑定到clientSelector上,这样不用IO模型中1w个while循环在死等
     * clientSelector被一个while死循环包裹,如果在某一时刻有多条连接有数据可读通过 clientSelector.select(1)方法轮询出来进而批量处理
     * 数据的读写以内存块为单位
     */
    public static void main(String[] args) throws IOException {
        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();
        new Thread(() -> {
            try {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(8000));
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
                while (true) {
                    // 轮询监测是否有新的连接
                    if (serverSelector.select(1) > 0) {
                        System.out.println("有新的连接----------------");
                        Set<SelectionKey> selectionKeys = serverSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                        while (keyIterator.hasNext()) {
                            SelectionKey selectionKey = keyIterator.next();
                            if (selectionKey.isAcceptable()) {
                                try {
                                    //(1)每来一个新连接不需要创建一个线程而是直接注册到clientSelector
                                    SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                                    socketChannel.configureBlocking(false);
                                    socketChannel.register(clientSelector, SelectionKey.OP_READ);
                                } finally {
                                    keyIterator.remove();
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    // (2)批量轮询是否有哪些连接有数据可读
                    if (clientSelector.select(1) > 0) {
                        Set<SelectionKey> selectionKeys = serverSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                        while (keyIterator.hasNext()) {
                            SelectionKey selectionKey = keyIterator.next();
                            if (selectionKey.isReadable()) {
                                try {
                                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    //(3)读取数据以块为单位批量读取
                                    socketChannel.read(byteBuffer);
                                    byteBuffer.flip();
                                    System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuffer).toString());
                                } finally {
                                    keyIterator.remove();
                                    selectionKey.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}

class NettyServer {
    private static final int BEGIN_PORT = 8000;
    public static final String CLIENT_VALUE = "clientValue";
    private static final String SERVER_NAME_VALUE = "nettyServer";
    public static final AttributeKey<Object> CLIENT_KEY = AttributeKey.valueOf("clientKey");
    private static final AttributeKey<Object> SERVER_NAME_KEY = AttributeKey.valueOf("serverName");

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        System.out.println("服务端启动中");
                        System.out.println(channel.attr(SERVER_NAME_KEY).get());

                        channel.pipeline().addLast(new StringDecoder());
                        channel.pipeline().addLast(new SimpleChannelInboundHandler<Object>() {
                            @Override
                            protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                                System.out.println("messageReceived------");
                                System.out.println(o);
                            }
                        });
                    }
                }).attr(SERVER_NAME_KEY, SERVER_NAME_VALUE)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childAttr(CLIENT_KEY, CLIENT_VALUE)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        System.out.println(channel.attr(CLIENT_KEY).get());
                    }
                });

        bind(serverBootstrap, BEGIN_PORT);
    }

    private static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(BEGIN_PORT).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}

class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast(new StringEncoder());
            }
        });

        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();
        while (true) {
            channel.writeAndFlush(new Date() + ": hello world!");
            Thread.sleep(2000);
        }
    }
}