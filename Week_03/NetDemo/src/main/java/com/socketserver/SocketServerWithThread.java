package com.socketserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServerWithThread {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
            int i = 1;
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("客户端连接数：" + i);
                Runnable runnable = new ThreadHandler(socket);
                Thread thread = new Thread(runnable);
                thread.start();
                i++;
            }
        }
    }
}

class ThreadHandler implements Runnable {
    private Socket accept;

    public ThreadHandler(Socket accept) {
        this.accept = accept;
    }

    @Override
    public void run() {
        try(InputStream in = accept.getInputStream();
            OutputStream out = accept.getOutputStream()){

        Scanner scanner = new Scanner(in,"UTF-8");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
