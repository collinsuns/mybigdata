package cn.itcast.scala

import java.io.IOException

/**
  * Created by jh on 2017/6/29.
  */
class Student(val name: String, val age: Int) {

  println("执行主构造器")

  try {
    println("读取文件")
  } catch {
    case e: NullPointerException =>println("打印异常Exception : "+ e)
    case e: IOException =>println("打印异常Exception : "+ e)
  } finally {
    println("执行finally部分")
  }

  private var gender = "male"

  def this(name: String, age: Int, gender : String){
    this(name, age)
    println("负责构造器")
    this.gender = gender
  }


}
