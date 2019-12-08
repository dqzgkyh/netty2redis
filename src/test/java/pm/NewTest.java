package pm;

public class NewTest {
    public static void main(String[] args) {
        pmSerialPort pmSerialPort = new pmSerialPort();
        pmSerialPort.run();

        try {
            while(true){
                Thread.sleep(800);
                System.out.println("run get pm: "+ pmSerialPort.getPm2AndPm10());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
