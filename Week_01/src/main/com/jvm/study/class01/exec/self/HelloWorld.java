package com.jvm.study.class01.exec.self;

import java.io.IOException;

/**
 * jps:查看java系统进程
 * jcmd <pid> help
 * VM.system_properties 运行时配置
 * java.ext.dirs=E\:\\java\\jdk1.8.0_221\\jre\\lib\\ext;C\:\\WINDOWS\\Sun\\Java\\lib\\ext
 * java.class.path=E\:\\java\\jdk1.8.0_221\\...
 * 同一个类加载器，类名一样
 * ClassLoader instance id + PackageName + ClassName
 */
public class HelloWorld {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");
        System.in.read();
    }
}
