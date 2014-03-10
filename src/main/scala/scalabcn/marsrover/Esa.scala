package scalabcn.marsrover

import akka.actor.{Terminated, Props, Actor}
import akka.event.LoggingReceive
import scalabcn.marsrover.MarsSatellite.{AbortMission, GoToMars}

class Esa extends Actor {

  val rover = context.actorOf(Props[MarsRover], "mars-rover")
  val satellite = context.actorOf(Props(classOf[MarsSatellite], rover), "mars-satellite")
  context.watch(satellite)
  satellite ! GoToMars
  satellite ! AbortMission

  override def receive = LoggingReceive {
    case Terminated(_) =>
      context.stop(self)
  }
}

object Earth {
  def main(args: Array[String]): Unit = {
    akka.Main.main(Array(classOf[Esa].getName))
  }

}