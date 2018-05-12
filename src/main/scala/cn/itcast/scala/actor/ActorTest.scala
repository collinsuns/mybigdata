//package cn.itcast.scala.actor
//
//import scala.actors.Actor
//
//
///**
//  * Scala Actor并发编程
//  * 初识Actor
//  * 2.10.6之前scala用的是scala.actors.Actor
//  * 之后用的是akka.actor.Actor
//  */
//object MyActor1 extends Actor{
//  //重新act方法
//  def act(){
//    for(i <- 1 to 20){
//      println("actor-1 " + i)
//      Thread.sleep(1000)
//    }
//  }
//
//  override def receive = ???
//}
//
//object MyActor2 extends Actor{
//  //重新act方法
//  def act(){
//    for(i <- 1 to 20){
//      println("actor-2 " + i)
//      Thread.sleep(1000)
//    }
//  }
//
//  override def receive = ???
//}
//
//object ActorTest extends App{
//  //启动Actor
//  MyActor1.start()
//  MyActor2.start()
//  println("main")
//}
//
//
