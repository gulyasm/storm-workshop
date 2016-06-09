package hu.gulyasm.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.UUID;

public class DroppingBolt extends BaseRichBolt {

    private int counter;
    private int dropRate;
    private OutputCollector collector;

    public DroppingBolt(int dropRate) {
        this.counter = 0;
        this.dropRate = dropRate;
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        if (++counter == this.dropRate) {
            counter = 0;
        } else {
            Values values = new Values();
            values.addAll(input.getValues());
            collector.emit(input, values);
            collector.ack(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("ID", "sensorID", "timestamp", "locationCode", "type", "value"));
    }
}
