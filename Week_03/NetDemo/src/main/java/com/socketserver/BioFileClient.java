package com.socketserver;

import java.io.*;
import java.net.Socket;

public class BioFileClient extends Socket {
    private Socket socket;
    private FileInputStream fis;
    private DataOutputStream dos;
    private static final int BYTE_VAL = 1024;

    public BioFileClient() throws IOException {
        //监听端口与连接端口一致
        super("127.0.0.1",8081);
        this.socket = this;
    }

    public void sendFile() throws IOException {
        File file = new File("D:\\java-study\\JAVA-000\\Week_03\\NetDemo\\src\\main\\resources\\hello.txt");
        if (file.exists()) {
            fis = new FileInputStream(file);
            dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF("copy_" + file.getName());
            dos.flush();
            dos.writeLong(file.length());
            dos.flush();

            byte[] bytes = new byte[BYTE_VAL];
            int length = 0;
            long progress = 0;
            while ((length = fis.read(bytes,0,bytes.length)) != -1) {
                dos.write(bytes,0,length);
                dos.flush();
                progress = progress + length;
            }

            dos.close();
            fis.close();
            socket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        BioFileClient client = new BioFileClient();
        client.sendFile();
    }
}
