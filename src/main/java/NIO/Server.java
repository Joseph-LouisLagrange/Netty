package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class Server {

    public static void main(String[] args)throws IOException {
        Server server=new Server(8884);
        server.start();
    }
    int port;
    ServerSocketChannel serverSocketChannel=null;
    Selector selector=null;
    public Server(int port){
        this.port=port;
    }

    public void start() throws IOException {
        serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        selector=Selector.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            if(selector.select(1000)==0){
                continue;
            }
            Iterator<SelectionKey> selectionKeys=selector.selectedKeys().iterator();
            while (selectionKeys.hasNext()){
                SelectionKey selectionKey=selectionKeys.next();
                if (selectionKey.isAcceptable()){
                    SelectionKey selectionKey1=serverSocketChannel.accept().configureBlocking(false).register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(152));
                    System.out.println(((SocketChannel)selectionKey1.channel()).getRemoteAddress()+"上线");
                }
                if(selectionKey.isReadable()){
                    try {
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        socketChannel.read(byteBuffer);
                        zhuanFa(socketChannel, new String(byteBuffer.array()));
                        byteBuffer.clear();
                    }catch (IOException e){
                        //System.out.println(((SocketChannel)selectionKey.channel()).getRemoteAddress()+"下线");
                        selectionKey.cancel();
                    }
                    //System.out.println(selectionKey.hashCode()+"客户端："+new String(byteBuffer.array()));
                }
                if(!selectionKey.isValid()){
                    System.out.println(((SocketChannel)selectionKey.channel()).getRemoteAddress()+"下线");
                }
            }
            selectionKeys.remove();
        }
    }

    public void zhuanFa(SocketChannel self , String message) throws IOException {
        Iterator<SelectionKey> selectionKeyIterator=selector.keys().iterator();
        while (selectionKeyIterator.hasNext()){
            SelectableChannel selectableChannel=selectionKeyIterator.next().channel();
            if(selectableChannel instanceof SocketChannel && selectableChannel!=self){
                ((SocketChannel) selectableChannel).write(ByteBuffer.wrap(message.getBytes()));
            }
        }
    }
}
