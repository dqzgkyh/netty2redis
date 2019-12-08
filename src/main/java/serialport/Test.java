package serialport;

public class Test {
    public  static void main(String[] args) {
//        Thread thread=new Thread(new SerialPortTest());
//        thread.run();
        tempertatureAndHunmiditySerialPort tempertatureAndHunmiditySerialPort = new tempertatureAndHunmiditySerialPort();
        tempertatureAndHunmiditySerialPort.init();
        while(true){
            try {
                tempertatureAndHunmiditySerialPort.sendMsg();
                Thread.sleep(1000);
                System.out.println(serialport.tempertatureAndHunmiditySerialPort.temperatureAndHunmidity);
                System.out.println(tempertatureAndHunmiditySerialPort);
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
