package cn.itcast.scala.test

/**
  * Project Name: hello-spark
  * User: Suns
  * Date: 2018-01-17 18:04
  * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
  */
class ApplyDemo {
  def main(args: Array[String]) {
    //调用了Array伴生对象的apply方法
    //def apply(x: Int, xs: Int*): Array[Int]
    //arr1中只有一个元素5
    val arr1 = Array(5)
    println(arr1.toBuffer)

    //new了一个长度为5的array，数组里面包含5个null
    var arr2 = new Array(5)
  }

}
