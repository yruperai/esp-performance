/**
  *************************************************

   NAME:  MAIN ABSTRACT PEAK LOAD TEST
   AUTHOR: Yogesh Ruperai
   DATE: 12/09/2018

  DESCRIPTION: Main Abstract Peak Load

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

class Main_PeakLoad extends Simulation
{
  //****************************************
  //                          (        Muliples of 10      )  (     Muliples of 50       )
  //Throughtput values can be 10,20,30,40,50,60,70,80,90,100, 150,200,250,300,350,400..etc
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
  var i = 0

  load.load(cThroughputPublish,cThroughputRetrieve,cTestDurationSeconds,cRampUp,cThroughputDataLoad,cDurationDataLoad)

  setUp(
    load.dataLoad.dataLoadExtraSmallScenario.inject(atOnceUsers(cDataLoadUser)),
    load.dataLoad.dataLoadSmallScenario.inject(nothingFor(3 minutes), atOnceUsers(cDataLoadUser)),
    load.dataLoad.dataLoadMediumScenario.inject(nothingFor(6 minutes), atOnceUsers(cDataLoadUser)),
    //load.dataLoad.dataLoadLargeScenario.inject(nothingFor(9 minutes), atOnceUsers(cDataLoadUser)),
    load.loadTest.extraSmallScenario.inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cExtraSmallRetrieveScenario.gUsers) over (cRampUp seconds)),
    load.loadTest.smallScenario.inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cSmallRetrieveScenario.gUsers) over (cRampUp seconds)),
    load.loadTest.mediumScenario.inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMediumRetrieveScenario.gUsers) over (cRampUp seconds)),
    //load.loadTest.largeScenario.inject(nothingFor(delay seconds), rampUsers(MasterSheet.cLargeMessageUsers*load.loadTest.cLargeScenario.gUserMuliplier) over (cRampUp seconds)),
    load.loadTest.extraSmallRetrieveScenario.inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cExtraSmallRetrieveScenario.gUsers) over (cRampUp seconds)),
    load.loadTest.smallRetrieveScenario.inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cSmallRetrieveScenario.gUsers) over (cRampUp seconds)),
    load.loadTest.mediumRetrieveScenario.inject(nothingFor(delay+RampUp.getNextRampUp seconds), rampUsers(load.loadTest.cMediumRetrieveScenario.gUsers) over (cRampUp seconds))
  ).protocols(http).maxDuration(duration seconds)

}

