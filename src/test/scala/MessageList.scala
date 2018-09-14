/**
  *************************************************

   NAME:  MessageList
   AUTHOR: Yogesh Ruperai
   DATE: 12/09/2018

  DESCRIPTION: MessageList represents one array of prod messages, e.g. R2ReleaseNotificationMessage
  *************************************************
  **/

package PerfMessageList

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import java.io.File
import scala.io.Source
import java.nio.file.Files
import java.nio.file.Paths
import io.gatling.core.body
import io.gatling.core.feeder
import PerfMasterSheet.MasterSheet
import scala.concurrent.duration._
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Random
import PerfFileManipulation._

class MessageList extends Simulation
{
  var gMessageLocation : String = ""

  object getData
  {
    var cProdMessageFiles = FileManipulation.getListOfFiles(gMessageLocation)
    val cProdMessageCount = cProdMessageFiles.length
  }

  def messageList(pMessageLocation : String)
  {
    gMessageLocation = pMessageLocation
    getData
  }

}

