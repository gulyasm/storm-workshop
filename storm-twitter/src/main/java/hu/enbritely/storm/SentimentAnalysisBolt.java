package hu.enbritely.storm;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import java.util.Arrays;
import java.util.List;

public class SentimentAnalysisBolt extends BaseBasicBolt {

    private static final List<String> POSITIVE = Arrays.asList(
            "good",
            "nice",
            "beautiful"
    );

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String text = tuple.getStringByField("text");
        String[] words = text.split(" ");
        int positive = 0;
        for(String word: words) {
            if(word.trim().length() == 0){
                continue;
            }
            if(POSITIVE.contains(word.toLowerCase())){
                positive++;
            }
        }
        if(positive > 0) {
            System.out.println("Positive tweet: " + text);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
