package cn.itcast.scala

import scala.collection.mutable.ArrayBuffer
/**
  * Created by jh on 2017/6/29.
  */
object ArrayDemo {

  def main(args: Array[String]): Unit = {
    val arry = new Array[Int](10)
    println(arry)
    println(arry.toBuffer)


    val arry2 = Array[Int](10)
    println("arry2: " + arry2.toBuffer)

    val arry3 = Array("hadoop","storm","spark")
    println("arry3: " + arry3(2))

    val ab = ArrayBuffer[Int]()
    ab += 1
    ab += (2, 3, 4, 5)
    ab ++= Array(6,7)
    ab ++= ArrayBuffer(9,10)
    ab.insert(0, -1, 0)
    ab.remove(8,2)


    println(ab)





  }

}
