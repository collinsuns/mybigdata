package cn.itcast.scala

/**
  * Project Name: hello-spark
  * User: Suns
  * Date: 2018-01-10 11:08
  * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
  */
object Practies {
  def main(args: Array[String]): Unit = {
    val lst0 = List(1,7,9,8,0,3,5,4,6,2)

    val lst1 = lst0.map(_ * 10)

    val lst2 = lst0.filter(_ % 2 == 0)

    val lst3 = lst0.toArray.sorted.toList

    val lst4 = lst3.reverse

    val lst5 = lst4.grouped(4).toList

    val lst6 = lst5.toList

    val lst7 = lst6.flatten

    println(lst5)

    val lines = List("hello tom hello jerry", "hello jerry", "hello kitty")

    val result = lines.map(_.split(" ")).flatten
  }

}
