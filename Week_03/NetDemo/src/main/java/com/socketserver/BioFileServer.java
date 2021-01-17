package com.socketserver;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BioFileServer extends ServerSocket {
    private static final int SERVER_PORT = 8081;

    private static final int BYTE_VAL = 1024;

    private static final String FILE_PATH = "D:\\java-study\\JAVA-000\\Week_03\\NetDemo\\src\\main\\resources\\";


    public BioFileServer() throws IOException {
        //监听端口与连接端口一致
        super(SERVER_PORT);
    }

    public void start() throws IOException {
        while (true) {
            //阻塞等待连接
            Socket socket = this.accept();
            new Thread(new Handler(socket)).start();
        }
    }

    class Handler implements Runnable {
        private Socket socket;

        private DataInputStream dis;

        private FileOutputStream fos;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                dis = new DataInputStream(socket.getInputStream());
                /*文件名*/
                String fileName = dis.readUTF();
                /*文件长度*/
                long length = dis.readLong();
                File file = new File(FILE_PATH);
                if (!file.exists()) {
                    file.mkdir();
                }
                File f = new File(file.getAbsoluteFile() + File.separator + fileName);
                fos = new FileOutputStream(f);
                byte[] bytes = new byte[BYTE_VAL];
                int lengthOut;
                while ((lengthOut = dis.read(bytes,0,bytes.length)) != -1) {
                    fos.write(bytes,0,lengthOut);
                    fos.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    fos.close();
                    dis.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BioFileServer server = new BioFileServer();
        server.start();
    }
}
