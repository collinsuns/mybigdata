package cn.itcast.scala.implic

/**
  * Project Name: hello-spark
  * User: Suns
  * Date: 2018-01-20 17:50
  * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
  */
object MyPredef {
  implicit def Girl2Ordered(girl: Girl) = new Ordered[Girl] {
    override def compare(that: Girl): Int = {
      if (girl.faceValue == that.faceValue) {
        girl.size - that.size
      } else {
        girl.faceValue - that.faceValue
      }
    }
  }

  //与上面是相同的效果，只是写法不一致，这个同样适用于ordering，
  //  implicit val Girl2Ordered = (g: Girl) => new Ordered[Girl] {
  //    override def compare(that: Girl): Int = {
  //      g.faceValue - that.faceValue
  //    }
  //  }


  implicit val Girl2Ordering = new Ordering[Girl] {
    override def compare(x: Girl, y: Girl): Int = {
      if (x.faceValue == y.faceValue) {
        x.size - y.size
      } else {
        x.faceValue - y.faceValue
      }
    }
  }
}
