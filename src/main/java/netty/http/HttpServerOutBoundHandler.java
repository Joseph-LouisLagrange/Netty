package netty.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpServerOutBoundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      //  System.out.println("ctx:"+ctx.hashCode());
        if(msg instanceof DefaultFullHttpResponse) {
            DefaultFullHttpResponse defaultFullHttpResponse = (DefaultFullHttpResponse) msg;
           // defaultFullHttpResponse.headers().add(HttpHeaderNames.ACCEPT_CHARSET, CharsetUtil.UTF_8.name());
            //System.out.println(defaultFullHttpResponse.content().setCharSequence(1,"你是",CharsetUtil.UTF_8));
            super.write(ctx, defaultFullHttpResponse, promise);
        }
    }
}
