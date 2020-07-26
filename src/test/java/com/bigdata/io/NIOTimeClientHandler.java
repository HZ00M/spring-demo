package com.bigdata.io;

import com.bigdata.demo.netty.ServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

public class NIOTimeClientHandler extends SimpleChannelInboundHandler {
    private final byte[] firstMessage;

    public NIOTimeClientHandler(){
        firstMessage  = "query time order".getBytes();
    }

    //客户端与服务端TCP链路建立成功后，NIO线程会调用此方法
    @Override
    public void channelActive(ChannelHandlerContext ctx)throws Exception{
        ByteBuf message = null;
        for (int i =0;i<100;i++){
            message = Unpooled.buffer(firstMessage.length);
            message.writeBytes(firstMessage);
            ctx.writeAndFlush(message);//将请求消息发送给客户端
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        System.out.println("收到服务端消息");
        ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"UTF-8");
        System.out.println("now is "+body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
