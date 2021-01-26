package com.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Reactor + Handler（非阻塞执行）
 * 轮询IO事件
 *
 */
public class ReactorServerDemo implements Runnable {
    Selector selector;
    ServerSocketChannel serverSocketChannel;

    /**
     * 接收事件
     * @return
     */
    ReactorServerDemo() throws IOException {
        //打开选择器
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        //绑定服务器监听端口
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8081);
        serverSocketChannel.socket().bind(address);
        serverSocketChannel.configureBlocking(false);
        //注册选择器,接受一个连接
        SelectionKey selectionKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        //接受一个事件
        selectionKey.attach(new AcceptorHandler());
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    dispatch(selectionKey);
                }
                selectionKeySet.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void dispatch(SelectionKey selectionKey) {
        Runnable r = (Runnable) selectionKey.attachment();
        if (r != null) {
            r.run();
        }
    }


    class AcceptorHandler implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new ReactorHandler(selector, socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new ReactorServerDemo()).start();
    }
}
