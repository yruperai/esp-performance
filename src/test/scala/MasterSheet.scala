/**
  *************************************************

   NAME:  MasterSheet
   AUTHOR: Yogesh Ruperai
   DATE: 12/09/2018

  DESCRIPTION: This is a Global functions and values accessible by all.

  *************************************************
  **/

package PerfMasterSheet

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import java.io.File
import scala.io.Source
import java.nio.file.Files
import java.nio.file.Paths
import io.gatling.core.body
import io.gatling.core.feeder
import scala.concurrent.duration._
import PerfProdMessage._
import scala.math._

object MasterSheet extends Simulation
{

  //Abstract Message Data
  val cMessageTypeDAUN: String     = "digitalassetupdatenotificationmessage"
  val cAspenUsername: String       = "service.pwd.deber.EC-aspenNP.sv"
  val cAspenPassword: String       = "T5Z6fgh!1"
  val cMessageTypeR2: String       = "r2releasenotificationmessage"
  val cMDSUsername: String         = "deber.ECmdsvc.sv"
  val cMDSPassword: String         = "hZjuhg4i"

  //Endpoint Details
  val cHostName: String            = "esp-nonprd.umusic.com"
  val cCommonPath: String          = "/perf/nginx/rest-facade"
  //val cCommonPath: String          = "/perf/rest-facade"
  val cPathPublish: String         = "/publish-v2/"
  val cPathRetrieve: String        = "/retrieve-v2/"
  val cFormat: String              = "yyyyMMddHHmmss"

  val cExtraSmallScenarioName      = "ExtraSmall"
  val cSmallScenarioName           = "Small"
  val cMediumScenarioName          = "Medium"
  val cLargeScenarioName           = "Large"

  val cMessageLocation: String     = System.getenv("GATLING_ABSTRACT_DATA")//+ "C:\\Gatling\\gatling-charts-highcharts-bundle-2.3.1\\AbstractMessages"
val cProdRootMessageLocation: String     = System.getenv("GATLING_PROD_DATA") //"C:\\YogeshRuperai\\Messages\\"

  println("Abstract Data Location: " + cMessageLocation)
  println("Prod Data Location: " + cProdRootMessageLocation)

  var prodMessageDetails = Map[String, ProdMessage]()

  val cExtraSmallMessageUsers: Int = 6
  val cSmallMessageUsers: Int      = 4
  val cMediumMessageUsers: Int     = 2
  val cLargeMessageUsers: Int      = 1

  def getProdThroughput(msgType: String, cThroughputPublish: Int) : Double =
  {
    cThroughputPublish*MasterSheet.prodMessageDetails(msgType).gLoadRatio
  }

  var AbstractMessageDetails : Map[String, Double] = Map(
    cExtraSmallScenarioName -> 0.5,
    cSmallScenarioName  -> 0.4,
    cMediumScenarioName ->  0.08,
    cLargeScenarioName -> 0.02)

