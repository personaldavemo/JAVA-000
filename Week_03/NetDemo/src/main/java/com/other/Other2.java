package com.other;

public class Other2 {
//    private static Other2 other2;
//    private Other2() {}
//
//    public static Other2 getOther2() {
//        if (other2 == null){
//            other2 = new Other2();
//        }
//        return other2;
//    }

//    private static Other2 other2 = new Other2();
//
//    private Other2() {}
//
//    public static Other2 getOther2() {
//        return other2;
//    }

//    private static volatile Other2 other2;
//    private Other2() {}
//    private static Other2 getInstance() {
//        if (other2 == null){
//            synchronized (Other2.class) {
//                if (other2 == null) {
//                    other2 = new Other2();
//                }
//            }
//        }
//        return other2;
//    }

//    private static class Other2Create {
//        private static final Other2 other2 = new Other2();
//    }
//    private Other2() {}
//
//    public static final  Other2 getInstance() {
//        return Other2Create.other2;
//    }
}

enum Singelton {
    INSTANCE;
}