package hu.enbritely.storm;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class DoorBolt extends BaseBasicBolt {
    private String previousState = "closed";
    private long opened = 0;

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        switch (tuple.getStringByField("status")) {
            case "open":
                if(previousState.matches("closed")){
                    System.out.println("The door is now open!");
                    previousState = "opened";
                    opened = System.currentTimeMillis();
                }
                break;
            case "closed":
                previousState = "closed";
                if(opened != 0){
                    final long now = System.currentTimeMillis();
                    final long openedDuration = now - opened;
                    System.out.println("The door was opened for " + Long.toString(openedDuration) + "ms.");
                }

                break;
            default:
                throw new IllegalArgumentException("Invalid door status");
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
