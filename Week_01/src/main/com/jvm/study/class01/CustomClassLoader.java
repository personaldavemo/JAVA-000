package com.jvm.study.class01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
