package cn.itcast.spark.test

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jh on 2017/7/19.
  */
object UserLocation {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("UerLocation").setMaster("local[2]")
    val sc = new SparkContext(conf)


    val rdd0 = sc.textFile("c://test/bs_log").map(line => {
      val fields = line.split(",")
      val mobileNum = fields(0)
      val lac = fields(2)
      val time = if(fields(3) == 1) -fields(1).toLong else fields(0).toLong
      ((mobileNum, lac), time)
    })

    val rdd1 = rdd0.reduceByKey(_ + _).map(line => {
      val lac = line._1._2
      val mobileNum = line._1._1
      val time = line._2
      (lac, (mobileNum, time))
    })

    val rdd2 = sc.textFile("c://test/lac_info.txt").map(line => {
      val fields = line.split(",")
      (fields(0), (fields(1), fields(2)))
    })

    val rdd3 = rdd1.join(rdd2).map(line => {
      val lac = line._1
      val mobileNum  = line._2._1._1
      val time = line._2._1._2
      val x = line._2._2._1
      val y = line._2._2._2
      (mobileNum, lac, time, x, y)

    })

    val rdd4 = rdd3.groupBy(_._1)
    val rdd5 = rdd4.mapValues(it => {
      it.toList.sortBy(_._3).reverse.take(2)
    })

    rdd5.saveAsTextFile("c://test/out1")
    println(rdd5.collect().toBuffer)
    sc.stop()

  }





}
