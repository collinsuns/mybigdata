//package cn.itcast.bigdata.mr.custom_inputformat;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.conf.Configured;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.BytesWritable;
//import org.apache.hadoop.io.NullWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapreduce.InputSplit;
//import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.Mapper;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapreduce.lib.input.FileSplit;
//import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
//import org.apache.hadoop.util.GenericOptionsParser;
//import org.apache.hadoop.util.Tool;
//import org.apache.hadoop.util.ToolRunner;
//
//import java.io.IOException;
//
///**
// * Project Name: hello-spark
// * author: Suns
// * Date: 2017/10/23 9:07
// * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
// * Description:
// */
//public class SmallFilesToSequenceFileConverter extends Configured implements Tool {
//
//    static class SequenceFileMapper extends Mapper<NullWritable, BytesWritable, Text, BytesWritable> {
//        private Text fileNameKey;
//
//        @Override
//        protected void setup(Context context) throws IOException, InterruptedException {
//            InputSplit split = context.getInputSplit();
//            Path path = ((FileSplit) split).getPath();
//            fileNameKey = new Text(path.toString());
//        }
//
//        @Override
//        protected void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
//            context.write(fileNameKey, value);
//        }
//    }
//
//
//    @Override
//    public int run(String[] args) throws Exception {
//
//        Configuration conf = new Configuration();
//        //是否运行为本地模式，就是看这个参数值是否为local，默认就是local
//        conf.set("mapreduce.framework.name", "local");
//
//        //本地模式运行mr程序时，输入输出的数据可以在本地，也可以在hdfs上
//        //到底在哪里，就看以下两行配置你用哪行，默认就是file:///
//        /*conf.set("fs.defaultFS", "hdfs://mini1:9000/");*/
//        conf.set("fs.defaultFS", "file:///");
//
//        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
//        if (otherArgs.length != 2) {
//            System.err.println("Usage: combinefiles <in> <out>");
//            System.exit(2);
//        }
//
//        Job job = Job.getInstance(conf, "combine small files to sequencefile");
//        job.setJarByClass(SmallFilesToSequenceFileConverter.class);
//
//        job.setInputFormatClass(WholeFileInputFormat.class);
//        job.setOutputFormatClass(SequenceFileOutputFormat.class);
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(BytesWritable.class);
//        job.setMapperClass(SequenceFileMapper.class);
//
//        FileInputFormat.setInputPaths(job, new Path(args[0]));
//        FileOutputFormat.setOutputPath(job, new Path(args[1]));
//
//        return job.waitForCompletion(true) ? 0 : 1;
//    }
//
//    public static void main(String[] args) throws Exception {
//        args = new String[]{"c:/test/custom_inputformat", "c:/test/output/custom_inputformat"};
//        int exitCode = ToolRunner.run(new SmallFilesToSequenceFileConverter(),
//                args);
//        System.exit(exitCode);
//
//    }
//}