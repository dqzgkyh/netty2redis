package TryTransString;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class ByteTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String example = "This is an example";
        byte[] bytes = example.getBytes();

        System.out.println("Text : " + example);
        System.out.println("Text [Byte Format] : " + bytes);
        System.out.println("Text [Byte Format] : " + bytes.toString());

        String s = new String(bytes,"UTF-8");
        System.out.println("Text Decryted : " + s);


    }
}
