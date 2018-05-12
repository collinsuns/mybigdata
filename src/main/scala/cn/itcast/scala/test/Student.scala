package cn.itcast.scala.test

import java.io.IOException

/**
  * Project Name: hello-spark
  * User: Suns
  * Date: 2018-01-17 17:40
  * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
  */
class Student(val name: String, val age: Int) {

  println("执行主构造器")

  try {
    println("读取文件")
    throw new IOException("io exception")
  } catch {
    case e: NullPointerException => println("打印异常Exception： " + e)
    case e: IOException => println("打印异常Exception： " + e)
  } finally {
    println("执行finally部分")
  }

  private var gender = "male"

  def this(name: String, age: Int, gender: String){
    this(name, age)

    println("执行辅助构造器")
    this.gender = gender
  }
}

object Student{
  def main(args: Array[String]): Unit = {
    val s = new Student("TOm", 20)
  }
}



class Queen private(val name: String, prop:Array[String], private var age: Int = 18){
  def description = name + " is " + age + " years old with " + prop.toBuffer
}
object Queen{
  def main(args: Array[String]): Unit = {
    val q = new Queen("hadoop", Array("lala", "kaka"), 20)
    println(q.description)
  }
}
