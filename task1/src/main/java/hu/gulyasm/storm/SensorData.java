package hu.gulyasm.storm;

public class SensorData {

    public final String ID;
    public final String sensorID;
    public final String type;
    public final long timestamp;
    public final float value;
    public final int locationCode;


    public SensorData(String id, String sensorID, String type, long timestamp, float value, int locationCode) {
        ID = id;
        this.sensorID = sensorID;
        this.type = type;
        this.timestamp = timestamp;
        this.value = value;
        this.locationCode = locationCode;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "ID='" + ID + '\'' +
                ", sensorID='" + sensorID + '\'' +
                ", type='" + type + '\'' +
                ", timestamp=" + timestamp +
                ", value=" + value +
                ", locationCode=" + locationCode +
                '}';
    }
}
