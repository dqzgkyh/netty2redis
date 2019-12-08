package pm;

public class Test {
    public static void main(String[] args) {
//        Thread thread=new Thread(new SerialPortTest());
//        thread.run();


        pmSerialPort pmSerialPort = new pmSerialPort();
        pmSerialPort.run();

        System.out.println("run get pm: "+ pmSerialPort.getPm2AndPm10());;
//        try {
//            Thread.sleep(1000);
//            System.out.println(SerialPortTest.pm2AndPm10);
//            thread.interrupt();
//            System.exit(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        String t = serialPortTest.runAndReturn();
//        System.out.println(t);
    }
}
