//package cn.itcast.bigdata.mr.custom_inputformat;
//
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.BytesWritable;
//import org.apache.hadoop.io.NullWritable;
//import org.apache.hadoop.mapreduce.InputSplit;
//import org.apache.hadoop.mapreduce.JobContext;
//import org.apache.hadoop.mapreduce.RecordReader;
//import org.apache.hadoop.mapreduce.TaskAttemptContext;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//
//import java.io.IOException;
//
///**
// * Project Name: hello-spark
// * author: Suns
// * Date: 2017/10/23 8:51
// * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
// * Description:
// */
//public class WholeFileInputFormat extends FileInputFormat {
//
//    @Override
//    protected boolean isSplitable(JobContext context, Path filename) {
//        return false;
//    }
//
//    @Override
//    public RecordReader<NullWritable, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
//        WholeFileRecordReader reader = new WholeFileRecordReader();
//        reader.initialize(split, context);
//        return reader;
//    }
//}