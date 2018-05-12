package cn.itcast.scala.test

import scala.util.Random

/**
  * Project Name: hello-spark
  * User: Suns
  * Date: 2018-01-17 20:29
  * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
  */
object CaseDemo1 extends App {
  val arr = Array("YoshizawaAkiho", "YuiHatano", "AoiSola")
  val name = arr(Random.nextInt(arr.length))
  name match {
    case "YoshizawaAkiho" => println("吉泽老师...")
    case "YuiHatano" => println("波多老师...")
    case _ => println("真不知道你们在说什么...")

  }


  val arr1 = Array("hello", 1, 2.0, CaseDemo1)
  val v = arr1(Random.nextInt(arr1.length))
  println(v)

  v match {
    case x:Int => println("Int")
    case x:String => println("String")
    case _ => println("CaseDemo1")
    case x:Double => println("Double")
  }

  val arr2 = Array(0, 3, 5)
  arr2 match {
    case Array(1, x, y) => println(x + "" + y)
    case Array(0) => println("only 0")
    case Array(0, _*) => println("0 ...")
    case _ => println("something else")
  }

  val lst = List(3, -1)
  lst match {
    case 0 :: Nil =>println("only 0")
    case x :: y :: Nil =>println(s"x: $x y: $y")
    case 0 :: tail =>println("0 ...")
    case _ =>println("something else")
  }

  val tup = (2, 3, 7)
  tup match {
    case (1, x, y) =>println(s"1, $x , $y")
    case (_, z, 5) =>println(z)
    case  _ =>println("else")
  }

}
