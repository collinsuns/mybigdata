package cn.itcast.scala

/**
  * Created by jh on 2017/6/29.
  */
object ForDemo {

  def main(args: Array[String]): Unit = {
    for(i <- 1 to 10) {
      println(i)
    }

    val array = Array("a", "b", "c")
    for(i <- array) {
      println(i)
    }

    for(i <- 1 to 3; j <- 1 to 3 if i != j) {
      println(i + "" + j + "")
    }

    val v = for(i <- 1 to 10) yield i * 10
    println(v)

  }

}
