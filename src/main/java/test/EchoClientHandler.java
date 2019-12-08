package test;
/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import pm.SerialPortTest;

import java.util.Date;

/**
 * Handler implementation for the echo client.  It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf firstMessage;

    /**
     * Creates a client-side handler.
     */
    public EchoClientHandler() {
        firstMessage = Unpooled.buffer(EchoClient.SIZE);
        for (int i = 0; i < firstMessage.capacity(); i ++) {
            firstMessage.writeByte(i);
        }

    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws InterruptedException { // (1)
        SerialPortTest serialPortTest = new SerialPortTest();
        serialPortTest.run();
        while (true) {
//            ByteBuf time = ctx.alloc().buffer(4); //为ByteBuf分配四个字节
//            time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//            ctx.writeAndFlush(time); // (3)
//            Thread.sleep(2000);

            ByteBuf pm = ctx.alloc().buffer(30); //为ByteBuf分配四个字节
            pm.writeBytes(SerialPortTest.pm2AndPm10.getBytes());
            System.out.println("pm2.5 and pm10 bytes :"+SerialPortTest.pm2AndPm10.getBytes());
            ctx.writeAndFlush(pm); // (3)
//            Thread.sleep(2000);



//        SerialPortTest serialPortTest = new SerialPortTest();
//        serialPortTest.run();
            try {
                Thread.sleep(1000);
                System.out.println(SerialPortTest.pm2AndPm10);
//                thread.interrupt();
//                System.exit(1);
//                thread.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            ctx.close();
        } // (4)
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ctx.writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) {
//        ctx.flush();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}

