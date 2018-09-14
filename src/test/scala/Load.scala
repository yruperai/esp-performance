
/**
  *************************************************

   NAME:  LOAD
   AUTHOR: Yogesh Ruperai
   DATE: 12/09/2018

  DESCRIPTION: This object represents a Load Test built from Abstract messages and Prod.

  *************************************************
  **/

package PerfLoad

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
import PerfUserCredentials._
import PerfProdMessage._
import PerfFileManipulation._

object Load extends Simulation
{

  var gUsersDataLoad: Int                     = 0
  var gThroughputDataLoad: Int                = 0
  var gDurationDataLoad : Int                 = 0
  var gUsersPublish: Int                      = 0
  var gUsersRetrieve: Int                     = 0
  var gThroughputPublish: Int                 = 0
  var gThroughputRetrieve: Int                = 0
  var gTestDurationSeconds: Int               = 0
  var gRampUp: Int                            = 0
  var fileData1: String                       = ""
  var fileData2: String                       = ""
  var fileData3: String                       = ""
  var fileData4: String                       = ""
  var prodMessageList : List[File]            =  FileManipulation.getListOfFolders(MasterSheet.cProdRootMessageLocation)
  var messageArchive : Array[MessageList] = new Array[MessageList](prodMessageList.length) //Array.empty[MessageList]

  def load(pThroughputPublish: Int, pThroughputRetrieve: Int, pTestDurationSeconds: Int, pRampUp: Int, pThroughputDataLoad: Int, pDurationDataLoad: Int)
  {
    val messageFiles = FileManipulation.getListOfFiles(MasterSheet.cMessageLocation)
    gThroughputPublish = pThroughputPublish
    gThroughputRetrieve = pThroughputRetrieve
    gTestDurationSeconds = pTestDurationSeconds
    gRampUp = pRampUp
    gThroughputDataLoad  = pThroughputDataLoad
    gDurationDataLoad  = pDurationDataLoad
    fileData1 = new String(Files.readAllBytes(Paths.get(messageFiles(0).getPath)))
    fileData2 = new String(Files.readAllBytes(Paths.get(messageFiles(1).getPath)))
    fileData3 = new String(Files.readAllBytes(Paths.get(messageFiles(2).getPath)))
    fileData4 = new String(Files.readAllBytes(Paths.get(messageFiles(3).getPath)))
    println("***SETUP PROD SCENARIO DATA***")
    println("******************************")
    println("***LOAD ALL FILES INTO MEMORY***")
    for(i <- 0 to (prodMessageList.length-1))
    {
      messageArchive(i) = new MessageList
      messageArchive(i).messageList(prodMessageList(i).getPath)
      println("LOADED FILES FROM LOCATION: " + prodMessageList(i).getPath)
    }
  }

  object dataLoad
  {
    def dataLoadUsers: Int = 1
    var cDataLoadExtraSmallScenario = new MessageScenario
    var cDataLoadSmallScenario = new MessageScenario
    var cDataLoadMediumScenario = new MessageScenario
    var cDataLoadLargeScenario = new MessageScenario
    var credentials = new UserCredentials
    cDataLoadExtraSmallScenario.messageScenario("DataLoad-ExtraSmall",  fileData1, MasterSheet.cExtraSmallScenarioName, MasterSheet.cMessageTypeDAUN, gThroughputDataLoad, gDurationDataLoad, credentials.getUsername("ASPEN"), credentials.getPassword("ASPEN"))
    cDataLoadSmallScenario.messageScenario("DataLoad-Small",  fileData2, MasterSheet.cSmallScenarioName, MasterSheet.cMessageTypeR2, gThroughputDataLoad, gDurationDataLoad, credentials.getUsername("MDS"), credentials.getPassword("MDS"))
    cDataLoadMediumScenario.messageScenario("DataLoad-Medium",  fileData3, MasterSheet.cMediumScenarioName, MasterSheet.cMessageTypeR2, gThroughputDataLoad, gDurationDataLoad, credentials.getUsername("MDS"), credentials.getPassword("MDS"))
    cDataLoadLargeScenario.messageScenario("DataLoad-Large",  fileData4, MasterSheet.cLargeScenarioName, MasterSheet.cMessageTypeR2, gThroughputDataLoad, gDurationDataLoad, credentials.getUsername("MDS"), credentials.getPassword("MDS"))
    var dataLoadExtraSmallScenario = scenario("DataLoad-ExtraSmall").exec(cDataLoadExtraSmallScenario.MessagePublish.messagePublishAbstract)
    var dataLoadSmallScenario = scenario("DataLoad-Small").exec(cDataLoadSmallScenario.MessagePublish.messagePublishAbstract)
    var dataLoadMediumScenario = scenario("DataLoad-Medium").exec(cDataLoadMediumScenario.MessagePublish.messagePublishAbstract)
    var dataLoadLargeScenario = scenario("DataLoad-Large").exec(cDataLoadLargeScenario.MessagePublish.messagePublishAbstract)
  }

