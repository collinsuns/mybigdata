package cn.itcast.scala

/**
  * Created by jh on 2017/6/29.
  */
object OptionDemo {

  def main(args: Array[String]) {
    val map = Map("a"->1, "b"->2)
    val v = map.get("b") match {
      case Some(i) => i
      case None =>0
    }
    println(v)
    //更好的方式
    val v1 = map.getOrElse("c", 0)
    println(v1)
  }


}
