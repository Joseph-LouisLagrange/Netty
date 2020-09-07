package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {

    public static void main(String[] args)throws IOException {
        Client client=new Client(8884);
        client.start();
    }
    int port;
    public Client(int port){
        this.port=port;
    }

    public void start() throws IOException {
        SocketAddress socketAddress=new InetSocketAddress("127.0.0.1",port);
        SocketChannel socketChannel=SocketChannel.open();
        if(!socketChannel.connect(socketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("做其他的事情");
            }
        }
        socketChannel.configureBlocking(true);
        new Thread(){
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(300);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        try {
                            byteBuffer.clear();
                            socketChannel.read(byteBuffer);
                            if(byteBuffer.limit()>0) {
                                byteBuffer.flip();
                                System.out.println(byteBuffer.toString());
                                System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Scanner scanner=new Scanner(System.in);
        while (true) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(scanner.nextLine().getBytes());
            socketChannel.write(byteBuffer);
        }
    }
}
