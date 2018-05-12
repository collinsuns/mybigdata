package cn.itcast.bigdata.mr.provinceflow;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Project Name: hello-spark
 * author: Suns
 * Date: 2017/10/7 20:15
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:
 */
public class FlowCount {

    static class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();    //将一行内容转成string
            String[] fields = line.split("\t");    //切分字段
            String phoneNbr = fields[1];    //取出手机号

            long upFlow = Long.parseLong(fields[fields.length - 3]);    //取出上行流量下行流量
            long dFlow = Long.parseLong(fields[fields.length - 2]);

            context.write(new Text(phoneNbr), new FlowBean(upFlow, dFlow));
        }
    }

    static class FlowCountReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
        @Override
        protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
            long sum_upFlow = 0;
            long sum_dFlow = 0;

            //遍历所有bean，将其中的上行流量，下行流量分别累加
            for (FlowBean bean : values) {
                sum_upFlow += bean.getUpFlow();
                sum_dFlow += bean.getdFlow();
            }

            FlowBean resultBean = new FlowBean(sum_upFlow, sum_dFlow);
            context.write(key, resultBean);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        //是否运行为本地模式，就是看这个参数值是否为local，默认就是local
        conf.set("mapreduce.framework.name", "local");

        //本地模式运行mr程序时，输入输出的数据可以在本地，也可以在hdfs上
        //到底在哪里，就看以下两行配置你用哪行，默认就是file:///
        /*conf.set("fs.defaultFS", "hdfs://mini1:9000/");*/
        conf.set("fs.defaultFS", "file:///");


        //运行集群模式，就是把程序提交到yarn中去运行
        //要想运行为集群模式，以下3个参数要指定为集群上的值
//        conf.set("mapreduce.framework.name", "yarn");
//        conf.set("yarn.resourcemanager.hostname", "hd1");
//        conf.set("fs.defaultFS", "hdfs://hd1:9000/");
//        conf.set("mapred.jar", "c:\\hello-spark.jar");

        Job job = Job.getInstance(conf);


		/*job.setJar("/home/hadoop/wc.jar");*/
        //指定本程序的jar包所在的本地路径
        job.setJarByClass(FlowCount.class);

        //指定本业务job要使用的mapper/Reducer业务类
        job.setMapperClass(FlowCountMapper.class);
        job.setReducerClass(FlowCountReducer.class);

        //指定我们自定义的数据分区器
        job.setPartitionerClass(ProvincePartitioner.class);
        //同时指定相应“分区”数量的reducetask
        job.setNumReduceTasks(5);

        //指定mapper输出数据的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        //指定最终输出的数据的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //指定job的输入原始文件所在目录
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        //指定job的输出结果所在目录
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行
		/*job.submit();*/
        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);


    }
}