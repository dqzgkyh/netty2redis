package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class toRedisOutboundServerHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        super.read(ctx);
        System.out.println("ServerOutbound接收到信息");

        ByteBuf outboundinf = ctx.alloc().buffer(4);
        outboundinf.writeInt(9999);
        ctx.writeAndFlush(outboundinf);
        System.out.println("ServerOutbound已经写入9999");

    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        super.write(ctx, msg, promise);
//
//        ByteBuf m =(ByteBuf) msg;
//        System.out.println(m.toString());
    }
}
