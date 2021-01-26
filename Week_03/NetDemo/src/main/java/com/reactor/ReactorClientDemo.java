package com.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ReactorClientDemo {

    public void start() throws IOException {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8081);
        SocketChannel socketChannel = SocketChannel.open(address);
        socketChannel.configureBlocking(false);
        while (!socketChannel.finishConnect()) {

        }
        Processer processer = new Processer(socketChannel);
        new Thread(processer).start();
    }

    static class Processer implements Runnable {
        final Selector selector;
        final SocketChannel socketChannel;

        public Processer(SocketChannel socketChannel) throws IOException {
            selector = Selector.open();
            this.socketChannel = socketChannel;
            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    selector.select();
                    Set<SelectionKey> selected = selector.selectedKeys();
                    Iterator<SelectionKey> it = selected.iterator();
                    while (it.hasNext()) {
                        SelectionKey sk = it.next();
                        if (sk.isWritable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);

                            Scanner scanner = new Scanner(System.in);
                            if (scanner.hasNext()) {
                                SocketChannel socketChannel = (SocketChannel) sk.channel();
                                String next = scanner.next();
                                buffer.put((System.currentTimeMillis() + " >>" + next).getBytes());
                                buffer.flip();
                                // 操作三：通过DatagramChannel数据报通道发送数据
                                socketChannel.write(buffer);
                                buffer.clear();
                            }

                        }
                        if (sk.isReadable()) {
                            // 若选择键的IO事件是“可读”事件,读取数据
                            SocketChannel socketChannel = (SocketChannel) sk.channel();

                            //读取数据
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int length = 0;
                            while ((length = socketChannel.read(byteBuffer)) > 0) {
                                byteBuffer.flip();
                                System.out.println(new String(byteBuffer.array(), 0, length));
                                byteBuffer.clear();
                            }

                        }
                        //处理结束了, 这里不能关闭select key，需要重复使用
                        //selectionKey.cancel();
                    }
                    selected.clear();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws IOException {
        new ReactorClientDemo().start();
    }
}
