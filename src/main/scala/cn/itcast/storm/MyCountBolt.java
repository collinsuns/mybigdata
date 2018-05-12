package cn.itcast.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import ch.qos.logback.core.net.SyslogOutputStream;

import java.util.HashMap;
import java.util.Map;

/**
 * Project Name: hello-spark
 * User: Suns
 * Date: 2018-02-23 15:58
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class MyCountBolt extends BaseRichBolt {

    OutputCollector collector;

    Map<String, Integer> map = new HashMap<String, Integer>();

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {

//        String word = input.getString(0);
        String word = input.getStringByField("word");

//        Integer num = input.getInteger(1);
        Integer num = input.getIntegerByField("num");

        if (map.containsKey(word)) {
            Integer count = map.get(word);
            map.put(word, count + num);
        } else {
            map.put(word, num);
        }

        System.out.println(map);

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
