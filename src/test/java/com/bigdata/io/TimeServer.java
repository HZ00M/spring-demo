package com.bigdata.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {
    public static void main(String[] args) throws Exception {
        int port = 8081;
        try {
            if (args != null && args.length > 0){
                port = Integer.valueOf(args[0]);
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

         MultiTimeServer timeServer = new MultiTimeServer(port);
        new Thread(timeServer,"NIO-TIMESERVER-001").start();
    }
}
