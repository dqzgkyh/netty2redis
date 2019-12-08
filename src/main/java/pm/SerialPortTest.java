package pm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class SerialPortTest implements Runnable, SerialPortEventListener {
    public static String hex_pm2 = null;
    public static String hex_pm10 = null;
    public static String pm2AndPm10 = null;

    // 检测系统中可用的通讯端口类
    private CommPortIdentifier portId;
    // 枚举类型
    private Enumeration<CommPortIdentifier> portList;

    // RS232串口
    private SerialPort serialPort;

    // 输入输出流
    private InputStream inputStream;
    private OutputStream outputStream;

    // 保存串口返回信息
    private String test = "";

    // 单例创建
    private static SerialPortTest uniqueInstance = new SerialPortTest();

    // 初始化串口
    @SuppressWarnings("unchecked")
    public void init() {
        // 获取系统中所有的通讯端口
        portList = CommPortIdentifier.getPortIdentifiers();
        // 循环通讯端口
        while(portList.hasMoreElements()){
        portId = portList.nextElement();
        // 判断是否是串口
        if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            // 比较串口名称是否是"COM3"
            if ("COM5".equals(portId.getName())) {
                System.out.println("找到串口COM5");
                // 打开串口
                try {
                    // open:（应用程序名随意命名】，阻塞【时等待的毫秒数）
                    serialPort = (SerialPort) portId.open(Object.class.getSimpleName(), 1500);
                    System.out.println("获取到串口对象,COM5");
                    //实例化输入流
                    inputStream = serialPort.getInputStream();
                    // 设置串口监听
                    serialPort.addEventListener(this);
                    // 设置串口数据时间有效(可监听)
                    serialPort.notifyOnDataAvailable(true);
                    // 设置串口通讯参数
                    // 波停特率，数据校位，止位和验方式
                    // 波特率9600,无校验
                    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, //
                            SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                    System.out.println("初始化完成");
                } catch (PortInUseException e) {
                    e.printStackTrace();
                } catch (TooManyListenersException e) {
                    e.printStackTrace();
                } catch (UnsupportedCommOperationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        }
    }

    // 实现接口SerialPortEventListener中的方法 读取从串口中接收的数据
    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.BI: // 通讯中断
            case SerialPortEvent.OE: // 溢位错误
            case SerialPortEvent.FE: // 帧错误
            case SerialPortEvent.PE: // 奇偶校验错误
            case SerialPortEvent.CD: // 载波检测
            case SerialPortEvent.CTS: // 清除发送
            case SerialPortEvent.DSR: // 数据设备准备好
            case SerialPortEvent.RI: // 响铃侦测
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 输出缓冲区已清空
                break;
            case SerialPortEvent.DATA_AVAILABLE: // 有数据到达
                readComm();
                break;
            default:
                break;
        }
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    // 读取串口返回信息
    public String readComm() {
        byte[] readBuffer = new byte[25];

            try {

                inputStream = serialPort.getInputStream();
                // 从线路上读取数据流
                int len = 0;
                while ((len = inputStream.read(readBuffer)) != -1) {
//                System.out.println("实时反馈：" + new String(readBuffer, 0, len).trim() + new Date());
                    String result = bytesToHexString(readBuffer);
                    System.out.println("result:"+result);
                    //6-9位是温度  10-13位是湿度
//                    for (int i = 0; i < 18; i++) {
//                        System.out.print(result.charAt(i));
//                    }
                    hex_pm2 = result.substring(6,8)+result.substring(4,6);
                    //这里似乎是左开右闭
//                    hex_pm2 = result.substring(5,9);
                    System.out.println("pm2.5是: "+ hex_pm2);
                    System.out.println("pm2.5是: "+ (Integer.parseInt(hex_pm2,16) / 10.0));


                    hex_pm10 = result.substring(10,12)+result.substring(8,10);

//                    hex_pm10 = result.substring(10,14);
                    System.out.println("pm10是: "+ hex_pm10);
                    System.out.println("pm10是: "+ Integer.parseInt(hex_pm10,16) / 10.0);

//
                    pm2AndPm10 = String.valueOf((Integer.parseInt(hex_pm2,16) / 10.0))+"##"+
                            String.valueOf((Integer.parseInt(hex_pm10,16) / 10.0));

                    System.out.println();
                    break;
                }

                closeSerialPort();

            } catch (IOException e) {
                e.printStackTrace();
            }


//        try {
//            inputStream = serialPort.getInputStream();
//            // 从线路上读取数据流
//            int len = 0;
//            while ((len = inputStream.read(readBuffer)) != -1) {
//                System.out.println("实时反馈：" + new String(readBuffer, 0, len).trim() + new Date());
//                test += new String(readBuffer, 0, len).trim();
//                break;
//            }
//            System.out.println(test + " ");
//            //closeSerialPort();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return String.valueOf((Integer.parseInt(hex_pm2,16) / 10.0))+"##"+
                String.valueOf((Integer.parseInt(hex_pm10,16) / 10.0));
    }

    public void closeSerialPort() {
        System.out.println("端口已经关闭");
        uniqueInstance.serialPort.close();
    }

    //向串口发送数据
    public void sendMsg(){
//        String information = "020302000002C580\r";
        String information = "020302000002C580";
        //020302000002C580  000

        try {
            //实例化输出流
            outputStream = serialPort.getOutputStream();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {

//            outputStream.write(information.getBytes());
            System.out.println("输入："+ByteUtil.toByteArray(information));
            outputStream.write(ByteUtil.toByteArray(information));
            System.out.println("写入至传感器完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        init();

//        sendMsg();

    }

    public String getPm2AndPm10(){
        return pm2AndPm10;
    }

    public  String runAndReturn(){
        init();

//        try{
//            Thread.sleep(500);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
        sendMsg();

        return pm2AndPm10;
    }

}
