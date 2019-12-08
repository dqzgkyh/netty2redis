package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import pm.pmSerialPort;

public class pmClienHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        tempertatureAndHunmiditySerialPort tempertatureAndHunmiditySerialPort = new tempertatureAndHunmiditySerialPort();
//        tempertatureAndHunmiditySerialPort.init();
//        tempertatureAndHunmiditySerialPort.sendMsg();
        pmSerialPort pmSerialPort = new pmSerialPort();
        pmSerialPort.run();
        while (true) {
//            ByteBuf time = ctx.alloc().buffer(10); //为ByteBuf分配四个字节
//            time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//            ctx.writeAndFlush(time); // (3)

            try {
//                Thread.sleep(1000);
//                String tempPM2 = serialPort.getPm2AndPm10().split("##")[0];
//                System.out.println(" handler pm2.5 : "+tempPM2);
//                ByteBuf pm2 = ctx.alloc().buffer(8); //为ByteBuf分配四个字节
//                pm2.writeFloat(Float.parseFloat(tempPM2));
//                ctx.writeAndFlush(pm2); // (3)
//                System.out.println("传输已完成");

                Thread.sleep(1000);
                //对于pm的处理,主动上报
                String tempPM = "pm##"+ pmSerialPort.getPm2AndPm10();
                System.out.println("handler pm2.5 and pm10 :"+tempPM);

                ByteBuf pm2 = ctx.alloc().buffer(8); //为ByteBuf分配四个字节
                pm2.writeBytes(tempPM.getBytes("UTF-8"));
                ctx.writeAndFlush(pm2); // (3)
                System.out.println("传输已完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        ByteBuf m =(ByteBuf) msg;
        System.out.println("已经接受的信息:"+ m.readUnsignedInt());
        m.release();
        ctx.close();
    }
}
