package cn.itcast.scala

/**
  * Created by jh on 2017/6/29.
  */
object ImmutListDemo {

  def main(args: Array[String]): Unit = {
    val list1 = List(1, 2, 3)

    val list2 = 0 :: list1
    val list3 = list1.:: (0)
    val list4 = 0 +: list1
    val list5 = list1.+:(0)

    val list6 = list1 :+ 3

    val list0 = List(4, 5, 6)
    val list7 = list1 ++ list0

    val list8 = list1 ++: list0

    val list9 = list1.:::(list0)



    println(list9)
  }

}
