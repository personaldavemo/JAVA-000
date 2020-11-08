package com.gc.exec.self;

public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        //堆中首次出现，表示同意引用
        String str1 = new StringBuffer("Str1").append("Str2").toString();
        System.out.println(str1.intern() == str1);
        String str3 = new StringBuffer("str3").append("str4").toString();
        System.out.println(str3.intern() == str3);
    }
}
