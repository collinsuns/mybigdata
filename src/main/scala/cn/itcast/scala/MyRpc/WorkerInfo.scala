package cn.itcast.scala.MyRpc

/**
  * Created by jh on 2017/7/9.
  */
class WorkerInfo(val id: String, val memory: Int, val cores: Int) {

  //TODO 上一次心跳
  var lastHeartbeatTime : Long = _
}