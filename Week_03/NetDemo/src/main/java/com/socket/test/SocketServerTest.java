package com.socket.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServerTest {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
            //阻塞等待客户端连接
            try (Socket socket = serverSocket.accept()) {
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                try (Scanner scanner = new Scanner(in,"UTF-8")) {
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,"UTF-8"),true);
                    writer.println("Hello,Enter BYE to exit.");

                    boolean done = false;
                    while (!done && scanner.hasNextLine()){
                        String line = scanner.nextLine();
                        writer.println(line);
                        if (line.trim().equals("BYE")){
                            done = true;
                        }
                    }
                }
            }
        }
    }
}
