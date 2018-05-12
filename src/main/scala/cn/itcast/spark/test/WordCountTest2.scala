package cn.itcast.spark.test

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jh on 2017/7/17.
  */
object WordCountTest2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("WC2222").setMaster("local");
    val sc = new SparkContext(conf)
    val rdd1 = sc.textFile("C:\\test\\wordcount").flatMap(_.split(" "))
    val rdd2 = sc.textFile("C:\\test\\wordcount").map(_.split(" "))

    println(rdd1.collect().toBuffer)

    sc.stop()
  }
}