  object loadTest
  {
    var cExtraSmallScenario = new MessageScenario
    var cSmallScenario = new MessageScenario
    var cMediumScenario = new MessageScenario
    var cLargeScenario = new MessageScenario
    var cExtraSmallRetrieveScenario = new MessageScenario
    var cSmallRetrieveScenario = new MessageScenario
    var cMediumRetrieveScenario = new MessageScenario
    var cLargeRetrieveScenario = new MessageScenario
    var MessageArchiveScenario =  Map[String, io.gatling.core.structure.ScenarioBuilder]() //= Collections.emptyMap
  var cMessageArchiveScenario =  Map[String, MessageScenario]()
    var credentials = new UserCredentials


    println("***SETUP MESSAGES***")
    var messageType = ""

    var username = ""
    var password = ""

    for(i <- 0 to (prodMessageList.length-1))
    {
      messageType = prodMessageList(i).getPath.split('\\')(3)
      MasterSheet.prodMessageDetails += messageType -> new ProdMessage
      println("Sucessfully Setup: " + messageType)
    }
    println("Sucessfully Loaded Prod Messages")
    println("***SETUP MESSAGE THROUGHPUT***")
    buildProdMessages()
    println("Sucessfully Setup Message Throughput")
    println("***SETUP MSG SCENARIOS ***")

    cExtraSmallScenario.messageScenario("Publish-" + MasterSheet.cExtraSmallScenarioName, fileData1, MasterSheet.cExtraSmallScenarioName, MasterSheet.cMessageTypeDAUN,  gThroughputPublish, gTestDurationSeconds, credentials.getUsername(MasterSheet.prodMessageDetails("digitalassetupdatenotificationmessage").gSource), credentials.getPassword(MasterSheet.prodMessageDetails("digitalassetupdatenotificationmessage").gSource))
    cSmallScenario.messageScenario("Publish-" + MasterSheet.cSmallScenarioName, fileData2, MasterSheet.cSmallScenarioName, MasterSheet.cMessageTypeR2, gThroughputPublish, gTestDurationSeconds, credentials.getUsername(MasterSheet.prodMessageDetails("r2releasenotificationmessage").gSource), credentials.getPassword(MasterSheet.prodMessageDetails("r2releasenotificationmessage").gSource))
    cMediumScenario.messageScenario("Publish-" + MasterSheet.cMediumScenarioName, fileData3, MasterSheet.cMediumScenarioName, MasterSheet.cMessageTypeR2, gThroughputPublish, gTestDurationSeconds, credentials.getUsername(MasterSheet.prodMessageDetails("r2releasenotificationmessage").gSource), credentials.getPassword(MasterSheet.prodMessageDetails("r2releasenotificationmessage").gSource))
    cLargeScenario.messageScenario("Publish-" + MasterSheet.cLargeScenarioName, fileData4, MasterSheet.cLargeScenarioName, MasterSheet.cMessageTypeR2,  gThroughputPublish, gTestDurationSeconds, credentials.getUsername(MasterSheet.prodMessageDetails("r2releasenotificationmessage").gSource), credentials.getPassword(MasterSheet.prodMessageDetails("r2releasenotificationmessage").gSource))

