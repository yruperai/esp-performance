/**
  *************************************************

   NAME:  PerfProdMessage
   AUTHOR: Yogesh Ruperai
   DATE: 12/09/2018

  DESCRIPTION: This class represents a single prod message and its details i.e. Source and load Ratio
  *************************************************
  **/

package PerfProdMessage
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

class ProdMessage extends Simulation
{
  var gSource: String = ""
  var gLoadRatio: Double  = 0.0

  def prodMessage(pSource: String, pLoadRatio: Double)
  {
    gSource =  pSource
    gLoadRatio =  pLoadRatio
  }

}




