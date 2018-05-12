package cn.itcast.scala

/**
  * Created by jh on 2017/6/29.
  */
object ConditionDemo {

  def main(args: Array[String]): Unit = {
    val x = 1

    val y = if(x> 0) 1 else -1
    println("y:" + y)

    val z = if(x > 1) 1 else "error"
    println("z: " + z)

    val m = if(x > 2) 1
    println("m: " + m)

    val n = if(x > 2) 1 else ()
    println("n: " + n)

    val k = if(x > 0) 1 else if(x < 0) 2 else "error"
    println("k: " + k)


  }

}
