import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.IntBuffer;
import java.util.Scanner;

public class SocketText {
    public static final int port=8036;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(port);
        while (true){
            final Socket socket=serverSocket.accept();
            System.out.println("接收socket："+socket);
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    byte[] bytes=new byte[1024];
                    try {
                        while (true) {
                            InputStream inputStream = socket.getInputStream();
                            while (true) {
                                int length = inputStream.read(bytes);
                                System.out.println("length:" + length);
                                System.out.print(new String(bytes, 0, length));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}

class Client{
    public Client(int port) {
    }

    public static void main(String[] args) throws IOException {
        Socket socket=new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",8036));

        Scanner input=new Scanner(System.in);
        while (true){
            String s=input.nextLine();
            socket.getOutputStream().write(s.getBytes());
        }
    }
}
