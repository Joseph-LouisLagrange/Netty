import NIO.Client;
import NIO.Server;
import netty.http.HttpServer;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Text {
    public static final int port=8888;
    public static void main(String[] args) throws IOException {
        RR.ex(a -> System.out.println(9));
    }
}
interface EE{
    public void run2(int a);
}
class RR{
    public static void ex(EE ee){
        ee.run2(1);
    }
}
