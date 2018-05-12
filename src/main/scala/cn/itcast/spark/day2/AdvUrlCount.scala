package cn.itcast.spark.day2

import java.net.URL

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jh on 2017/4/17.
  */
object AdvUrlCount {
  def main(args: Array[String]): Unit = {

    val arr = Array("java.itcast.cn", "php.itcast.cn", "net.itcast.cn")

    val conf = new SparkConf().setAppName("ForeachDemo").setMaster("local")
    val sc = new SparkContext(conf)

    val rdd1 = sc.textFile("c://test/itcast.log").map(line => {
      val f = line.split("\t")
      (f(1), 1)
    })
    val rdd2 = rdd1.reduceByKey(_ + _)

    val rdd3 = rdd2.map(t => {
      val url = t._1
      val host = new URL(url).getHost
      (host, url, t._2)
    })

    for (ins <- arr) {
      val rdd = rdd3.filter(_._1 == ins)
      val result = rdd.sortBy(_._3, false).take(3)
      println(result.toBuffer)
    }

  }
}
