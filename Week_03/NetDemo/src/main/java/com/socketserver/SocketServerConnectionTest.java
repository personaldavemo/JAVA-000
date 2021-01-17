package com.socketserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServerConnectionTest implements Runnable {
    private static final int BYTE_VAL = 1024;

    private static Logger logger = Logger.getLogger("com.socketserver.SocketServerConnectionTest");

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            //多线程TCP连接
            while (!Thread.interrupted()) {
                //接受一个连接,没有连接阻塞
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                new Thread(handler).start();

            }
        } catch (IOException e) {
            logger.log(Level.ALL,"socket IO error.");
        }
    }

    static class Handler implements Runnable {
        final Socket socket;

        Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            while (true) {
                byte[] input = new byte[BYTE_VAL];
                try {
                    socket.getInputStream().read(input);
                    byte[] output = null;
                    socket.getOutputStream().write(output);
                } catch (IOException e) {
                    logger.log(Level.ALL,"socket IO error.");
                }

            }
        }
    }

    public static void main(String[] args) {
        SocketServerConnectionTest test = new SocketServerConnectionTest();
        test.run();
    }
}
