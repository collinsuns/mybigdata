package cn.itcast.scala

/**
  * Created by jh on 2017/6/29.
  */
object MethodAndFunctionDemo {

  def m(f : (Int , Int ) => Int) = {
    f(2,6)
  }

  val f1 = (x: Int, y: Int) => x + y
  val f2 = (x: Int, y: Int) => x * y

  def m2(x: Int, y: Int) : Int = x * y



  def main(args: Array[String]): Unit = {
    val r1 = m(f1)
    println(r1)

    val r2 = m(f2)
    println(r2)

    val r3 = m(m2 _)
    println(r3)
  }



}
