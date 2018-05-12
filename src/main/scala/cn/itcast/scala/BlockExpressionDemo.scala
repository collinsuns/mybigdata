package cn.itcast.scala

/**
  * Created by jh on 2017/6/29.
  */
object BlockExpressionDemo {

  def main(args: Array[String]): Unit = {
    val x = 1

    val result = {
      if(x < 0) {
        -1
      } else if (x > 0) {
        1
      } else{
        0
      }
    }
    println(result)
  }


}
