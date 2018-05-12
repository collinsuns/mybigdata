package cn.itcast.bigdata.mr.custom_groupingcomparator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @ProjectName: hello-spark
 * @author: Suns
 * @Date: 2017/10/26 20:30
 * @Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * @Description:
 */
public class SecondarySort {

    static class SecondarySortMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {

        OrderBean bean = new OrderBean();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            String[] fields = StringUtils.split(line, "-");

            bean.set(new Text(fields[0]), new DoubleWritable(Double.parseDouble(fields[2])));

            context.write(bean, NullWritable.get());

        }

    }

    static class SecondarySortReducer extends Reducer<OrderBean, NullWritable, OrderBean, NullWritable> {


        //到达reduce时，相同id的所有bean已经被看成一组，且金额最大的那个一排在第一位
        @Override
        protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
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

        job.setJarByClass(SecondarySort.class);

        job.setMapperClass(SecondarySortMapper.class);
        job.setReducerClass(SecondarySortReducer.class);


        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path("c:/test/gpinput"));
        FileOutputFormat.setOutputPath(job, new Path("c:/test/output/gpoutput"));

        //在此设置自定义的Groupingcomparator类
        job.setGroupingComparatorClass(ItemidGroupingComparator.class);
        //在此设置自定义的partitioner类
        job.setPartitionerClass(ItemIdPartitioner.class);

        job.setNumReduceTasks(2);

        job.waitForCompletion(true);

    }

}