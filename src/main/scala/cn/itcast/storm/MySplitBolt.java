package cn.itcast.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * Project Name: hello-spark
 * User: Suns
 * Date: 2018-02-23 15:46
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class MySplitBolt extends BaseRichBolt {
    OutputCollector collector;
    //初始化方法
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    // 被storm框架 while(true) 循环调用  传入参数tuple
    @Override
    public void execute(Tuple input) {

        String line = input.getString(0);

//        String love = input.getStringByField("love");
        String[] arrWords = line.split(" ");
        for (String word : arrWords) {
            collector.emit(new Values(word, 1));
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word", "num"));
    }
}
