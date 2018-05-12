package cn.itcast.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Project Name: mybigdata
 * author: Suns
 * Date: 2017/10/1 13:30
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description: 客户端去操作hdfs时，是有一个用户身份的
 * 默认情况下，hdfs客户端api会从jvm中获取一个参数来作为自己的用户身份：-DHADOOP_USER_NAME=hadoop
 *
 * 也可以在构造客户端fs对象时，通过参数传递进去
 */
public class HdfsClientDemo {

    FileSystem fs = null;

    Configuration conf = null;

    @Before
    public void init() throws Exception {
        conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://hd1:9000");

        //拿到一个文件系统操作的客户端实例对象
		/*fs = FileSystem.get(conf);*/
        //可以直接传入 uri和用户身份
        //最后一个参数为用户名
        fs = FileSystem.get(new URI("hdfs://hd1:9000"), conf, "hadoop");
    }

    @Test
    public void testUpload() throws Exception {
        Thread.sleep(2000);
        fs.copyFromLocalFile(new Path("c:/test/flow.log"), new Path("/flow.log.copy"));
        fs.close();
    }

    @Test
    public void testDownload() throws Exception {
        fs.copyToLocalFile(new Path("/flow.log.copy"), new Path("c:/test/"));
        fs.close();
    }

    @Test
    public void testConf() {
        Iterator<Entry<String, String>> iterator = conf.iterator();
        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            System.out.println(entry.getValue() + "--" + entry.getKey());
        }
    }

    /**
     * 创建目录
     */
    @Test
    public void mkdirTest() throws IOException {
        boolean mkdirs = fs.mkdirs(new Path("/aaa/bbb"));
        System.out.println(mkdirs);
    }

    /**
     * 删除目录
     */
    @Test
    public void deleteTest() throws IOException {
        boolean delete = fs.delete(new Path("/aaa"), true);
        System.out.println(delete);
    }

    @Test
    public void listTest() throws IOException {
        FileStatus[] fileStatuses = fs.listStatus(new Path("/"));
        for (FileStatus fileStatus : fileStatuses) {
            System.out.println(fileStatus.getPath() + "============" + fileStatus.toString());
        }
        //会递归找到所有的文件
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        while(listFiles.hasNext()){
            LocatedFileStatus next = listFiles.next();
            String name = next.getPath().getName();
            Path path = next.getPath();
            System.out.println(name + "---" + path.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://hd1:9000");
        //拿到一个文件系统操作的客户端实例对象
        FileSystem fs = FileSystem.get(conf);

        fs.copyFromLocalFile(new Path("G:/access.log"), new Path("/access.log.copy"));
        fs.close();
    }

}