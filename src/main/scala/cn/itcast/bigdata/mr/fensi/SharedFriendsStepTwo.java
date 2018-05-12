package cn.itcast.bigdata.mr.fensi;

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
import java.util.Arrays;

/**
 * Project Name: hello-spark
 * author: Suns
 * Date: 2017/10/18 15:38
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:
 */
public class SharedFriendsStepTwo {
    static class SharedFriendsStepTwoMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] friend_persons = line.split("\t");

            String friend = friend_persons[0];
            String[] persons = friend_persons[1].split(",");

            Arrays.sort(persons);

            for (int i = 0; i < persons.length; i++) {
                for (int j = i + 1; j < persons.length; j++) {
                    context.write(new Text(persons[i] + "-" + persons[j]), new Text(friend));
                }
            }

        }
    }


    static class SharedFriendsStepTwoReducer extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void reduce(Text person_person, Iterable<Text> friends, Context context) throws IOException, InterruptedException {

            StringBuffer sb = new StringBuffer();

            for (Text friend : friends) {
                sb.append(friend).append(" ");

            }
            context.write(person_person, new Text(sb.toString()));
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
        job.setJarByClass(SharedFriendsStepTwo.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(SharedFriendsStepTwoMapper.class);
        job.setReducerClass(SharedFriendsStepTwoReducer.class);

        FileInputFormat.setInputPaths(job, new Path("c:/test/output/friendsstepone/part-r-00000"));
        FileOutputFormat.setOutputPath(job, new Path("c:/test/output/friendssteptwo/"));

        job.waitForCompletion(true);

    }
}