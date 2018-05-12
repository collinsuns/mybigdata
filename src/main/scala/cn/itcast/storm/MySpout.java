package cn.itcast.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * Project Name: hello-spark
 * User: Suns
 * Date: 2018-02-23 15:43
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class MySpout extends BaseRichSpout {
    SpoutOutputCollector collector;

    //初始化方法
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    //storm 框架在 while(true) 调用nextTuple方法
    public void nextTuple() {
        collector.emit(new Values("i am lilei love hanmeimei"));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("love"));
    }
}
