package cn.itcast.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jh on 2017/6/19.
 */
public class HBaseTest {

    static Configuration config = null;
    private Connection connection = null;
    private Table table = null;


    @Before
    public void init() throws IOException {
        config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "hd2,hd3,hd4");
        config.set("hbase.zookeeper.property.clientPort", "2181");
        connection = ConnectionFactory.createConnection(config);
        table = connection.getTable(TableName.valueOf("user"));

    }

    /**
     * 创建一个表
     *
     * @throws Exception
     */
    @Test
    public void createTable() throws Exception {
        // 创建表管理类
        HBaseAdmin admin = new HBaseAdmin(config); // hbase表管理
        // 创建表描述类
        TableName tableName = TableName.valueOf("test"); // 表名称
        HTableDescriptor desc = new HTableDescriptor(tableName);
        // 创建列族的描述类
        HColumnDescriptor family = new HColumnDescriptor("info"); // 列族
        // 将列族添加到表中
        desc.addFamily(family);
        HColumnDescriptor family2 = new HColumnDescriptor("info2"); // 列族
        // 将列族添加到表中
        desc.addFamily(family2);
        // 创建表
        admin.createTable(desc); // 创建表
    }

    @Test
    public void deleteTable() throws IOException {
        HBaseAdmin admin = new HBaseAdmin(config);
        admin.disableTable("testtest");
        admin.deleteTable("testtest");
        admin.close();
    }

    @Test
    public void insertData() throws IOException {
        table.setAutoFlushTo(true);
        table.setWriteBufferSize(534534534);
        ArrayList<Put> puts = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            Put put = new Put(Bytes.toBytes("1234" + i));
            put.add(Bytes.toBytes("info1"), Bytes.toBytes("name"), Bytes.toBytes("lisi" + i));
            put.add(Bytes.toBytes("info1"), Bytes.toBytes("age"), Bytes.toBytes("0" + i));
            puts.add(put);

        }

        table.put(puts);
        table.flushCommits();
    }

    @Test
    public void updateData() throws IOException {
        Put put = new Put(Bytes.toBytes("12341"));
        put.add(Bytes.toBytes("info1"), Bytes.toBytes("name"), Bytes.toBytes("zhangsan"));
        put.add(Bytes.toBytes("info1"), Bytes.toBytes("age"), Bytes.toBytes("66666"));
        table.put(put);
        table.flushCommits();

    }

    @Test
    public void deleteData() throws IOException {
        Delete delete = new Delete(Bytes.toBytes("12341"));

        table.delete(delete);
        table.flushCommits();
    }

    @Test
    public void queryData() throws IOException {
        Get get = new Get(Bytes.toBytes("12343"));
        Result result = table.get(get);
        System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("name"))));
        System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("age"))));
    }

    @Test
    public void scanData() throws IOException {
        Scan scan = new Scan();
//        scan.addFamily(Bytes.toBytes("info1"));

//        scan.addColumn(Bytes.toBytes("info1"), Bytes.toBytes("name"));

        scan.setStartRow(Bytes.toBytes("12341"));
        scan.setStopRow(Bytes.toBytes("12342"));

        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {
            System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("name"))));
            System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("age"))));
            System.out.println("-----------------------------");
        }

    }

    @Test
    public void scanDataByFilter1() throws IOException {
        Scan scan = new Scan();

        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes("info1"), Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("lisi33"));

        scan.setFilter(singleColumnValueFilter);

        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {
            System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("name"))));
            System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("age"))));
            System.out.println("-----------------------------");
        }

    }

    @Test
    public void scanDataFilter2() throws IOException {
        Scan scan = new Scan();

        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("^12341"));

        scan.setFilter(rowFilter);

        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {
            System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("name"))));
            System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("age"))));
            System.out.println("-----------------------------");
        }


    }

    @Test
    public void scanDataFilter3() throws IOException {
        Scan scan = new Scan();

        ColumnPrefixFilter filter = new ColumnPrefixFilter(Bytes.toBytes("na"));

        scan.setFilter(filter);

        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {

            System.out.println("rowkey" + Bytes.toString(result.getRow()));


            if(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("name")) != null){
                System.out.println("info1:name " + Bytes.toString(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("name"))));

            }

            if(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("age")) != null){
                System.out.println("info1:age " + Bytes.toString(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("age"))));
            }

        }
    }

    @Test
    public void scanDataFilter4() throws IOException {

        Scan scan = new Scan();

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);

        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("^1234"));
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes("info1"), Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("lisi48"));

        filterList.addFilter(rowFilter);
        filterList.addFilter(singleColumnValueFilter);

        scan.setFilter(filterList);

        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {

            System.out.println("rowkey" + Bytes.toString(result.getRow()));


            if(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("name")) != null){
                System.out.println("info1:name " + Bytes.toString(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("name"))));

            }

            if(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("age")) != null){
                System.out.println("info1:age " + Bytes.toString(result.getValue(Bytes.toBytes("info1"), Bytes.toBytes("age"))));
            }

        }


    }




    @After
    public void close() throws Exception {
        table.close();
        connection.close();
    }



}
