package cn.itcast.spark.test

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Project Name: hello-spark
  * User: Suns
  * Date: 2018-01-27 17:06
  * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
  */
object AdvUserLocation {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("AdvUserLocation").setMaster("local[2]")

    val sc = new SparkContext(conf)

    val rdd1= sc.textFile("c://test/bs_log").map(line => {
      val field = line.split(",")
      val mobile = field(0)

      val loc = field(2)
      val time = if(field(3) == "1") -field(1).toLong else field(1).toLong

      ((mobile, loc), time)
    })

    val rdd2 = rdd1.reduceByKey(_+_).map(x => {
      val mobile = x._1._1
      val loc = x._1._2
      val time = x._2

      (loc, (mobile, time))
    })

    val rdd3 = sc.textFile("c://test/lac_info.txt").map(line => {
      val f = line.split(",")
      (f(0), (f(1), f(2)))
    })

    val rdd4 = rdd2.join(rdd3).map(x => {
      val loc = x._1
      val mobile = x._2._1._1
      val time = x._2._1._2
      val lon = x._2._2._1
      val log = x._2._2._2
      (mobile, loc, time, lon, log)
    })

    val rdd5 = rdd4.groupBy(_._1)
    val rdd6 = rdd5.mapValues(it => {
      it.toList.sortBy(_._2).reverse.take(2)
    })

    rdd6.saveAsTextFile("c://test//output//UserLocation")

    println(rdd6.collect().toBuffer)
    sc.stop()






  }

}
