package cn.itcast.scala.myimplicit

import java.io.File

/**
  * Created by jh on 2017/7/9.
  */
object MyPredef {
  implicit def fileToRichFile(f: File) = new RichFile(f)

}
