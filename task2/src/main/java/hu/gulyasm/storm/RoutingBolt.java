package hu.gulyasm.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class RoutingBolt extends BaseRichBolt {

    public static final String STREAM_DEFAULT = "default";
    public static final String STREAM_DOOR = "door-stream";
    public static final String STREAM_ALARM = "alarm-stream";

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String type = input.getStringByField("type");
        Values values = new Values();
        values.addAll(input.getValues());
        switch (type) {
            case "door":
                collector.emit(STREAM_DOOR, values);
                break;
            case "alarm":
                collector.emit(STREAM_ALARM, values);
                break;
            default:
                collector.emit(STREAM_DEFAULT, values);
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream(STREAM_ALARM, new Fields("ID", "sensorID", "timestamp", "locationCode", "type", "value"));
        declarer.declareStream(STREAM_DOOR, new Fields("ID", "sensorID", "timestamp", "locationCode", "type", "value"));
        declarer.declareStream(STREAM_DEFAULT, new Fields("ID", "sensorID", "timestamp", "locationCode", "type", "value"));
    }
}
