package cn.itcast.bigdata.mr.provinceflow;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

/**
 * Project Name: hello-spark
 * author: Suns
 * Date: 2017/10/7 20:14
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description: 定义自己的从map到reduce之间的数据（分组）分发规则 按照手机号所属的省份来分发（分组）ProvincePartitioner
 * 默认的分组组件是HashPartitioner

 */
public class ProvincePartitioner extends Partitioner<Text, FlowBean> {
    public static HashMap<String, Integer> proviceDict = new HashMap<String, Integer>();

    static {
        proviceDict.put("136", 0);
        proviceDict.put("137", 1);
        proviceDict.put("138", 2);
        proviceDict.put("139", 3);
    }


    @Override
    public int getPartition(Text key, FlowBean value, int numPartitions) {
        String prefix = key.toString().substring(0, 3);
        Integer provinceId = proviceDict.get(prefix);

        return provinceId == null ? 4 : provinceId;
    }
}