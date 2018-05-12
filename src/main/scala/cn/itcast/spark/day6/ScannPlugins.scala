package cn.itcast.spark.day6

import cn.itcast.spark.day5.LoggerLevels
import kafka.serializer.StringDecoder
import org.apache.commons.lang3.time.FastDateFormat
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Milliseconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Project Name: hello-spark
  * User: Suns
  * Date: 2018-02-04 16:25
  * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
  */
object ScannPlugins {
  def main(args: Array[String]): Unit = {
    LoggerLevels.setStreamingLogLevels()
    val dataFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")
    val Array(zkQuorum, group, topics, numThreads) = Array("hd4:2181,hd2:2181,hd3:2181", "aa", "gamelog2", "2")
    val conf = new SparkConf().setAppName("ScannPlugins").setMaster("local[2]")
    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Milliseconds(10000))
    sc.setCheckpointDir("c://checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> zkQuorum,
      "group.id" -> group,
      "auto.offset.reset" -> "smallest"
    )

    val dstream = KafkaUtils.createStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicMap, StorageLevel.MEMORY_AND_DISK_SER)

    val lines = dstream.map(_._2)
    val splitedLines = lines.map(_.split("\t"))
    val filteredLines = splitedLines.filter(f => {
      val et = f(3)
      val item = f(8)
      et == "11" && item == "强效太阳水"
    })

    val window = filteredLines.map(f => (f(7), dataFormat.parse(f(12)).getTime)).groupByKeyAndWindow(Milliseconds(30000),Milliseconds(20000))
    val filtered = window.filter(_._2.size >= 5)

    val itemAvgTime = filtered.mapValues(it => {
      val list = it.toList.sorted
      val size = list.size
      val first = list(0)
      val last = list(size -1)
      val cha: Double = last - first
      cha / size
    })

    val badUser = itemAvgTime.filter(_._2 < 1000)
    
    filteredLines.print()
    ssc.start()
    ssc.awaitTermination()



  }

}
