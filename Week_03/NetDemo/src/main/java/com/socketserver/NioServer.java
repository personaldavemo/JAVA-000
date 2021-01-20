package com.socketserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NioServer {
    private Charset charset = Charset.forName("UTF-8");
    private static final int BYTE_VAL = 1024;
    private static final int PORT = 8081;

    /**
     * 服务器保存客户端文件
     */
    static class Client {
        String fileName;
        long fileSize;
        InetSocketAddress inetSocketAddress;
        FileChannel outChannel;
    }

    private ByteBuffer byteBuffer = ByteBuffer.allocate(BYTE_VAL);
    Map<SelectableChannel, Client> clientMap = new HashMap<>();

    public void start() throws IOException {
        //获取选择器
        Selector selector = Selector.open();
        //获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocketChannel.configureBlocking(false);
        //4.绑定连接
        InetSocketAddress address = new InetSocketAddress(PORT);
        serverSocket.bind(address);
        //5.接收新连接
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 6.轮询感兴趣的IO
        while (selector.select() > 0) {
            //7.选择集合
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                //8.获取单个键
                SelectionKey key = it.next();
                if (key.isAcceptable()) {
                    //9.是新连接，就获取新的客户端连接
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = server.accept();
                    if (socketChannel == null) {
                        continue;
                    }
                    socketChannel.configureBlocking(false);
                    //注册客户端
                    SelectionKey selectionKey = socketChannel.register(selector,SelectionKey.OP_READ);
                    Client client = new Client();
                    client.inetSocketAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
                    clientMap.put(socketChannel, client);
                } else if (key.isReadable()) {
                    processData(key);
                }
                it.remove();
            }
        }

    }

    private void processData(SelectionKey key) throws IOException {
        Client client = clientMap.get(key.channel());

        SocketChannel socketChannel = (SocketChannel) key.channel();
        int num = 0;
        try {
            byteBuffer.clear();
            while ((num = socketChannel.read(byteBuffer)) > 0) {
                byteBuffer.flip();
                if (client.fileName == null) {
                    String fileName = charset.decode(byteBuffer).toString();
                    String destPath = "D:\\java-study\\JAVA-000\\Week_03\\NetDemo\\src\\main\\resources\\";
                    File file = new File(destPath);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    client.fileName = fileName;
                    String fullName = file.getAbsolutePath() + File.separatorChar + fileName;

                    File f = new File(fullName);
                    FileChannel fileChannel = new FileOutputStream(f).getChannel();
                    client.outChannel = fileChannel;
                } else if (client.fileSize == 0) {
                    long fileLength = byteBuffer.getLong();
                    client.fileSize = fileLength;
                } else {
                    client.outChannel.write(byteBuffer);
                }
                byteBuffer.clear();
            }
            key.channel();
        } catch (IOException e) {
            key.channel();
            e.printStackTrace();
            return;
        }
        if (num == -1) {
            client.outChannel.close();
            key.channel();
        }
    }

    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer();
        server.start();
    }
}
