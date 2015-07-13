package hu.enbritely.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.Map;
import java.util.Random;

public class NumberSpout implements IRichSpout {

    private SpoutOutputCollector ouc;
    private Random random;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declareStream("odd", new Fields("number"));
        outputFieldsDeclarer.declareStream("general", new Fields("number"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        ouc = spoutOutputCollector;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void nextTuple() {
        int number = random.nextInt(100);
        ouc.emit("general",new Values(number));
        if(number % 2 == 1) {
            ouc.emit("odd",new Values(number));
        }
    }

    @Override
    public void ack(Object o) {

    }

    @Override
    public void fail(Object o) {

    }
}
