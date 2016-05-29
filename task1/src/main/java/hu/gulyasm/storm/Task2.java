package hu.gulyasm.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

public class Task2 {

    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("sensor-gen", new SensorGeneratorSpout());

        builder.setBolt("filter-door", new FilterBolt("door")).shuffleGrouping("sensor-gen", "door");
        builder.setBolt("printing-door", new PrintingBolt("DOOR> ")).shuffleGrouping("filter-door");

        builder.setBolt("filter-alarm", new FilterBolt("alarm")).shuffleGrouping("sensor-gen", "alarm");
        builder.setBolt("printing-alarm", new PrintingBolt("ALARM> ")).shuffleGrouping("filter-alarm");

        LocalCluster cluster = new LocalCluster();
        Config conf = new Config();
        cluster.submitTopology("mytopology", conf, builder.createTopology());

        Utils.sleep(2 * 60 * 1000);

        cluster.killTopology("mytopology");
        cluster.shutdown();
    }
}
