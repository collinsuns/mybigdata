package cn.itcast.spark.test

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by jh on 2017/8/8.
  */
object StreamingWorkCount {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("StreamingWordCunt").setMaster("local[2]")
    val sc = new SparkContext(conf)


    val ssc = new StreamingContext(sc, Seconds(5))

    val ds = ssc.socketTextStream("192.168.7.201", 8888)

    val result = ds.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

    result.print()

    ssc.start()
    ssc.awaitTermination()

  }

}
