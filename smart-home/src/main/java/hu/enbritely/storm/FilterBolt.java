package hu.enbritely.storm;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.UUID;

public class FilterBolt extends BaseBasicBolt {
    @Override
    public void execute(Tuple tuple, BasicOutputCollector oc) {
        String eventID = UUID.randomUUID().toString();
        switch (tuple.getStringByField("deviceId")) {
            case "fridge":
                String status = tuple.getStringByField("eventType");
                oc.emit("fridge", new Values(eventID, status));
                break;
            case "door":
                status = tuple.getStringByField("eventType");
                oc.emit("door", new Values(eventID, status));
                break;
            case "thermostat":
                break;
            default:
                throw new IllegalArgumentException("No way that happend");
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declareStream("fridge", new Fields("eventId", "status"));
        outputFieldsDeclarer.declareStream("door", new Fields("eventId", "status"));
        outputFieldsDeclarer.declareStream("thermostat", new Fields("eventId", "eventType","value"));
    }
}
