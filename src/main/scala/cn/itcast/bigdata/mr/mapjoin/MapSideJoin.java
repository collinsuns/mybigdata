package cn.itcast.bigdata.mr.mapjoin;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Project Name: hello-spark
 * author: Suns
 * Date: 2017/10/18 9:53
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:
 */
public class MapSideJoin {

    public static class MapsideJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        Map<String, String> pdInfoMap = new HashMap<>();
        Text k = new Text();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("pdts.txt")));

            String line;

            while (StringUtils.isNotEmpty(line = br.readLine())) {
                String[] fields = line.split(",");
                pdInfoMap.put(fields[0], fields[1]);
            }
            br.close();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String orderLine = value.toString();
            String[] fields = orderLine.split("\t");
            String pdName = pdInfoMap.get(fields[1]);
            k.set(orderLine + "\t" + pdName);
            context.write(k, NullWritable.get());
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

        conf.set("mapred.textoutputformat.separator", "\t");

        Job job = Job.getInstance(conf);

        job.setJarByClass(MapSideJoin.class);

        job.setMapperClass(MapsideJoinMapper.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path("c:/test/join/mapjoininput"));
        FileOutputFormat.setOutputPath(job, new Path("c:/test/output/mapjoin"));

        // 指定需要缓存一个文件到所有的maptask运行节点工作目录
		/* job.addArchiveToClassPath(archive); */// 缓存jar包到task运行节点的classpath中
		/* job.addFileToClassPath(file); */// 缓存普通文件到task运行节点的classpath中
		/* job.addCacheArchive(uri); */// 缓存压缩包文件到task运行节点的工作目录
		/* job.addCacheFile(uri) */// 缓存普通文件到task运行节点的工作目录

        // 将产品表文件缓存到task工作节点的工作目录中去
        job.addCacheFile(new URI("file:/c:/test/join/mapjoincache/pdts.txt"));

        //map端join的逻辑不需要reduce阶段，设置reducetask数量为0
        job.setNumReduceTasks(0);

        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);

    }

}