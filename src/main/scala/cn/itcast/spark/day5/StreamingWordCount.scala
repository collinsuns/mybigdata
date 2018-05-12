package cn.itcast.spark.day5

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jh on 2017/5/15.
  */
object StreamingWordCount {
  def main(args: Array[String]): Unit = {

    LoggerLevels.setStreamingLogLevels()

    val conf = new SparkConf().setAppName("StreamingWordCount").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(5))

    val ds = ssc.socketTextStream("192.168.7.201", 8888)
    val result = ds.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_)

    result.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
