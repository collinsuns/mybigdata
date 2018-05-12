package cn.itcast.bigdata.mr.flowsum;

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

public class FlowCount {

    static class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();

            String[] fields = line.split("\t");
            String phoneNbr = fields[1];

            long upFlow = Long.parseLong(fields[fields.length - 3]);
            long dFlow = Long.parseLong(fields[fields.length - 2]);

            context.write(new Text(phoneNbr), new FlowBean(upFlow, dFlow));

        }
    }

    static class FlowCountReduce extends Reducer<Text, FlowBean, Text, FlowBean> {
        @Override
        protected void reduce(Text key, Iterable<FlowBean> values, Context context)
                throws IOException, InterruptedException {
            // TODO Auto-generated method stub
            long sum_upFlow = 0;
            long sum_dFlow = 0;

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
//		conf.set("mapreduce.framework.name", "local");

        //本地模式运行mr程序时，输入输出的数据可以在本地，也可以在hdfs上
        //到底在哪里，就看以下两行配置你用哪行，默认就是file:///
		/*conf.set("fs.defaultFS", "hdfs://hd1:9000/");*/
//		conf.set("fs.defaultFS", "file:///");



        //运行集群模式，就是把程序提交到yarn中去运行
        //要想运行为集群模式，以下3个参数要指定为集群上的值
		conf.set("mapreduce.framework.name", "yarn");
		conf.set("yarn.resourcemanager.hostname", "hd1");
		conf.set("fs.defaultFS", "hdfs://hd1:9000/");

        //指定本程序的jar包所在的本地路径
		/*job.setJarByClass(WordcountDriver.class);*/

        Job job = Job.getInstance(conf);

        job.setJarByClass(FlowCount.class);

        job.setMapperClass(FlowCountMapper.class);
        job.setReducerClass(FlowCountReduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }
}
