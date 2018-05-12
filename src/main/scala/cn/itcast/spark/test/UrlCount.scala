package cn.itcast.spark.test

import java.net.URL

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jh on 2017/7/19.
  */
object UrlCount {
  def main(args: Array[String]): Unit = {

    val arr = Array("java.itcast.cn", "php.itcast.cn", "net.itcast.cn")

    val conf = new SparkConf().setAppName("UrlCount").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val rdd0 = sc.textFile("c://test/itcast.log").map(line => {
      val fields = line.split("\t")
      (fields(1), 1)
    })

    val rdd1 = rdd0.reduceByKey(_+_)

    val rdd2 = rdd1.map(line => {
      val url = line._1
      val host = new URL(url).getHost
      (host, url, line._2)
    })

    for(ins <- arr){
      val rdd = rdd2.filter(_._1 == ins)
      val result = rdd.sortBy(_._3, false).take(3)
      println(result.toBuffer)
    }
  }
}
