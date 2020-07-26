package com.bigdata.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandler implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    public TimeClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void stop(){
        this.stop = true;
    }


    @Override
    public void run() {
        try {
            doConnect();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handlerInput(key);
                    }catch (Exception e){
                        if (key!=null){
                            key.cancel();
                            key.channel().close();
                        }
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void handlerInput(SelectionKey key) throws IOException{
        if (key.isValid()){
            SocketChannel socketChannel = (SocketChannel) key.channel();
            //判断是否连接成功
            if (key.isConnectable()){
                socketChannel.register(selector,SelectionKey.OP_READ);
                doWrite(socketChannel);
            }else System.exit(1);
        }
        if (key.isReadable()){
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(readBuffer);
            if (read>0){
                readBuffer.flip();
                byte[] bytes = new byte[readBuffer.remaining()];
                String body = new String(bytes,"UTF-8");
                System.out.println("now is "+ body);
                this.stop = true;
            }else if (read<0){
                key.cancel();
                key.channel().close();
            }else ;
        }
    }

    private void doConnect() throws IOException{
        if (socketChannel.connect(new InetSocketAddress(host,port))){
            socketChannel.register(selector,SelectionKey.OP_READ);
            doWrite(socketChannel);
        }else socketChannel.register(selector,SelectionKey.OP_CONNECT);
    }

    private void doWrite(SocketChannel socketChannel) throws IOException{
        byte[] req = "query time order".getBytes();
        ByteBuffer writeBudder = ByteBuffer.allocate(req.length);
        writeBudder.put(writeBudder).flip();
        socketChannel.write(writeBudder);
        if (!writeBudder.hasRemaining()){
            System.out.println("send order 2 server success!");
        }
    }


}
