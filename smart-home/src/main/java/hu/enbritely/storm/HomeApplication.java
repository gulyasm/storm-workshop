package hu.enbritely.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

public class HomeApplication {
    public static void main(String[] main) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("event-generator", new EventGenerator());
        builder.setBolt("filter-bolt", new FilterBolt()).shuffleGrouping("event-generator");
        builder.setBolt("door-bolt", new DoorBolt()).shuffleGrouping("filter-bolt", "door");

        LocalCluster cluster = new LocalCluster();
        Config conf = new Config();
        cluster.submitTopology("smarthome-topology", conf, builder.createTopology());

    }
}
