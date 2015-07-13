package hu.enbritely.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;

public class StormHello {

    public static void main(String[] args){
        TopologyBuilder builder = new TopologyBuilder();
        // Build topology

        builder.setSpout("number-spout", new NumberSpout());
        builder.setBolt("odd-bolt", new OddBolt()).shuffleGrouping("number-spout","odd");
        builder.setBolt("average-bolt", new AverageBolt()).shuffleGrouping("number-spout", "general");

        // Create topology
        StormTopology topology = builder.createTopology();
        LocalCluster cluster = new LocalCluster();
        Config config = new Config();
        cluster.submitTopology("my-topology", config, topology);
    }
}
