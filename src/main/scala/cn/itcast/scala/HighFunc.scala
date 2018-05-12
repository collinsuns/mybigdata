package cn.itcast.scala

/**
  * Created by jh on 2017/7/7.
  */
object HighFunc {



  //定义方法
  def multiply(x: Int, y:Int) : Int = x * y

  //定义函数,表示定义一个匿名函数，然后把这个匿名函数给f1这个引用
  // val 常量名 = (输入参数和类型列表)=>函数定义
  val f1 = (x: Int, y: Int) => x * y


  //定义函数
  //val 常量名:(输入参数类型列表)=>返回类型 = (输入参数)=>函数定义
  val func: Int => Int = {x => x * x}



  //柯理化的2中写法
  def m1(x: Int)(y: Int) : Int = x * y
  def m2(x: Int) = (y: Int) => x * y

  //柯理化没有第一个参数，其实是这样def m3() = (x: Int) => {x * x}
  def m3 = (x: Int) => {x * x}

}
