package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import teaAndHum.thSerialPort;

public class thClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        thSerialPort thSerialPort = new thSerialPort();
        thSerialPort.init();

        while(true){
            try {
                //对于温湿度的处理,查询
                thSerialPort.sendMsg();
                Thread.sleep(1000);
                String tempTH = "th##"+ thSerialPort.temperatureAndHunmidity;
                System.out.println("温湿度分别是: "+tempTH);

                ByteBuf pm2 = ctx.alloc().buffer(8); //为ByteBuf分配四个字节
                pm2.writeBytes(tempTH.getBytes("UTF-8"));
                ctx.writeAndFlush(pm2); // (3)
                System.out.println("温湿度传输已完成");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);

        cause.printStackTrace();
    }
}
