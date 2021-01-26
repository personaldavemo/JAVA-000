package com.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class NewReactorServer {
    ServerSocketChannel serverSocketChannel;
    Selector[] selectors = new Selector[2];
    /**多核拆封reactor,和selector一致*/
    SubReactor[] subReactors = null;
    /**确保每个选择器是原子的*/
    AtomicInteger next = new AtomicInteger(0);

    NewReactorServer() throws IOException {
        selectors[0] = Selector.open();
        selectors[1] = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1",8081);
        serverSocketChannel.socket().bind(address);
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selectors[0], SelectionKey.OP_ACCEPT);
        selectionKey.attach(new AcceptorHandler());
        SubReactor subReactor1 = new SubReactor(selectors[0]);
        SubReactor subReactor2 = new SubReactor(selectors[1]);
        subReactors = new SubReactor[]{subReactor1, subReactor2};
    }

    public void startService() {
        new Thread(subReactors[0]).start();
        new Thread(subReactors[1]).start();
    }

    class SubReactor implements Runnable {
        final Selector selector;
        public SubReactor(Selector selector) {
            this.selector = selector;
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
                    new NewReactorHandler(selectors[next.get()], socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (next.incrementAndGet() == selectors.length) {
                next.set(0);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        NewReactorServer server = new NewReactorServer();
        server.startService();
    }
}
