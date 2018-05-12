package cn.itcast.scala

/**
  * Created by jh on 2017/6/29.
  */
object TupleDemo {

  def main(args: Array[String]): Unit = {
    val t,(a, b, c) = ("hadoop", 3.14, 666)
    println(t)
    println(t._1)

    val array = Array(("Tome",10),("Jerry",90))
    println(array.toMap)

    val name = Array("Tome", "Jerry", "Suke")
    val score = Array(80, 90, 60)
    println(name.zip(score))
  }

}
