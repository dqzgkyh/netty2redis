package TryTransString;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;



public class ByteTest {
    static String example = "This is an example";
    static byte[] bytes = example.getBytes();

    public static void print1(){
        Integer int1 = Integer.parseInt("3");
        System.out.println("Text : " + example);
        System.out.println("Text [Byte Format] : " + bytes);
        System.out.println("Text [Byte Format] : " + bytes.toString());
    }

    public static void print2() throws UnsupportedEncodingException {
        String s = new String(bytes,"UTF-8");
        System.out.println("Text Decryted : " + s);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        print1();

        print2();

    }
}
