package cn.itcast.spark.test

import java.net.URL

import org.apache.spark.{HashPartitioner, Partitioner, SparkConf, SparkContext}

import scala.collection.mutable


object UrlCountPartitionTest{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("UrlPartTest").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val rdd1 = sc.textFile("c://test//itcast.log").map(line => {
      val f = line.split("\t")
      (f(1), 1)
    })

    val rdd2 = rdd1.reduceByKey(_+_)

    val rdd3 = rdd2.map(t => {
      val url = t._1
      val host = new URL(url).getHost
      (host, (url, t._2))
    })

    val ins = rdd3.map(_._1).distinct().collect()

    val hostParitioner = new hostPartitionTest(ins)

    val rdd4 = rdd3.partitionBy(hostParitioner)

    rdd4.saveAsTextFile("c://out4")

    sc.stop()
  }
}


class hostPartitionTest(ins: Array[String]) extends Partitioner{

  val insMap = new mutable.HashMap[String, Int]()
  var count = 0
  for(i <- ins){
    insMap += (i -> count)
    count += 1
  }

  override def numPartitions: Int = ins.length

  override def getPartition(key: Any): Int = {
    insMap.getOrElse(key.toString, 0)

  }
}
