package cn.itcast.spark.day2

import java.net.URL

import org.apache.spark.{SparkConf, SparkContext}


/**
  * Created by jh on 2017/4/17.
  */
object UrlCount {
  def main(args: Array[String]): Unit = {
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
      (host, (url, t._2))
    })
//    val rdd4 = rdd3.groupBy(_._1).mapValues(it => {
    //      it.toList.sortBy(_._3).reverse.take(3)
    //    })

    rdd3.repartition(3).saveAsTextFile("c://test/out1")

    println(rdd3.collect().toBuffer)
    sc.stop()



  }
}
