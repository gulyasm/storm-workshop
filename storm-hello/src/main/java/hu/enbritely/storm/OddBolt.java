package hu.enbritely.storm;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class OddBolt extends BaseBasicBolt {
    private static final long THRESHOLD = 10 * 1000; // 10 seconds
    private long lastPrint = 0l;

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        final int number = tuple.getIntegerByField("number");
        long now = System.currentTimeMillis();
        if(now - lastPrint > THRESHOLD) {
            System.out.println("This is odd: " + Integer.toString(number));
            lastPrint = now;
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }



}
