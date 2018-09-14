
/**
  *************************************************

   NAME:  Injector
   AUTHOR: Yogesh Ruperai
   DATE: 12/09/2018

  DESCRIPTION: This class establishes the host name and defines a Special Pre-Fix Character

  *************************************************
  **/


package PerfInjector

import PerfMasterSheet._
import PerfMessageScenario._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import java.io.File
import scala.io.Source
import java.nio.file.Files
import java.nio.file.Paths
import io.gatling.core.body
import io.gatling.core.feeder
import scala.concurrent.duration._
import scala.collection._
import MasterSheet._
import scala.math
import PerfMessageList._ //MessageList._
import java.util.Collections
import java.net.InetAddress

object Injector extends Simulation
{

  val listOfInjectors = Array("EC2AMAZ-5ENQFH4", "EC2AMAZ-5ENQFH4")

  def getInjectorName(): String =
  {
    (InetAddress.getLocalHost()).getHostName()
  }

  def splitByInjectors(throughput: Int): Int =
  {
    throughput/listOfInjectors.length
  }

  def getPreFix(injector : String) : Int =
  {
    var i : Int = 0
    while(injector != listOfInjectors(i))
    {
      i = i+1
    }
    i
  }

  val preFixCharacter = getPreFix(getInjectorName())
}



