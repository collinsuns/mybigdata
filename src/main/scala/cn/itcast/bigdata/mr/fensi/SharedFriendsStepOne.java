package cn.itcast.bigdata.mr.fensi;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Project Name: hello-spark
 * author: Suns
 * Date: 2017/10/18 15:01
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:
 */
public class SharedFriendsStepOne {

    static class SharedFriendsStepOneMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] person_friends = line.split(":");
            String person = person_friends[0];
            String friends = person_friends[1];

            for (String friend : friends.split(",")) {
                context.write(new Text(friend), new Text(person));
            }
        }
    }

    static class SharedFriendsStepOneReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text friend, Iterable<Text> persons, Context context) throws IOException, InterruptedException {
            StringBuffer sb = new StringBuffer();
            for (Text person : persons) {
                sb.append(person).append(",");
            }
            context.write(friend, new Text(sb.toString()));
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

        Job job = Job.getInstance(conf);
        job.setJarByClass(SharedFriendsStepOne.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(SharedFriendsStepOneMapper.class);
        job.setReducerClass(SharedFriendsStepOneReducer.class);

        FileInputFormat.setInputPaths(job, new Path("c:/test/friends"));
        FileOutputFormat.setOutputPath(job, new Path("c:/test/output/friendsstepone"));

        job.waitForCompletion(true);
    }

}