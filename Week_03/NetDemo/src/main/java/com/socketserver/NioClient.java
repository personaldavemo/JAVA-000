package com.socketserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NioClient {
    private Charset charset = Charset.forName("UTF-8");
    public NioClient() {}

    public void sendFile() throws IOException {
        String sourcePath = "D:\\java-study\\JAVA-000\\Week_03\\NetDemo\\src\\main\\resources\\client\\test.txt";
        String destPath = "D:\\java-study\\JAVA-000\\Week_03\\NetDemo\\src\\main\\resources\\";
        File file = new File(sourcePath);
        if (!file.exists()) {
            return;
        }
        FileChannel fileChannel = new FileInputStream(file).getChannel();
        SocketChannel socketChannel =  SocketChannel.open();
        socketChannel.socket().connect(new InetSocketAddress("127.0.0.1",8081));
        socketChannel.configureBlocking(false);

        while (!socketChannel.finishConnect()) {

        }

        ByteBuffer buffer = charset.encode(destPath);
        socketChannel.write(buffer);

        ByteBuffer content = ByteBuffer.allocate(1024);
        content.putLong(file.length());

        content.flip();
        socketChannel.write(content);
        content.clear();

        int length = 0;
        long progress = 0;
        while ((length = fileChannel.read(content)) > 0) {
            content.flip();
            socketChannel.write(buffer);
            buffer.clear();
            progress += length;
        }

        if (length == -1) {
            fileChannel.close();
            socketChannel.shutdownOutput();
            socketChannel.close();
        }
    }

    public static void main(String[] args) throws IOException {
        NioClient client = new NioClient();
        client.sendFile();
    }
}
