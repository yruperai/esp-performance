/**
  *************************************************

   NAME:  MAIN PROD PEAK LOAD TEST
   AUTHOR: Yogesh Ruperai
   DATE: 12/09/2018

  DESCRIPTION: Main Prod Peak Load

  *************************************************
  **/


import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.io.Source
import java.nio.file.Files
import java.nio.file.Paths
import io.gatling.core.body
import io.gatling.core.feeder
import scala.concurrent.duration._
import PerfMasterSheet._
import PerfLoad._
import PerfInjector._
import PerfRampUp._


class Main_ProdPeakLoad extends Simulation
{
  val cFullThroughputPublish : Int = 30
  val cFullThroughputRetrieve   : Int = 10
  //*****************************************
  val cRampUp               : Int = 90
  val cTestDurationSeconds  : Int = 900 + cRampUp
  //***************************************

  //Don't Change these Variables
  var delay                 : Int = 725
  val cThroughputDataLoad   : Int = 10
  val cDataLoadUser         : Int = 1
  val cDurationDataLoad     : Int = 180
  val duration                    = delay  + cTestDurationSeconds
  RampUp.rampUpCalculator(cRampUp)
  val cThroughputPublish    : Int = Injector.splitByInjectors(cFullThroughputPublish)
  val cThroughputRetrieve    : Int = Injector.splitByInjectors(cFullThroughputRetrieve)

  var load  =  Load



  load.load(cThroughputPublish,cThroughputRetrieve,cTestDurationSeconds,cRampUp,cThroughputDataLoad,cDurationDataLoad)

