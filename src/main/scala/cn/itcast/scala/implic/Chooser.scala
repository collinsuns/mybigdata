package cn.itcast.scala.implic

/**
  * Project Name: hello-spark
  * User: Suns
  * Date: 2018-01-20 17:44
  * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
  */
//视图界定,必须传进去一个隐式转换的函数,choose方法就可以使用Ordered里面的>方法了
class Chooser[T <% Ordered[T]] {

  def choose(first: T, second: T): T = {
    if(first > second) first else second
  }
}

//上下文界定，必须传进去一个隐式转换的值，choose方法就可以使用Orderrong里面的gt方法了
//class Chooser[T : Ordering] {
//
//  def choose(first: T, second: T): T = {
//    val ord = implicitly[Ordering[T]]
//
//    if(ord.gt(first, second)) first else second
//  }
//}

object Chooser {
  def main(args: Array[String]): Unit = {
    import MyPredef._
    val c = new Chooser[Girl]

    val g1 = new Girl("anglebaby", 90, 66)
    val g2 = new Girl("hatano", 99, 33)
    val g = c.choose(g1, g2)
    println(g.name)
  }
}


