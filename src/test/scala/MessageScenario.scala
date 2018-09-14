/**
  *************************************************

   NAME:  MessageScenario
   AUTHOR: Yogesh Ruperai
   DATE: 12/09/2018

  DESCRIPTION: Message Scenario details, i.e. the main Exec step to send traffic
  *************************************************
  **/

package PerfMessageScenario

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
import PerfMessageList._ //MessageList._
import java.util.Random
import scala.math._
import PerfInjector._

class MessageScenario extends Simulation
{

  var gScenarioName: String = ""
  var gFile: String         = ""
  var gMessageType: String  = ""
  var gThroughput: Int      = 0
  var gDuration: Int        = 0
  var gUsername: String     = ""
  var gPassword: String     = ""
  var gIterations :Int      = 0
  var gPacing: Int          = 0
  var gUserMuliplier: Int   = 0
  var cBound :Int           = 0
  var cPlus :Int            = 0
  var gFiles                = new MessageList
  var gProdFlag :Boolean    = false
  var gUsers : Int          = 0

  var rand = new Random

  def setValues(pScenarioName: String, pMessageType: String, pThroughput: Int, pDuration: Int, pUsername: String, pPassword: String)
  {
    gScenarioName = pScenarioName
    gMessageType = pMessageType
    gThroughput = pThroughput
    gDuration = pDuration
    gUsername = pUsername
    gPassword = pPassword
  }

  def messageScenario(pScenarioName: String, pFile: String, pSize : String, pMessageType: String, pThroughput: Int, pDuration: Int, pUsername: String, pPassword: String)
  {
    setValues(pScenarioName, pMessageType, pThroughput, pDuration, pUsername, pPassword)
    gFile = pFile

    gUserMuliplier = 1

    calculationEngine(pThroughput, MasterSheet.AbstractMessageDetails(pSize), pDuration, 8, pMessageType)

  }

  def messageScenario(pScenarioName: String, pFiles: PerfMessageList.MessageList, pMessageType: String, pThroughput: Int, pDuration: Int, pUsername: String, pPassword: String)
  {
    setValues(pScenarioName, pMessageType, pThroughput, pDuration, pUsername, pPassword)
    gProdFlag = true
    //gUserMuliplier = 1

    gFiles = pFiles

    calculationEngine(pThroughput, MasterSheet.prodMessageDetails(pMessageType).gLoadRatio, pDuration, 8, pMessageType)

  }

  object MessagePublish
  {
    var messagePublishProd = repeat(gIterations, "x") //3000
    {
      exec(session =>
      {
        var a: Int = session.loopCounterValue("x")
        while (a > (gFiles.getData.cProdMessageCount - 1))
        {
          a = a - gFiles.getData.cProdMessageCount
        }
        session.set("dateTime", DateTimeFormatter.ofPattern(MasterSheet.cFormat).format(LocalDateTime.now)).set("messageFile", new String((Files.readAllBytes(Paths.get(gFiles.getData.cProdMessageFiles(a).getPath)))))
      })
        .exec(http(gScenarioName)
          .post("https://" + MasterSheet.cHostName + MasterSheet.cCommonPath + MasterSheet.cPathPublish + gMessageType + "?identifier=" + Injector.preFixCharacter + "${x}" + "&version=${dateTime}" + "111")
          .header("Content-Type", "application/xml")
          .basicAuth(gUsername, gPassword)
          .body(StringBody("${messageFile}")))
        .pause(gPacing milliseconds)

    }

    var messagePublishAbstract = repeat(gIterations, "x") //3000
    {

      exec(session => {
        session.set("dateTime", DateTimeFormatter.ofPattern(MasterSheet.cFormat).format(LocalDateTime.now))
      })

        .exec(http(gScenarioName + ": " + gMessageType)
          .post("https://" + MasterSheet.cHostName + MasterSheet.cCommonPath + MasterSheet.cPathPublish + gMessageType + "?identifier=" + Injector.preFixCharacter + "${x}" + "&version=${dateTime}" + "111")
          .header("Content-Type", "application/xml")
          .basicAuth(gUsername, gPassword)
          .body(StringBody(gFile)))
        .pause(gPacing milliseconds)


    }
  }

  object MessageRetrieve
  {
    var messageRetrieve = repeat(gIterations,"x")
    {
      exec(http(gScenarioName)
        .get("https://" + MasterSheet.cHostName + MasterSheet.cCommonPath + MasterSheet.cPathRetrieve + gMessageType + "?identifier=" + Injector.preFixCharacter + (rand.nextInt(cBound-cPlus) + cPlus) + "&wrap=false")
        .basicAuth(gUsername, gPassword))
        .pause(gPacing milliseconds)
    }
  }


  def calculationEngine(pThroughput: Int, pRatio: Double, pDuration: Int, pResponseTimeMultiplier : Int, pMessageType : String)
  {
    var requiredThroughputSec : Double = pThroughput*pRatio
    gIterations = (requiredThroughputSec*pDuration).toInt
    var responseTimes : Double = pResponseTimeMultiplier*gThroughput
    if (gThroughput > 49)
    {
      responseTimes  = 13.00*gThroughput
    }
    if (gThroughput > 59)
    {
      responseTimes  =  16.00*gThroughput
    }
    var actualThroughputSec : Double = 1000/responseTimes
    gUsers = (requiredThroughputSec/actualThroughputSec).toInt + 1

    var totalThroughput = gUsers * actualThroughputSec
    var perUser = requiredThroughputSec/gUsers
    gPacing = (1000/(perUser)).toInt
    gPacing = (gPacing-responseTimes).toInt  //220
    if (gPacing < 0)
    {
      gPacing =0
    }
    println("Prod Message Details: " + pMessageType + " " + requiredThroughputSec + " " + gUsers + " " + gPacing)

    //The following is used to determine what data to retrieve
    if (gScenarioName == "Retrieve-ExtraSmall")
    {
      cBound = 900
      cPlus  = 721
    }
    else if (gScenarioName == "Retrieve-Small")
    {
      cBound = 720
      cPlus  = 145
    }
    else if (gScenarioName == "Retrieve-Medium")
    {
      cBound = 144
      cPlus  = 6
    }
    else if (gScenarioName == "Retrieve-Large")
    {
      cBound = 5
      cPlus  = 1
    }
  }
}

