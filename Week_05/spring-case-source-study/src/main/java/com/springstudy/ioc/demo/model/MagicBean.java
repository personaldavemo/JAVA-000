package com.springstudy.ioc.demo.model;

public class MagicBean {
    private String magic;

    public String getMagic() {
        return magic;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }

    public void info() {
       System.out.println("Condition test");
   }
}
