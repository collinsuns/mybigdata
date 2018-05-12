package cn.itcast.scala.myimplicit

import java.io.File

import scala.io.Source

import MyPredef._

/**
  * Created by jh on 2017/7/9.
  */
class RichFile(val f: File) {
  def read() = Source.fromFile(f).mkString

}
object RichFile{
  def main(args: Array[String]): Unit = {
    val f = new File("c://words.txt")
//    val contents = new RichFile(f).read()
    val contents = f.read()
    println(contents)
  }
}
