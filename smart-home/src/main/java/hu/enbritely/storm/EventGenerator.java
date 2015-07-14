package hu.enbritely.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EventGenerator extends BaseRichSpout {

    private static final String[] DEVICES = new String[]{
            "thermostat",
            "fridge",
            "door"
    };

    private static final Map<String, String[]> EVENTS = new HashMap<>();

    static {
        EVENTS.put("fridge", new String[]{"empty", "filled", "warning", "error"});
        EVENTS.put("door", new String[]{"open", "closed"});
        EVENTS.put("thermostat", new String[]{"value-change"});
    }

    private SpoutOutputCollector soc;
    private Random random;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("value", "deviceId", "eventType"));
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector soc) {
        this.soc = soc;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void nextTuple() {
        final String deviceId = DEVICES[this.random.nextInt(DEVICES.length)];
        String[] events = EVENTS.get(deviceId);
        final String eventType = events[random.nextInt(events.length)];
        final float value = eventType.matches("value-change") ? random.nextFloat() : -1;
        soc.emit(new Values(value, deviceId, eventType));


    }
}
