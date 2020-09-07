package netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class NettyClientHandler  extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        System.out.println("client :"+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Server:", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        ByteBuf byteBuf= (ByteBuf) msg;
        System.out.println(byteBuf);
        System.out.println("服务器回复信息："+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器远端地址："+ctx.channel().remoteAddress());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       // super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        //ctx.close();
    }
}
