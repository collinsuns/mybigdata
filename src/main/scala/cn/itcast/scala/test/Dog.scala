package cn.itcast.scala.test

/**
  * Project Name: hello-spark
  * User: Suns
  * Date: 2018-01-17 17:58
  * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
  */
class Dog {

  val id = 1
  private var name = "itcast"

  def printName(): Unit = {
    println(Dog.CONSTANT + name)
  }

}

object Dog{
  private val CONSTANT = "WANG:"

  def main(args: Array[String]): Unit = {
    val p = new Dog
    p.name = "123";
    p.printName()
  }
}
