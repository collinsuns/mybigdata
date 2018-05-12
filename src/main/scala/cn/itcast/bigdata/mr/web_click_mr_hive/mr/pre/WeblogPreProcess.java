package cn.itcast.bigdata.mr.web_click_mr_hive.mr.pre;

import cn.itcast.bigdata.mr.web_click_mr_hive.mrbean.WebLogBean;
import cn.itcast.bigdata.mr.web_click_mr_hive.mrbean.WebLogParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 处理原始日志，过滤出真实pv请求
 * 转换时间格式
 * 对缺失字段填充默认值
 * 对记录标记valid和invalid
 *
 * @author
 *
 */
public class WeblogPreProcess {

    static class WebblgPreProcessMapper extends Mapper<Writable, Text, Text, NullWritable> {
        Set<String> pages = new HashSet<>();
        Text k = new Text();
        NullWritable v = NullWritable.get();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {

            pages.add("/about");
            pages.add("/black-ip-list/");
            pages.add("/cassandra-clustor/");
            pages.add("/finance-rhive-repurchase/");
            pages.add("/hadoop-family-roadmap/");
            pages.add("/hadoop-hive-intro/");
            pages.add("/hadoop-zookeeper-intro/");
            pages.add("/hadoop-mahout-roadmap/");
        }

        @Override
        protected void map(Writable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            WebLogBean webLogBean = WebLogParser.parser(line);
            // 过滤js/图片/css等静态资源
            WebLogParser.filtStaticResource(webLogBean, pages);
			/* if (!webLogBean.isValid()) return; */

            k.set(webLogBean.toString());
            context.write(k, v);

        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(WeblogPreProcess.class);

        job.setMapperClass(WebblgPreProcessMapper.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setNumReduceTasks(0);
        job.waitForCompletion(true);

    }

}
