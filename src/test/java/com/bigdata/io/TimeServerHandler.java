package com.bigdata.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;

public class TimeServerHandler implements Runnable {
    private Socket socket ;
    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            String currentTime = null;
            String body = null;
            while (true){
                System.out.println("开始");
                body = in.readLine();
                System.out.println("结束");
                if (body == null)break;;
                System.out.println("reveive body :" + body);
                currentTime = "query time order".equalsIgnoreCase(body)? LocalTime.now().toString():"bad order";
                System.out.println(currentTime);
            }
        }catch (IOException e){
            e.printStackTrace();
            if (in !=null){
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null){
                out.close();
                out = null;
            }
            if (this.socket!=null){
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
