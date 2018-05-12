package cn.itcast.spark.day2

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jh on 2017/4/16.
  */
object ForeachDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("ForeachDemo").setMaster("local")
    val sc = new SparkContext(conf)
    val rdd1 = sc.parallelize(List(1,2,3,4,5,6,7,8,9), 3)
    rdd1.foreach(println(_))
    sc.stop()
  }
}
