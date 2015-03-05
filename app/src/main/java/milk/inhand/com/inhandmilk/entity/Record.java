package milk.inhand.com.inhandmilk.entity;

/**
 * Record
 * Desc: 某次的记录
 * Team: InHand
 * User: Wooxxx
 * Date: 2015-03-04
 * Time: 20:22
 */
//@AVClassName(Record.RECORD_CLASS)
//public class Record extends AVObject{
//    static final String RECORD_CLASS = "Record";
//    private static final String VOLUME_KEY = "volume";
//    private static final String MIN_TEMPERATURE_KEY = "min_temperature";
//    private static final String MAX_TEMPERATURE_KEY = "max_temperature";
//    private static final String DURATION_KEY = "duration";
//
//    //奶量
//    private int volume;
//    //温度(最低温度，最高温度)
//    private double minTemperature;
//    private double maxTemperature;
//    //喝奶时长
//    private int duration;
//
//    public Record(){
//
//    }
//
//    public int getVolume() {
//        return this.getInt(VOLUME_KEY);
//    }
//
//    public void setVolume(int volume) {
//        this.put(VOLUME_KEY,volume);
//    }
//
//    public double getMinTemperature() {
//        return this.getDouble(MIN_TEMPERATURE_KEY);
//    }
//
//    public void setMinTemperature(double minTemperature) {
//        this.put(MIN_TEMPERATURE_KEY,minTemperature);
//    }
//
//    public double getMaxTemperature() {
//        return this.getDouble(MAX_TEMPERATURE_KEY);
//    }
//
//    public void setMaxTemperature(double maxTemperature) {
//        this.put(MAX_TEMPERATURE_KEY,maxTemperature);
//    }
//
//    public int getDuration(){
//        return this.getInt(DURATION_KEY);
//    }
//
//    public void setDuration(int duration){
//        this.put(DURATION_KEY,duration);
//    }
//}

public class Record{
    //奶量
    private int volume;
    //温度(最低温度，最高温度)
    private double minTemperature;
    private double maxTemperature;
    //喝奶时长
    private int duration;

    public Record(){

    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