  def buildProdMessages(): Unit =
  {
    prodMessageDetails("resourcelocal").prodMessage("MDS",0.26449)
    prodMessageDetails("releaselocal").prodMessage("MDS",0.16981)
    prodMessageDetails("masterreleasenotificationmessage").prodMessage("OHI",0.14284)
    prodMessageDetails("worklocal").prodMessage("MDS",0.08981)
    prodMessageDetails("resourceprice").prodMessage("GPS",0.05781)
    prodMessageDetails("releaseprice").prodMessage("GPS",0.03726)
    prodMessageDetails("financialdataupc").prodMessage("MDS",0.03664)
    prodMessageDetails("resourcerights").prodMessage("GRS",0.02725)
    prodMessageDetails("financialdataisrc").prodMessage("MDS",0.02500)
    prodMessageDetails("r2releasenotificationmessage").prodMessage("MDS",0.02500)
    prodMessageDetails("dealsupdatenotificationmessage").prodMessage("OHI",0.01858)
    prodMessageDetails("digitalassetupdatenotificationmessage").prodMessage("ASPEN",0.01763)
    prodMessageDetails("singleresourcenotificationmessage").prodMessage("MDS",0.01472)
    prodMessageDetails("trackrights").prodMessage("GRS",0.01161)
    prodMessageDetails("digitalassetmessage").prodMessage("ASPEN",0.00749)
    prodMessageDetails("globalresourcepricemessage").prodMessage("GPS",0.00738)
    prodMessageDetails("resource").prodMessage("MDS",0.00638)
    prodMessageDetails("globalreleasepricemessage").prodMessage("GPS",0.00524)
    prodMessageDetails("release").prodMessage("MDS",0.00460)
    prodMessageDetails("translation").prodMessage("MDS",0.00441)
    //prodMessageDetails("releaseschedulev2").prodMessage("DIGS",0.00361)
    prodMessageDetails("taggedentitymessage").prodMessage("OHI",0.00200)
    prodMessageDetails("supplychainstatusmessage").prodMessage("OHI",0.00160)
    prodMessageDetails("segment").prodMessage("OHI",0.00159)
    prodMessageDetails("releasestatusnotification").prodMessage("OHI",0.00157)
    prodMessageDetails("releaseorderednotification").prodMessage("OHI",0.00155)
    prodMessageDetails("physicalassetmessage").prodMessage("AIC",0.00144)
    prodMessageDetails("billofmaterialsv6").prodMessage("OHI",0.00142)
    prodMessageDetails("billofmaterials").prodMessage("OHI",0.00141)
    prodMessageDetails("jpreleaselan").prodMessage("MDS",0.00121)
    prodMessageDetails("releaselegalrights").prodMessage("DIGS",0.00112)
    prodMessageDetails("work").prodMessage("MDS",0.00099)
    prodMessageDetails("deliveryreleasenotificationmessage").prodMessage("OHI",0.00096)
    prodMessageDetails("physicalreleaseschedulev3").prodMessage("OHI",0.00094)
    prodMessageDetails("physicalreleaseschedule").prodMessage("OHI",0.00090)
    prodMessageDetails("releaserights").prodMessage("GRS",0.00090)
    prodMessageDetails("filemanifestmessage").prodMessage("DLAKE",0.00071)
    prodMessageDetails("usphysicalreleaseschedule").prodMessage("OHI",0.00039)
    prodMessageDetails("project").prodMessage("MDS",0.00038)
    prodMessageDetails("talentname").prodMessage("MDS",0.00027)
    prodMessageDetails("talent").prodMessage("MDS",0.00027)
    prodMessageDetails("nationalreleaseschedule").prodMessage("OHI",0.00023)
    prodMessageDetails("externalkey").prodMessage("MDS",0.00022)
    prodMessageDetails("fulfillmentrequestmessage").prodMessage("ASPEN",0.00021)
    prodMessageDetails("fulfillmentresponsemessage").prodMessage("ASPEN",0.00021)
    prodMessageDetails("dealoperatingparametersmessage").prodMessage("STDHUB",0.00013)
    prodMessageDetails("grsresourcerightsload").prodMessage("MEDIAP",0.00012)
    prodMessageDetails("jpresource").prodMessage("MDS",0.00010)
    prodMessageDetails("northamericancopyrightclearancemessage").prodMessage("CLA",0.00010)
    prodMessageDetails("ingestrequestmessage").prodMessage("STDHUB",0.00009)
    prodMessageDetails("ingestresponsemessage").prodMessage("ASPEN",0.00009)
    prodMessageDetails("artistrosterproject").prodMessage("OHI",0.00009)
    prodMessageDetails("partstracking").prodMessage("OHI",0.00006)
    prodMessageDetails("physicaltrackedparts").prodMessage("OHI",0.00006)
    prodMessageDetails("workflow").prodMessage("OHI",0.00006)
    prodMessageDetails("filemanifestgroupmessage").prodMessage("GRS",0.00005)
    prodMessageDetails("projectrights").prodMessage("OHI",0.00005)
    prodMessageDetails("territorialrights").prodMessage("OHI",0.00004)
    prodMessageDetails("authorization").prodMessage("OHI",0.00003)
    prodMessageDetails("resourcedurationupdatenotification").prodMessage("MDS",0.00003)
    prodMessageDetails("publishingcompany").prodMessage("DLAKE",0.00003)
    prodMessageDetails("repertoireresourcenotificationresponsemessage").prodMessage("MDS",0.00003)
    prodMessageDetails("repertoireresourcenotification").prodMessage("STDHUB",0.00003)
    prodMessageDetails("partnerprofilemessage").prodMessage("OHI",0.00002)
    prodMessageDetails("artistrights").prodMessage("GRS",0.00002)
    prodMessageDetails("panddmarketingterms").prodMessage("MEDIAP",0.00002)
    prodMessageDetails("panddreleaseschedule").prodMessage("MEDIAP",0.00002)
    prodMessageDetails("grscontractrightsload").prodMessage("STDHUB",0.00002)
    prodMessageDetails("audioregistrationresponsemessage").prodMessage("MDS",0.00002)
    prodMessageDetails("audioregistrationresponsemessagev11").prodMessage("MDS",0.00002)
    prodMessageDetails("audioregistrationrequestmessage").prodMessage("STDHUB",0.00002)
    prodMessageDetails("deal").prodMessage("OHI",0.00002)
    prodMessageDetails("pccompany").prodMessage("MDS",0.00001)
    prodMessageDetails("envelope").prodMessage("OHI",0.00001)
    prodMessageDetails("taxonomydefinitionmessage").prodMessage("OHI",0.00001)
    //prodMessageDetails("dealcontractuallanguagemessage").prodMessage("STDHUB",0.00001)
    //prodMessageDetails("dealproductroyaltyprovisionsmessage").prodMessage("STDHUB",0.00001)
    prodMessageDetails("entitylabel").prodMessage("OHI",0.00001)
    prodMessageDetails("location").prodMessage("MDS",0.00001)
    prodMessageDetails("grstrackrightsload").prodMessage("MEDIAP",0.00001)
    prodMessageDetails("company").prodMessage("OHI",0.00001)
    prodMessageDetails("financiallabel").prodMessage("OHI",0.00001)
    prodMessageDetails("divisionlabel").prodMessage("OHI",0.00001)
    prodMessageDetails("label").prodMessage("OHI",0.00001)
    prodMessageDetails("companydivision").prodMessage("OHI",0.00001)
    //prodMessageDetails("hitstranslationmessage").prodMessage("HITS",0.00001)
    prodMessageDetails("productioncompany").prodMessage("MDS",0.00001)
    prodMessageDetails("familycompany").prodMessage("OHI",0.00001)
    prodMessageDetails("japancompanynotification").prodMessage("OHI",0.00001)
    prodMessageDetails("legalentity").prodMessage("OHI",0.00001)
    prodMessageDetails("sai_workflow").prodMessage("OHI",0.00001)
    prodMessageDetails("reportingpartner").prodMessage("OHI",0.00001)
    prodMessageDetails("dataaccessgroup").prodMessage("OHI",0.00001)
    //prodMessageDetails("grsreleaserightsload").prodMessage("MEDIAP",0.00001)
    prodMessageDetails("collapsedrepertoire").prodMessage("MDS",0.00001)
  }

}

