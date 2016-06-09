package hu.gulyasm.storm;

import org.apache.storm.state.State;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.*;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class AverageSensorBolt extends BaseRichBolt {

    private HashMap<Integer, Integer> count = new HashMap<>();
    private HashMap<Integer, Double> sum = new HashMap<>();


    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {

        final Integer locationCode = input.getIntegerByField("locationCode");
        final Float value = input.getFloatByField("value");

        int currentCount = 1;
        double currentSum = value;

        if (!count.containsKey(locationCode)) {
            count.put(locationCode, 1);
            sum.put(locationCode, value.doubleValue());
        } else {
            currentCount = count.get(locationCode);
            currentSum = sum.get(locationCode);
            count.put(locationCode, currentCount + 1);
            sum.put(locationCode,  currentSum + value);
        }

        if (currentCount % 10 == 0) {
            collector.emit(new Values(locationCode, currentSum / currentCount));
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("locationCode", "average"));

    }
}
