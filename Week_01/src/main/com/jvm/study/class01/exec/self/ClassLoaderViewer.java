package com.jvm.study.class01.exec.self;

public class ClassLoaderViewer {
    public static void main(String[] args) throws ClassNotFoundException {
        //BootStrap ClassLoader
        System.out.println(ClassLoaderViewer.class.getClassLoader().loadClass("java.lang.Object").getClassLoader());
        //Extension ClassLoader
        System.out.println(ClassLoaderViewer.class.getClassLoader().loadClass("com.sun.nio.zipfs.ZipCoder").getClassLoader());
        //Application ClassLoader
        System.out.println(ClassLoaderViewer.class.getClassLoader());

        /**
         * 双亲委派机制
         * 子加载器 <---> app <---> ext <---> bootstrap
         * 由下到上逐级委托，由上到下逐级查找
         * 首先交给父加载器去加载，一次传递到最上层
         * 父加载器无法加载时，子加载器才尝试自己加载
         * 防止重复加载和安全问题
         */
    }
}
