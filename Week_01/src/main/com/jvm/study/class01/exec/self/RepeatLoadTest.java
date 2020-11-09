package com.jvm.study.class01.exec.self;


import java.net.URL;
import java.net.URLClassLoader;

/**
 * public class ClassTest {
 * public static int SIZE = 10;
 * static {
 * System.out.println("static block");
 * }
 * public static String getStr(){
 * return "Hello";
 * }
 * public void doSomething(){
 * System.out.println("init" + SIZE);
 * }
 * }
 * <p>
 * -----------------------------------------------------
 * ClassTest loader is java.net.URLClassLoader@1b6d3586
 * static block
 * init10
 * null
 * Hello
 * <p>
 * ClassTest loader is java.net.URLClassLoader@1b6d3586
 * init10
 * null
 * Hello
 * 类不会重复加载，不会加载新的代码
 * 初始化顺序
 * 对象创建 -- 静态方法 -- 静态代码块 -- 静态变量 -- 普通方法
 * <p>
 * --------------------------------------------------
 * 卸载时机
 * Class所有实例被GC
 * 加载该类的ClassLoader实例被GC
 * -verbose:class
 */
public class RepeatLoadTest {
    public static void main(String[] args) throws Exception {
        URL classUrl = new URL("file:D:\\");
        URLClassLoader classLoader = new URLClassLoader(new URL[]{classUrl});
        while (true) {
            if (classLoader == null) {
                break;
            }
            //热加载机制
            //URLClassLoader classLoader = new URLClassLoader(new URL[]{classUrl});
            //双亲委派,父加载器已完成加载不会再重复加载
            URLClassLoader loader = new URLClassLoader(new URL[]{classUrl}, classLoader);
            //Class clazz = classLoader.loadClass("ClassTest");
            Class clazz = loader.loadClass("ClassTest");
            System.out.println("ClassTest loader is " + clazz.getClassLoader());
            Object instance = clazz.newInstance();
            Object val_1 = clazz.getMethod("doSomething").invoke(instance);
            System.out.println(val_1);
            Object val_2 = clazz.getMethod("getStr").invoke(instance);
            System.out.println(val_2);

            Thread.sleep(3000L);
            System.out.println();

            //GC
            instance = null;
            classLoader = null;

        }
        System.gc();
        Thread.sleep(10000L);

    }
}
