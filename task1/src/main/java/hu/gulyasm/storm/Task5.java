package hu.gulyasm.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

import java.util.concurrent.TimeUnit;

public class Task5 {

    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("sensor-gen", new SensorGeneratorSpout());
        builder.setBolt("filter", new FilterBolt("temp")).shuffleGrouping("sensor-gen");
        builder.setBolt("printing", new PrintingBolt("SENSOR> ")).shuffleGrouping("filter");


        LocalCluster cluster = new LocalCluster();
        Config conf = new Config();
        cluster.submitTopology("mytopology", conf, builder.createTopology());

        Utils.sleep(2 * 60 * 1000);

        cluster.killTopology("mytopology");
        cluster.shutdown();
    }
}