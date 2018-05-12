package cn.itcast.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Project Name: mybigdata
 * author: Suns
 * Date: 2017/10/4 19:24
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:用流的方式来操作hdfs上的文件,可以实现读取指定偏移量范围的数据
 */
public class HdfsStreamAccess {

    FileSystem fs = null;
    Configuration conf = null;

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        conf = new Configuration();
        //可以直接传入 uri和用户身份
        fs = FileSystem.get(new URI("hdfs://hd1:9000"), conf, "hadoop");
    }

    /**
     * 通过流的方式上传文件到hdfs
     *
     * @throws Exception
     */
    @Test
    public void testUpload() throws IOException {
        FSDataOutputStream outputStream = fs.create(new Path("/test.md5"));
        FileInputStream inputStream = new FileInputStream("c:/test/test.md5");

        IOUtils.copy(inputStream, outputStream);
    }

    @Test
    public void testDownload() throws IOException {
        FSDataInputStream inputStream = fs.open(new Path("/test.md5"));

        FileOutputStream outputStream = new FileOutputStream("c:/test/test.md5.back");
        IOUtils.copy(inputStream, outputStream);
    }

    @Test
    public void testRandomAccess() throws Exception {

        FSDataInputStream inputStream = fs.open(new Path("/angelababy.love"));

        inputStream.seek(12);

        FileOutputStream outputStream = new FileOutputStream("d:/angelababy.love.part2");

        IOUtils.copy(inputStream, outputStream);


    }

    /**
     * 显示hdfs上文件的内容
     *
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testCat() throws IllegalArgumentException, IOException {

        FSDataInputStream in = fs.open(new Path("/test.md5"));

        IOUtils.copy(in, System.out);

//		IOUtils.copyBytes(in, System.out, 1024);
    }

}