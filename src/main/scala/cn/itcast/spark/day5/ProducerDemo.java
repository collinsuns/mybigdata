package cn.itcast.spark.day5;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Project Name: hello-spark
 * User: Suns
 * Date: 2018-02-02 14:53
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class ProducerDemo {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("zk.connect", "hd4:2181,hd2:2181,hd3:2181");
        properties.put("metadata.broker.list", "hd4:9092,hd2:9092,hd3:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");

        ProducerConfig producerConfig = new ProducerConfig(properties);
        Producer<String, String> producer = new Producer<>(producerConfig);
        for (int i = 1001; i <= 1100; i++) {
            producer.send(new KeyedMessage<String, String>("test", "it" + i));
        }
    }
}
