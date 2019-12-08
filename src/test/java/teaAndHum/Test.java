package teaAndHum;

public class Test {
    public static void main(String[] args) {
//        Thread thread=new Thread(new SerialPortTest());
//        thread.run();
        thSerialPort thSerialPort = new thSerialPort();
        thSerialPort.init();
        while(true){
            try {
                thSerialPort.sendMsg();
                Thread.sleep(1000);
                System.out.println(thSerialPort.temperatureAndHunmidity);
//            thread.interrupt();
//                System.exit(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("发生异常");
            }
        }

//        String t = serialPortTest.runAndReturn();
//        System.out.println(t);



    }
}
