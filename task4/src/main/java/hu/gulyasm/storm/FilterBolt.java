package hu.gulyasm.storm;

import org.apache.storm.state.State;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.*;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class FilterBolt extends BaseRichBolt {

    private String type;
    private OutputCollector collector;

    public FilterBolt(String type) {
        this.type = type;
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        if(input.getStringByField("type").equals(this.type)) {
            Values values = new Values();
            values.addAll(input.getValues());
            collector.emit(values);
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("ID", "sensorID", "timestamp", "locationCode", "type", "value"));
    }
}
