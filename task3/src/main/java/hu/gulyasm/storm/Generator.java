package hu.gulyasm.storm;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;


public class Generator {

    private static final int LOCATION_MAX_ID = 8;
    private static final String[] DATA_TYPES = new String[]{"temp", "light", "alarm", "door"};
    private final Random random;
    private boolean currentDoorStatus;
    private boolean currentAlarmStatus;
    private HashMap<String, ArrayList<String>> sensorIDs;

    public Generator() {
        this.random = new Random();
        currentDoorStatus = false;
        sensorIDs = initializeSensorIDs();
    }

    private static float boolToFloat(boolean value) {
        return value ? 1f : 0f;

    }

    public static void main(String[] args) {
        Generator g = new Generator();
        for (int i = 0; i < 100000; i++) {
            SensorData data = g.generate();
            System.out.println(data);
        }
    }

    private HashMap<String, ArrayList<String>> initializeSensorIDs() {
        HashMap<String, ArrayList<String>> sensorIDs = new HashMap<>();
        for (String type : DATA_TYPES) {
            int length = getSensorCount(type);
            ArrayList<String> IDs = new ArrayList<>();
            sensorIDs.put(type, IDs);
            for (int i = 0; i < length; i++) {
                IDs.add(UUID.randomUUID().toString());
            }
        }
        return sensorIDs;
    }

    private int getSensorCount(String type) {
        switch (type) {
            case "temp":
                return 100;
            case "light":
                return 50;
            case "door":
                return 35;
            case "alarm":
                return 2;
            default:
                throw new IllegalArgumentException("Unknown sensor type: " + type);
        }
    }

    public SensorData generate() {
        SensorDataBuilder b = new SensorDataBuilder();
        b.setTimestamp(System.currentTimeMillis())
                .setId(UUID.randomUUID().toString())
                .setLocationCode(random.nextInt(LOCATION_MAX_ID + 1));
        final String type = generateType();
        final float value = generateValue(type);
        final String sensorID = generateSensorID(type);

        b.setType(type).setValue(value).setSensorID(sensorID);
        return b.create();
    }

    private String generateSensorID(String type) {
        ArrayList<String> IDs = sensorIDs.get(type);
        return IDs.get(random.nextInt(IDs.size()));
    }

    private String generateType() {
        return DATA_TYPES[random.nextInt(DATA_TYPES.length)];
    }

    private float generateValue(String type) {
        switch (type) {
            case "temp":
                return (float) (15 + random.nextGaussian() * 20);
            case "light":
                return (float) (10 + random.nextGaussian() * 5);
            case "door":
                currentDoorStatus = currentDoorStatus ^ true;
                return boolToFloat(currentDoorStatus);
            case "alarm":
                if (currentAlarmStatus && random.nextFloat() < 0.0001) {
                    return 2;
                }
                if (random.nextFloat() < 0.0005) {
                    return 3;
                }
                currentAlarmStatus = currentDoorStatus ^ true;
                return boolToFloat(currentAlarmStatus);
            default:
                throw new IllegalArgumentException("Unknown sensor type: " + type);
        }
    }
}
