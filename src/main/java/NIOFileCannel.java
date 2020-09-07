

import java.io.*;
import java.nio.ByteBuffer;

public class NIOFileCannel {
    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer=ByteBuffer.allocate(512);
        FileInputStream fileInputStream=new FileInputStream("1.txt");
       // fileInputStream.getChannel().read(byteBuffer);
        FileOutputStream fileOutputStream=new FileOutputStream("3.txt");
        //byteBuffer.flip();
        fileOutputStream.getChannel().transferFrom(fileInputStream.getChannel(),0,10);
    }
}
