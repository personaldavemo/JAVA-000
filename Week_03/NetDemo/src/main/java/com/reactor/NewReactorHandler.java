package com.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewReactorHandler implements Runnable {
    final SocketChannel socketChannel;
    final SelectionKey selectionKey;
    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    static final int RECIVE = 0, SEND = 1;
    int state = RECIVE;
    static ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public NewReactorHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        this.selectionKey = socketChannel.register(selector, 0);
        this.selectionKey.attach(this);
        this.selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }



    public synchronized void handler() {
        try {
            if (state == SEND) {
                socketChannel.write(byteBuffer);
                byteBuffer.flip();
                selectionKey.interestOps(SelectionKey.OP_READ);
                state = RECIVE;
            } else if (state == RECIVE) {
                int length = 0;
                while ((length = socketChannel.read(byteBuffer)) > 0) {
                    System.out.println(new String(byteBuffer.array(), 0, length));
                }
                byteBuffer.flip();
                selectionKey.interestOps(SelectionKey.OP_WRITE);
                state = SEND;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        threadPool.execute(new AsynTask());
    }

    class AsynTask implements Runnable {
        @Override
        public void run() {
            NewReactorHandler.this.handler();
        }
    }
}
