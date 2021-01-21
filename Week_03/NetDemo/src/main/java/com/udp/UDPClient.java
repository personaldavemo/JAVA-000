package com.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class UDPClient {
    public void send() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            buffer.put((System.currentTimeMillis() + "---->" + next).getBytes());
            buffer.flip();
            channel.send(buffer,new InetSocketAddress("127.0.0.1",8082));
            buffer.clear();
        }

        channel.close();
    }

    public static void main(String[] args) throws IOException {
        new UDPClient().send();
    }
}
