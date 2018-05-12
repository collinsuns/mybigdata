package cn.itcast.scala.implic

/**
  * Project Name: hello-spark
  * User: Suns
  * Date: 2018-01-20 17:27
  * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
  */
class Boy(val name: String, var faceValue: Int) extends Comparable[Boy]{
  override def compareTo(o: Boy): Int = {
    this.faceValue - o.faceValue
  }
}