  setUp(
    load.dataLoad.dataLoadExtraSmallScenario.inject(atOnceUsers(cDataLoadUser)),
    load.dataLoad.dataLoadSmallScenario.inject(nothingFor(3 minutes), atOnceUsers(cDataLoadUser)),
    load.dataLoad.dataLoadMediumScenario.inject(nothingFor(6 minutes), atOnceUsers(cDataLoadUser)),
    //load.dataLoad.dataLoadLargeScenario.inject(nothingFor(9 minutes), atOnceUsers(cDataLoadUser)),
    load.loadTest.extraSmallRetrieveScenario.inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cExtraSmallRetrieveScenario.gUsers) over (cRampUp seconds)),
    load.loadTest.smallRetrieveScenario.inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cSmallRetrieveScenario.gUsers) over (cRampUp seconds)),
    load.loadTest.mediumRetrieveScenario.inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMediumRetrieveScenario.gUsers) over (cRampUp seconds)),
    //load.loadTest.largeRetrieveScenario.inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cLargeRetrieveScenario.gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("resourcelocal").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("resourcelocal").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("releaselocal").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("releaselocal").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("masterreleasenotificationmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("masterreleasenotificationmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("worklocal").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("worklocal").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("resourceprice").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("resourceprice").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("releaseprice").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("releaseprice").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("financialdataupc").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("financialdataupc").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("resourcerights").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("resourcerights").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("financialdataisrc").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("financialdataisrc").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("r2releasenotificationmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("r2releasenotificationmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("dealsupdatenotificationmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("dealsupdatenotificationmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("digitalassetupdatenotificationmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("digitalassetupdatenotificationmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("singleresourcenotificationmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("singleresourcenotificationmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("trackrights").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("trackrights").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("digitalassetmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("digitalassetmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("globalresourcepricemessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("globalresourcepricemessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("resource").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("resource").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("globalreleasepricemessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("globalreleasepricemessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("release").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("release").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("translation").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("translation").gUsers) over (cRampUp seconds)),
    // load.loadTest.MessageArchiveScenario("releaseschedulev2").inject(nothingFor(delay seconds), rampUsers(1) over (cRampUp seconds)).throttle(reachRps((cThroughputPublish*MasterSheet.prodMessageDetails("resourcelocal").gLoadRatio).asInstanceOf(Int)) in (cRampUp seconds),holdFor(duration minutes)),
    load.loadTest.MessageArchiveScenario("taggedentitymessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds),rampUsers(load.loadTest.cMessageArchiveScenario("taggedentitymessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("supplychainstatusmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("supplychainstatusmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("segment").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("segment").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("releasestatusnotification").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("releasestatusnotification").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("releaseorderednotification").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("releaseorderednotification").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("physicalassetmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("physicalassetmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("billofmaterialsv6").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("billofmaterialsv6").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("billofmaterials").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("billofmaterials").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("jpreleaselan").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("jpreleaselan").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("releaselegalrights").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("releaselegalrights").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("work").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("work").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("filemanifestmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("filemanifestmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("deliveryreleasenotificationmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("deliveryreleasenotificationmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("physicalreleaseschedulev3").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("physicalreleaseschedulev3").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("physicalreleaseschedule").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("physicalreleaseschedule").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("releaserights").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("releaserights").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("usphysicalreleaseschedule").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("usphysicalreleaseschedule").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("project").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("project").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("talentname").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("talentname").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("talent").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("talent").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("nationalreleaseschedule").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("nationalreleaseschedule").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("externalkey").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("externalkey").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("fulfillmentrequestmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("fulfillmentrequestmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("fulfillmentresponsemessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("fulfillmentresponsemessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("dealoperatingparametersmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("dealoperatingparametersmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("grsresourcerightsload").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("grsresourcerightsload").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("jpresource").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("jpresource").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("northamericancopyrightclearancemessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("northamericancopyrightclearancemessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("ingestrequestmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("ingestrequestmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("ingestresponsemessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("ingestresponsemessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("artistrosterproject").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("artistrosterproject").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("partstracking").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("partstracking").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("physicaltrackedparts").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("physicaltrackedparts").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("workflow").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("workflow").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("filemanifestgroupmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("filemanifestgroupmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("projectrights").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("projectrights").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("territorialrights").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("territorialrights").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("authorization").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("authorization").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("publishingcompany").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("publishingcompany").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("repertoireresourcenotificationresponsemessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("repertoireresourcenotificationresponsemessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("repertoireresourcenotification").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("repertoireresourcenotification").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("partnerprofilemessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("partnerprofilemessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("artistrights").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("artistrights").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("panddmarketingterms").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("panddmarketingterms").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("panddreleaseschedule").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("panddreleaseschedule").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("grscontractrightsload").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("grscontractrightsload").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("audioregistrationresponsemessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("audioregistrationresponsemessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("audioregistrationresponsemessagev11").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("audioregistrationresponsemessagev11").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("audioregistrationrequestmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("audioregistrationrequestmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("deal").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("deal").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("pccompany").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("pccompany").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("envelope").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("envelope").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("taxonomydefinitionmessage").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("taxonomydefinitionmessage").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("entitylabel").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("entitylabel").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("location").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("location").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("grstrackrightsload").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("grstrackrightsload").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("company").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("company").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("financiallabel").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("financiallabel").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("divisionlabel").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("divisionlabel").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("label").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("label").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("companydivision").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("companydivision").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("productioncompany").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("productioncompany").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("familycompany").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("familycompany").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("japancompanynotification").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("japancompanynotification").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("legalentity").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("legalentity").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("sai_workflow").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("sai_workflow").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("reportingpartner").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("reportingpartner").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("collapsedrepertoire").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("collapsedrepertoire").gUsers) over (cRampUp seconds)),
    load.loadTest.MessageArchiveScenario("dataaccessgroup").inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMessageArchiveScenario("dataaccessgroup").gUsers) over (cRampUp seconds))
  ).protocols(http).maxDuration(duration seconds)

  //.protocols(http)
  /*throttle(
    reachRps(30) in (300 seconds),
    holdFor(15 minutes)
    //jumpToRps(50),
    //holdFor(2 hours)
  )*/



}

