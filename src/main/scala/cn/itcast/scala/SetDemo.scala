package cn.itcast.scala

import scala.collection.mutable.Map
//import scala.collection.immutable.Map

/**
  * Created by jh on 2017/6/29.
  */
object SetDemo {
  def main(args: Array[String]): Unit = {
    val score1 = Map("Tom"->"80", "Jerry"->"90", "Lucy"->"70")
    val score2 = Map(("Tom",80),("Jerry",90),("Lucy",70))


    println(score1("Tom"))
    println(score2("Jerry"))
    println(score1.getOrElse("suke",0))

    score1("Tom") = "100"
    println(score1("Tom"))

    score1 += (("Coco","99"),("Lilei","88"))
    println(score1("Lilei"))
  }

}
