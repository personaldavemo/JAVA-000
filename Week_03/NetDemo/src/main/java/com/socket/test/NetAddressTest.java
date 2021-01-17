package com.socket.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetAddressTest {
    public static void main(String[] args) throws UnknownHostException {
        String host = "www.baidu.com";
        InetAddress[] addresses = InetAddress.getAllByName(host);
        for (InetAddress address : addresses){
            System.out.println(address);
        }
    }
}
