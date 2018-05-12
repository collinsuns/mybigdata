package cn.itcast.scala.MyRpc

/**
  * Created by jh on 2017/7/9.
  */
trait RemoteMessage extends Serializable

//Worker -> Master
case class RegisterWorker(id: String, memory: Int, cores: Int) extends RemoteMessage

case class Heartbeat(id: String) extends RemoteMessage


//Master -> Worker
case class RegisteredWorker(masterUrl: String) extends RemoteMessage

//Worker -> self
case object SendHeartbeat

// Master -> self
case object CheckTimeOutWorker



