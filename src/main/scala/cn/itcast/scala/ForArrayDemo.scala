package cn.itcast.scala

/**
  * Created by jh on 2017/6/29.
  */
object ForArrayDemo {

  def main(args: Array[String]): Unit = {
    val array = Array(1,3,4,5,7,8,9)
    for(i <- array) {
      println(i)
    }

    for( i <- (0 until array.length).reverse) {
      println(array(i))
    }

    println("-------------------------")
    val array2 = for(i <- array) yield i * 2
    for (i <- array2){
      println(i)
    }


    println("-------------------------")
    val res = for(i <- array if i % 2 == 0) yield i * 10
    println(res.toBuffer)

    println("-------------------------")
    val arr = array.filter(_ % 2 == 0).map(_ * 10)
    println(arr.toBuffer)

    println(arr.sum)
    println(arr.max)
    println(arr.sorted.toBuffer)

  }

}
