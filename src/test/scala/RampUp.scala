/**
  *************************************************

   NAME:  RampUp
   AUTHOR: Yogesh Ruperai
   DATE: 12/09/2018

  DESCRIPTION: This class establishes the rampup strategy and provides getNextRampUp which is used
                by scenarios to delay start up

  *************************************************
  **/


package PerfRampUp

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
import java.util

object RampUp extends Simulation
{

  var totalMessages: Int = 94
  var sequenceNo :Int = 0
  var stepArray             : Array[Int]    =  new Array[Int](totalMessages)

  def rampUpCalculator(pRampUp: Int)
  {
    var temp=0
    var rampUpStep            : Int = pRampUp/totalMessages
    for (i <- 0 to (totalMessages-1))
    {
      stepArray(i) = temp
      temp = temp + rampUpStep
    }
  }

  def getNextRampUp() : Int =
  {
    var originalValue : Int = sequenceNo
    sequenceNo = sequenceNo + 1
    stepArray(originalValue)

  }

}




