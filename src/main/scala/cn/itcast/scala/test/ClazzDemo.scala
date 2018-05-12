package cn.itcast.scala.test

/**
  * Project Name: hello-spark
  * User: Suns
  * Date: 2018-01-17 18:08
  * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
  */
object ClazzDemo {
  def main(args: Array[String]): Unit = {
    val h = new Human
    println(h.flight())
  }

}

trait Flyable{
  def fly(): Unit={
    println("I can fly")
  }

  def flight(): String
}

abstract class Animal{
  def run(): Int
  val name: String
}

class Human extends Animal with Flyable{
  val name = "abc"

  val t1,t2,(a,b,c) = {
    println("ABC")
    (1, 2, 3)
  }

  def run() = {
    1
  }

  override def flight() = {
    "fight with bangzi"
  }
}