    cExtraSmallRetrieveScenario.messageScenario("Retrieve-" + MasterSheet.cExtraSmallScenarioName, fileData1, MasterSheet.cExtraSmallScenarioName, MasterSheet.cMessageTypeDAUN,  gThroughputRetrieve, gTestDurationSeconds, credentials.getUsername(MasterSheet.prodMessageDetails("digitalassetupdatenotificationmessage").gSource), credentials.getPassword(MasterSheet.prodMessageDetails("digitalassetupdatenotificationmessage").gSource))
    cSmallRetrieveScenario.messageScenario("Retrieve-" + MasterSheet.cSmallScenarioName, fileData2, MasterSheet.cSmallScenarioName, MasterSheet.cMessageTypeR2, gThroughputRetrieve, gTestDurationSeconds, credentials.getUsername(MasterSheet.prodMessageDetails("r2releasenotificationmessage").gSource), credentials.getPassword(MasterSheet.prodMessageDetails("r2releasenotificationmessage").gSource))
    cMediumRetrieveScenario.messageScenario("Retrieve-" + MasterSheet.cMediumScenarioName, fileData3, MasterSheet.cMediumScenarioName, MasterSheet.cMessageTypeR2, gThroughputRetrieve, gTestDurationSeconds, credentials.getUsername(MasterSheet.prodMessageDetails("r2releasenotificationmessage").gSource), credentials.getPassword(MasterSheet.prodMessageDetails("r2releasenotificationmessage").gSource))
    cLargeRetrieveScenario.messageScenario("Retrieve-" + MasterSheet.cLargeScenarioName, fileData4, MasterSheet.cLargeScenarioName, MasterSheet.cMessageTypeR2,  gThroughputRetrieve, gTestDurationSeconds, credentials.getUsername(MasterSheet.prodMessageDetails("r2releasenotificationmessage").gSource), credentials.getPassword(MasterSheet.prodMessageDetails("r2releasenotificationmessage").gSource))

    for(i <- 0 to (prodMessageList.length-1))
    {
      messageType = prodMessageList(i).getPath.split('\\')(3)
      username = credentials.getUsername(MasterSheet.prodMessageDetails(messageType).gSource)
      password = credentials.getPassword(MasterSheet.prodMessageDetails(messageType).gSource)
      cMessageArchiveScenario += messageType -> new MessageScenario
      cMessageArchiveScenario(messageType).messageScenario("Prod-Publish " + messageType, messageArchive(i), messageType, gThroughputPublish, gTestDurationSeconds, username , password)
      MessageArchiveScenario += messageType -> scenario("Publish-" + messageType).exec(cMessageArchiveScenario(messageType).MessagePublish.messagePublishProd)
      println("Sucessfully Setup Scenario: " + messageType)
    }

    var extraSmallScenario = scenario("Publish-" + MasterSheet.cExtraSmallScenarioName).exec(cExtraSmallScenario.MessagePublish.messagePublishAbstract)
    var smallScenario = scenario("Publish-" + MasterSheet.cSmallScenarioName).exec(cSmallScenario.MessagePublish.messagePublishAbstract)
    var mediumScenario = scenario("Publish-" + MasterSheet.cMediumScenarioName).exec(cMediumScenario.MessagePublish.messagePublishAbstract)
    var largeScenario = scenario("Publish-" + MasterSheet.cLargeScenarioName).exec(cLargeScenario.MessagePublish.messagePublishAbstract)

    var extraSmallRetrieveScenario = scenario("Retrieve-" + MasterSheet.cExtraSmallScenarioName).exec(cExtraSmallRetrieveScenario.MessageRetrieve.messageRetrieve)
    var smallRetrieveScenario = scenario("Retrieve-" + MasterSheet.cSmallScenarioName).exec(cSmallRetrieveScenario.MessageRetrieve.messageRetrieve)
    var mediumRetrieveScenario = scenario("Retrieve-" + MasterSheet.cMediumScenarioName).exec(cMediumRetrieveScenario.MessageRetrieve.messageRetrieve)
    var largeRetrieveScenario = scenario("Retrieve-" + MasterSheet.cLargeScenarioName).exec(cLargeRetrieveScenario.MessageRetrieve.messageRetrieve)

    println("***STARTING TEST NOW***")
    println("************************")

  }

}



