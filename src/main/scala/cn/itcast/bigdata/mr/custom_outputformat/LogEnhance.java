package cn.itcast.bigdata.mr.custom_outputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Project Name: hello-spark
 * author: Suns
 * Date: 2017/10/23 15:17
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:
 */
public class LogEnhance {

    static class LogEnhanceMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

        Map<String, String> ruleMap = new HashMap<>();

        Text k = new Text();
        NullWritable v = NullWritable.get();

        // 从数据库中加载规则信息倒ruleMap中
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {

            try {
                DBLoader.dbLoader(ruleMap);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // 获取一个计数器用来记录不合法的日志行数, 组名, 计数器名称
            Counter counter = context.getCounter("malformed", "malformedline");
            String line = value.toString();
            String[] fields = StringUtils.split(line, "\t");
            try {
                String url = fields[26];
                String content_tag = ruleMap.get(url);
                // 判断内容标签是否为空，如果为空，则只输出url到待爬清单；如果有值，则输出到增强日志
                if (content_tag == null) {
                    k.set(url + "\t" + "tocrawl" + "\n");
                    context.write(k, v);
                } else {
                    k.set(line + "\t" + content_tag + "\n");
                    context.write(k, v);
                }

            } catch (Exception exception) {
                counter.increment(1);
            }
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

        job.setJarByClass(LogEnhance.class);

        job.setMapperClass(LogEnhanceMapper.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        // 要控制不同的内容写往不同的目标路径，可以采用自定义outputformat的方法
        job.setOutputFormatClass(LogEnhanceOutputFormat.class);

        FileInputFormat.setInputPaths(job, new Path("c:/test/webloginput/"));

        // 尽管我们用的是自定义outputformat，但是它是继承制fileoutputformat
        // 在fileoutputformat中，必须输出一个_success文件，所以在此还需要设置输出path
        FileOutputFormat.setOutputPath(job, new Path("c:/test/output/enhance"));

        // 不需要reducer
        job.setNumReduceTasks(0);

        job.waitForCompletion(true);
        System.exit(0);

    }

}