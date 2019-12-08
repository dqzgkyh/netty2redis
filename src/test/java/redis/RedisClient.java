package redis;
/*
 * Copyright 2016 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License, version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */


import serialport.tempertatureAndHunmiditySerialPort;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import io.netty.util.concurrent.GenericFutureListener;

/**
 * Simple Redis client that demonstrates Redis commands against a Redis server.
 */
public class RedisClient {
    String host ;    //   目标主机
    int port ;       //   目标主机端口

    public RedisClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void start(String key,String value) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new RedisClientInitializer());

            Channel channel = bootstrap.connect(host, port).sync().channel();
            System.out.println(" connected to host : " + host + ", port : " + port);
//            System.out.println(" type redis's command to communicate with redis-server or type 'quit' to shutdown ");
//            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            ChannelFuture lastWriteFuture = null;

//                String s = in.readLine();
//                if(s.equalsIgnoreCase("quit")) {
//                    break;
//                }
            String s = "hmset "+key+" "+value;
            System.out.println(s);
//                System.out.print(">");
                lastWriteFuture = channel.writeAndFlush(s);
                lastWriteFuture.addListener(new GenericFutureListener<ChannelFuture>() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            System.err.print("write failed: ");
                            future.cause().printStackTrace(System.err);
                        }
                    }
                });

            if (lastWriteFuture != null) {
                lastWriteFuture.sync();

            }

        }finally {
            group.shutdownGracefully();
            System.out.println(" bye ");
        }
    }

    public static void main(String[] args) throws Exception{
        RedisClient client = new RedisClient("192.168.1.13",6379);
        tempertatureAndHunmiditySerialPort tempertatureAndHunmiditySerialPort = new tempertatureAndHunmiditySerialPort();

        client.start("inf01", tempertatureAndHunmiditySerialPort.runAndReturn());
//        client.start("inf01","xxx");
        Thread.sleep(1000);
        return;
    }
}

