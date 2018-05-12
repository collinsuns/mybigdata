package cn.itcast.scala.myimplicit

/**
  * Created by jh on 2017/7/9.
  */

object Context{
  implicit val str = "Coco"
  implicit val i = 9
}

object ImplicitValue {

  def sayHi()(implicit  name: String = "Tom") : Unit = {
    println(s"hi $name")
  }

  def main(args: Array[String]): Unit = {
    import Context._
    println(1 to 10)
    sayHi()
  }

}
