package pm;

public class Test {
    public static void main(String[] args) {
        Thread thread=new Thread(new SerialPortTest());
        thread.run();
//        SerialPortTest serialPortTest = new SerialPortTest();
//        serialPortTest.run();
        try {
            Thread.sleep(1000);
            System.out.println(SerialPortTest.pm2AndPm10);
            thread.interrupt();
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        String t = serialPortTest.runAndReturn();
//        System.out.println(t);



    }
}
