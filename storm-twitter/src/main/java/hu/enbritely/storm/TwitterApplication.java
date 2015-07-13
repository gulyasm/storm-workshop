package hu.enbritely.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

public class TwitterApplication {

    public static void main(String[] args) {
        TopologyBuilder builder= new TopologyBuilder();
        builder.setSpout("twitter-source", new TwitterSpout());
        builder.setBolt("text-extractor", new TextExtractor()).shuffleGrouping("twitter-source");
        builder.setBolt("attitude-analysis", new SentimentAnalysisBolt()).shuffleGrouping("text-extractor");

        Config config = new Config();
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("twitter-topology", config, builder.createTopology());

    }
}
