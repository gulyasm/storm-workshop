package hu.gulyasm.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

import java.util.concurrent.TimeUnit;

public class Task2 {

    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("sensor-gen", new SensorGeneratorSpout());

        builder.setBolt("routing", new RoutingBolt()).shuffleGrouping("sensor-gen");

        builder.setBolt("filter-door", new FilterBolt("door")).shuffleGrouping("routing", RoutingBolt.STREAM_DOOR);
        builder.setBolt("filter-alarm", new FilterBolt("alarm")).shuffleGrouping("routing", RoutingBolt.STREAM_ALARM);

        builder.setBolt("printing-door", new PrintingBolt("DOOR> ")).shuffleGrouping("filter-door");
        builder.setBolt("printing-alarm", new PrintingBolt("ALARM> ")).shuffleGrouping("filter-alarm");


        LocalCluster cluster = new LocalCluster();
        Config conf = new Config();
        cluster.submitTopology("mytopology", conf, builder.createTopology());

        Utils.sleep(2 * 60 * 1000);

        cluster.killTopology("mytopology");
        cluster.shutdown();
    }
}
