package marker;

public class marker {
//1是异常
    public String mark(String pm2,String temperature,String humidity){
        String pmAbnormal = "0";
        String temperatureAbnormal = "0";
        String humidityAbnormal = "0";
        //pm是否异常,异常的话改为1
        if(Long.parseLong(pm2)<0 || Long.parseLong(pm2)>=250){
            pmAbnormal = "1";
        }
        //温度的异常
        if(Long.parseLong(temperature)<-10 || Long.parseLong(temperature)>=45){
                temperatureAbnormal = "1";
        }
        //湿度的异常
        if(Long.parseLong(humidity)<40 || Long.parseLong(humidity)>=65){
            humidityAbnormal = "1";
        }
    return pmAbnormal+temperatureAbnormal+humidityAbnormal;

    }
}
