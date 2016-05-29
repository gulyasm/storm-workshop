package hu.gulyasm.storm;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;

public class SensorGeneratorSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private Generator generator;

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        this.generator = new Generator();
    }

    public void nextTuple() {
        Utils.sleep(50);
        SensorData data = generator.generate();
        collector.emit(new Values(data.ID, data.sensorID, data.timestamp, data.locationCode, data.type, data.value));
    }



    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("ID", "sensorID", "timestamp", "locationCode", "type", "value"));
    }
}
