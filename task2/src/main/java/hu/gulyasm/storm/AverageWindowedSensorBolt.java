package hu.gulyasm.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.windowing.TupleWindow;

import java.util.HashMap;
import java.util.Map;

public class AverageWindowedSensorBolt extends BaseWindowedBolt {
    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(TupleWindow inputWindow) {

        HashMap<Integer, Integer> count = new HashMap<>();
        HashMap<Integer, Double> sum = new HashMap<>();


        for (Tuple input : inputWindow.getNew()) {
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
                sum.put(locationCode, currentSum + value);
            }

        }

        for (Map.Entry<Integer, Integer> entrySet : count.entrySet()) {
            Integer locationCode = entrySet.getKey();
            int currentCount = entrySet.getValue();
            double currentSum = sum.get(locationCode);

            collector.emit(new Values(locationCode, currentSum / currentCount));
        }


    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("locationCode", "average"));

    }
}
