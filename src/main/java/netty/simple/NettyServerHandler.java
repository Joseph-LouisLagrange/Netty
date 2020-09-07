package netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class NettyServerHandler  extends ChannelInboundHandlerAdapter {


    public void bigTask(){
        try{
            System.out.println("开始执行大任务的。。。");
        //Thread.sleep(3000);
        System.out.println("大任务的作业执行完毕=---");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        System.out.println("server -> "+ctx);
        ByteBuf byteBuf= (ByteBuf) msg;
        ctx.channel().eventLoop().schedule(this::bigTask,1, TimeUnit.SECONDS);
        Stream.of("1","2","3").map(String::length).forEach(System.out::println);
        System.out.println("客户端发送信息为 ： -》"+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：——》"+ctx.channel().remoteAddress());
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //super.channelReadComplete(ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Client", CharsetUtil.UTF_8));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }
}
