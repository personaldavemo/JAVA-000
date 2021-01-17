package com.self.io;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Logger;

/**
 *Nio基本组件
 */
public class NIODemo {

    private static Logger logger = Logger.getLogger("com.self.io.NIODemo");
    /**
     * init byte缓冲区
     */
    private static ByteBuffer byteBuffer = null;

    private static final int ONE_BYTE = 1024;

    /**
     * buffer实例
     */
    public static void bufferTest() {
        //1.分配Buffer空间
        byteBuffer = ByteBuffer.allocate(ONE_BYTE);
        printLogger();

        //2.写入缓冲区
        byte[] bytes = new byte[]{1,2,3,4,5};
        //预期写入写入5bit
        byteBuffer.put(bytes);
        logger.info("After put in buffer");
        printLogger();

        // 3.读取缓冲区
        byteBuffer.flip();
        for (int i = 0; i < 5; i++) {
            byte data = byteBuffer.get();
            logger.info("data=" + data);
        }
        printLogger();

        //BufferUnderflowException
        //byteBuffer.get();

        //4.重新读取
        byteBuffer.rewind();
        for (int i = 0; i < 5 ; i++) {
            if (i == 2) {
                //临时记录一个标记位
                byteBuffer.mark();
            }
            //移动指针
            byteBuffer.get();
        }

        //重上一次mark记录开始读取
        byteBuffer.reset();
        printLogger();

        for (int i = byteBuffer.position(); i < byteBuffer.limit() ; i++) {
            byte resetReadData = byteBuffer.get();
            logger.info("2-read-data=" + resetReadData);
        }

        //切换写入模式
        byteBuffer.clear();
        //byteBuffer.compact();
    }

    /**
     * NIO读写文件
     * @throws IOException
     */
    public static void fileChannelTest() throws IOException {

        //文件缓冲区
        FileInputStream fis = new FileInputStream("D:\\java-study\\JAVA-000\\Week_03\\NetDemo\\src\\main\\resources\\hello.txt");
        FileChannel inChannel = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(ONE_BYTE);
        int length;
        while ((length = inChannel.read(buffer)) != -1) {
            buffer.flip();
            byte[] readData = buffer.array();
            logger.info("read data from file:" + new String(readData,0,length));
        }
        FileOutputStream fos = new FileOutputStream("D:\\java-study\\JAVA-000\\Week_03\\NetDemo\\src\\main\\resources\\outputFile.txt");
        FileChannel outChannel = fos.getChannel();
        buffer.rewind();
        while ((length = outChannel.write(buffer)) != 0) {
        }
        logger.info("write data in file success");

        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\java-study\\JAVA-000\\Week_03\\NetDemo\\src\\main\\resources\\outputFile.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        buffer.rewind();
      while ((length = fileChannel.read(buffer)) != -1) {
            buffer.flip();
            byte[] readData = buffer.array();
            logger.info("2-read data from file:" + new String(readData,0,length));
        }

        outChannel.close();
        fos.close();
        inChannel.close();
        fis.close();
        fileChannel.close();
        randomAccessFile.close();
    }

    /**
     * 字符流读取文件
     * @throws IOException
     */
    public static void readFile() throws IOException {
        File file = new File("D:\\java-study\\JAVA-000\\Week_03\\NetDemo\\src\\main\\resources\\hello.txt");
        Reader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String data;
        while ((data = bufferedReader.readLine()) != null){
            logger.info("data: " + data);
        }

        bufferedReader.close();
        reader.close();
    }

    /**
     * 普通复制文件
     * @throws IOException
     */
    public static void fileCopy() throws IOException {
        InputStream is = null;
        OutputStream os = null;
        File source = new File("D:\\java-study\\JAVA-000\\Week_03\\NetDemo\\src\\main\\resources\\hello.txt");
        File dest = new File("D:\\java-study\\JAVA-000\\Week_03\\NetDemo\\src\\main\\resources\\helloCopy.txt");

        if (!dest.exists()) {
            dest.createNewFile();
        }

        is = new FileInputStream(source);
        os = new FileOutputStream(dest);
        byte[] buffer = new byte[ONE_BYTE];
        int read;
        while ((read = is.read(buffer)) != -1) {
            os.write(buffer,0,read);
        }
        os.flush();

        os.close();
        is.close();
    }

    public static void filePath(String path) throws IOException {
        File file = new File(path);
        if (file.isFile()) {
            logger.info("file:" + file.getCanonicalPath());
        } else if (file.isDirectory()) {
            logger.info("directory:" + file.getCanonicalPath());
            File[] subFiles = file.listFiles();
            if (null == subFiles) {
                return;
            }
            for (File f : subFiles) {
                filePath(f.getCanonicalPath());
            }
        }else {
            logger.info("error");
        }
    }

    public static void nioCopy() throws IOException {
        File source = new File("D:\\java-study\\JAVA-000\\Week_03\\NetDemo\\src\\main\\resources\\hello.txt");
        File dest = new File("D:\\java-study\\JAVA-000\\Week_03\\NetDemo\\src\\main\\resources\\helloCopyNio2.txt");

        if (!dest.exists()) {
            dest.createNewFile();
        }

        FileInputStream fis = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(dest);
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        int length;
        ByteBuffer buffer = ByteBuffer.allocate(ONE_BYTE);
        long size = inChannel.size();
        long pos = 0;
        long count = 0;
//        while((length = inChannel.read(buffer)) != -1) {
//            buffer.flip();
//
//            int write;
//            while ((write = outChannel.write(buffer)) != 0) {
//                System.out.println("Bytes:" + write);
//            }
//            buffer.clear();
//        }
        while (pos < size) {
            count = size - pos > ONE_BYTE ? 1024 : size - pos;
            pos = pos + outChannel.transferFrom(inChannel,pos,count);
        }
        outChannel.force(true);
        outChannel.close();
        fos.close();
        inChannel.close();
        fis.close();
    }

    private static void printLogger() {
        logger.info("position=" + byteBuffer.position());
        logger.info("limit=" + byteBuffer.limit());
        logger.info("capacity=" + byteBuffer.capacity());
    }


    public static void main(String[] args) throws IOException {
        //bufferTest();
        //fileChannelTest();
        //readFile();
        //fileCopy();
        //filePath("D:\\java-study\\JAVA-000");
        nioCopy();
    }

}
