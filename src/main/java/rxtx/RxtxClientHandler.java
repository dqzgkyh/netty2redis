package rxtx;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@SuppressWarnings("ALL")
public class RxtxClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String information = "020302000002C580";
        System.out.println(information);
        ctx.writeAndFlush(information.getBytes());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //noinspection AlibabaUndefineMagicConstant
        if ("OK".equals(msg)) {
            System.out.println("Serial port responded to AT");
        } else {
            System.out.println("Serial port responded with not-OK: " + msg);
        }
        ctx.close();
    }
}
