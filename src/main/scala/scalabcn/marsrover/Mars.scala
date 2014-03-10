package scalabcn.marsrover

import akka.actor.{ActorRef, Actor}
import akka.event.LoggingReceive
import scalabcn.marsrover.MarsSatellite.AbortMission

object MarsSatellite{
case object AbortMission
}

class MarsSatellite(rover:ActorRef) extends Actor{

  override def receive = LoggingReceive {
    case AbortMission =>
      rover ! SelfDestruct
      context.stop(self)
  }
}

case object SelfDestruct

class MarsRover extends Actor{
  override def receive = LoggingReceive {
    case SelfDestruct => context.stop(self)
  }

}