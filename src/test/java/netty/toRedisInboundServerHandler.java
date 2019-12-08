package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import redis.RedisClient;

import java.math.BigDecimal;
import java.util.Date;

@ChannelHandler.Sharable
public class toRedisInboundServerHandler extends ChannelInboundHandlerAdapter {
    //启动连接
    RedisClient client = new RedisClient("192.168.1.13",6379);

    String palce = new String("2");
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        double time = (double) System.currentTimeMillis();
        BigDecimal bd = new BigDecimal(Double.toString(time));
        //"集合"的名字
        String name = bd.toPlainString()+"##"+palce;


        try {
//            ByteBuf m = (ByteBuf) msg;
//            client.start(name,"pm2.5 "+Float.toString(m.readFloat()));
////            System.out.println(name+" ++ "+"pm2.5: "+Float.toString(m.readFloat()));
//            System.out.println("insert complete. ");

            ByteBuf m = (ByteBuf) msg;
            byte[] bytes = new byte[m.readableBytes()];
            m.readBytes(bytes);
            String tempInformation = new String(bytes);

            if("pm".equals(tempInformation.split("##")[0])){
                System.out.println("pm2.5: "+tempInformation.split("##")[1]
                        +" pm10: "+tempInformation.split("##")[2]);
            }

            if("th".equals(tempInformation.split("##")[0])){
                System.out.println("temperature "+tempInformation.split("##")[1]
                        +" hunmidity: "+tempInformation.split("##")[2]);
            }
//            client.start(name,"pm2.5 "+tempInformation.split("##")[0]
//                            +" pm10 "+tempInformation.split("##")[1]
//                            +" temperature "+tempInformation.split("##")[2]
//                            +" hunmidity "+tempInformation.split("##")[3]);
//            System.out.println(name+" ++ "+"pm2.5: "+tempInformation.split("##")[0]
//                                +" pm10: "+tempInformation.split("##")[1]
//                                +" temperature "+tempInformation.split("##")[2]
//                                +" hunmidity "+tempInformation.split("##")[3]);
//            System.out.println("insert complete. ");
        } catch (Exception e) {
            System.out.println("传送至redis失败.");
            e.printStackTrace();
        }finally {

        }

//        ByteBuf m = (ByteBuf) msg;
//        try {
//            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
//            System.out.println(new Date(currentTimeMillis));
//        } finally {
////            m.release();
//        }

//        ByteBuf inboundinf = ctx.alloc().buffer(4);
//        inboundinf.writeInt(1111);
//        ctx.writeAndFlush(inboundinf);
//        System.out.println("ServerInbound已经写入1111");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);



    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
