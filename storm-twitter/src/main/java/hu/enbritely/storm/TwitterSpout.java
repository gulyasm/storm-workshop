package hu.enbritely.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class TwitterSpout extends BaseRichSpout implements StatusListener {

    private SpoutOutputCollector soc;
    private LinkedBlockingQueue<Status> queue;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("tweet"));

    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.soc = spoutOutputCollector;
        queue = new LinkedBlockingQueue<>(1024 * 1024);
        ConfigurationBuilder builder = new ConfigurationBuilder();
        TwitterStream twitterStream = new TwitterStreamFactory(builder.build()).getInstance();
        twitterStream.setOAuthConsumer(
                "SdAaEy2NQxQsiVdqaoaNKhwNx",
                "rOMPFWLvlTXNPXRTAe9TJy1jEdupjd27TXZI2PGatAGSsrbSpN");
        AccessToken token = new AccessToken(
                "154070429-8PiYSmuniBiTuSeAl00YukhsBGzuu2DMb6LIpMvI",
                "DRXRaP0Jcaeruj8vbrwZJsny8EHO552YLZViyN1sfQYin");
        twitterStream.setOAuthAccessToken(token);
        twitterStream.addListener(this);
        twitterStream.sample();


    }

    @Override
    public void nextTuple() {
        final ArrayList<Status> tweets = new ArrayList<>();
        queue.drainTo(tweets);
        for (Status tweet : tweets) {
            soc.emit(new Values(tweet));
        }
    }

    @Override
    public void onException(Exception e) {

    }


    @Override
    public void onStatus(Status status) {
        queue.offer(status);
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {

    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {

    }
}
