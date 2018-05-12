package cn.itcast.scala.implic

/**
  * Project Name: hello-spark
  * User: Suns
  * Date: 2018-01-20 17:29
  * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
  */
object TestBoy {
  def main(args: Array[String]): Unit = {
    val b1 = new Boy("laoduan", 99)
    val b2 = new Boy("laozhao", 999)

    val arr = Array(b1, b2)

    var sorted = arr.sortBy(x => x).reverse

    for(b <- sorted){
      println(b.name)
    }
  }

}
