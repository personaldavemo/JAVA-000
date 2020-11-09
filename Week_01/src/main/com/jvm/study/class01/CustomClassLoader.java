package com.jvm.study.class01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

 /**
 * JVM读取.class
 * 1.加载 （二进制）
 * 2.验证（语义分析）
 * 3.准备（分配内存，static初始化）
 * 4.解析（类，接口，字段，类方法等）
 * 5.初始化（静态变量赋值，执行静态代码块）
 * 6.使用(创建实例对象)
 * 7.卸载（从方法区卸载）
  *  类加载器
  *  BootStrap Loader --- null 核心类库
  *  Extension ClassLoader --- 扩展类库
  *  application ClassLoader --- 用户应用程序加载（java -cp）
 */
public class CustomClassLoader extends ClassLoader {
    private byte[] getFile(String fileName) {
        try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName+".xlass");
            ByteArrayOutputStream byteOS = new ByteArrayOutputStream();) {
            byte[] res, trans;
            trans = new byte[1];
            int b = 0;
            while ((b = is.read(trans)) != -1) {
                trans[0] = (byte) (255 - trans[0]);

                byteOS.write(trans);
            }
            res = byteOS.toByteArray();
            return res;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Class<?> findClass(String name) {
            byte[] b = getFile(name);
            if (b !=null) {
                return defineClass(name, b, 0, b.length);
            }
            return null;
    }

    public static void main(String[] args) {
        try {
            Class<?> clz = new CustomClassLoader().findClass("Hello");

            Method hello = clz.getDeclaredMethod("hello");
            hello.setAccessible(true);
            hello.invoke(clz.newInstance());

        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
