package hu.gulyasm.storm;

public class SensorDataBuilder {
    private String id;
    private String sensorID;
    private String type;
    private long timestamp;
    private float value;
    private int locationCode;

    public SensorDataBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public SensorDataBuilder setSensorID(String sensorID) {
        this.sensorID = sensorID;
        return this;
    }

    public SensorDataBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public SensorDataBuilder setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public SensorDataBuilder setValue(float value) {
        this.value = value;
        return this;
    }

    public SensorDataBuilder setLocationCode(int locationCode) {
        this.locationCode = locationCode;
        return this;
    }

    public SensorData create() {
        return new SensorData(id, sensorID, type, timestamp, value, locationCode);
    }
}