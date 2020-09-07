package netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpServerHandler extends  SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("HttpServerHandler ctx:"+ctx.hashCode());
        if(!(msg instanceof HttpRequest)) return;
        System.out.println("发生连接 :msg"+msg.getClass()+" 客户端地址"+ctx.channel().remoteAddress());
        String s="1你好9";
        ByteBuf buf= Unpooled.copiedBuffer(s, CharsetUtil.UTF_8);
        HttpResponse httpResponse=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,buf);
        httpResponse.headers().add(HttpHeaderNames.CONTENT_TYPE,HttpHeaderValues.TEXT_PLAIN+";charset=utf-8");
        httpResponse.headers().add(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());
        ctx.writeAndFlush(httpResponse);
    }
}
