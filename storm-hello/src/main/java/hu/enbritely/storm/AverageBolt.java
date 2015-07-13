package hu.enbritely.storm;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class AverageBolt extends BaseBasicBolt {

    private static final long THRESHOLD = 5 * 1000; // 5 seconds
    private int sum = 0;
    private int count = 0;
    private long lastPrint = 0l;

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        int number = tuple.getInteger(0).intValue();
        count++;
        sum += number;
        final long now = System.currentTimeMillis();
        if(now - lastPrint > THRESHOLD) {
            System.out.println("Numbers: " + Integer.toString(count));
            System.out.println((float)sum / count);
            lastPrint = now;
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
