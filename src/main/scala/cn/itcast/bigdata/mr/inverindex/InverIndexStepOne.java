package cn.itcast.bigdata.mr.inverindex;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Project Name: hello-spark
 * author: Suns
 * Date: 2017/10/18 16:01
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:
 */
public class InverIndexStepOne {

    static class InverIndexStepOneMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        Text k = new Text();
        IntWritable v = new IntWritable(1);

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] words = line.split(" ");
            FileSplit inputSplit = (FileSplit) context.getInputSplit();

            String fileName = inputSplit.getPath().getName();
            for (String word : words) {
                k.set(word + "--" + fileName);
                context.write(k, v);
            }

        }
    }

    static class InverIndexStepOneReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

            int count = 0;

            for (IntWritable value : values) {
                count += value.get();
            }

            context.write(key, new IntWritable(count));
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
        job.setJarByClass(InverIndexStepOne.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path("c:/test/inverindexinput"));
        FileOutputFormat.setOutputPath(job, new Path("c:/test/output/inverindex"));
        // FileInputFormat.setInputPaths(job, new Path(args[0]));
        // FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(InverIndexStepOneMapper.class);
        job.setReducerClass(InverIndexStepOneReducer.class);

        job.waitForCompletion(true);
    }

}