package netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

public class HttpServer {
    static EventLoopGroup  bossLoopGroup=new NioEventLoopGroup(1);
    static EventLoopGroup  workLoopGroup=new NioEventLoopGroup();
    static ServerBootstrap serverBootstrap=new ServerBootstrap();
    public static void command(){
        System.out.println("执行指令");
    }
    public static void main(String[] args) throws InterruptedException {
        serverBootstrap.group(bossLoopGroup,workLoopGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ChannelInitializer<SocketChannel>(){
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new HttpServerCodec(),new HttpServerHandler());
                    }
                });
        System.out.println(bossLoopGroup==bossLoopGroup.next().parent());
       // System.out.println("服务器已开启");
        ChannelFuture channelFuture=serverBootstrap.bind(8090).sync();
        channelFuture.channel().closeFuture().sync();
    }
}
