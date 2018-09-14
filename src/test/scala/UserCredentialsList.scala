/**
  *************************************************

   NAME:  UserCredentials
   AUTHOR: Yogesh Ruperai
   DATE: 12/09/2018

  DESCRIPTION: This class is used to obtain user credentials based upon
                source system name
  *************************************************
  **/

package PerfUserCredentials

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

class UserCredentials extends Simulation
{

  def userCredentials: Map[String, List[(String)]] = Map(
    "AAD"  	      ->	List("deber.ECAAD.sv","3Fwt6FTg"),
    "AIC"	        ->	List("deber.EC-AIC.sv","C4V5hgf!4"),
    "ARTS"	      ->	List("deber.EC-ARTS.sv","Q2W3asd!5"),
    "ASPEN"       ->	List("deber.EC-aspen.sv","Z7U8hjk!2"),
    "ASUPT"	      ->	List("deber.ECasuptlNP.sv","28QWiH8t1J889fNwYYygp"),
    "AUDITION"	  ->	List("deber.ECaudition.sv","uP5dBM79"),
    "BITMAX"      ->	List("deber.ECbitmax.sv","7Rtd!Mk47"),
    "CANOPUS" 	  ->	List("deber.ECcanopus.sv","v5B3wyZp"),
    "CARO"        ->	List("deber.EC-Caro.sv","X2J3tkl!1"),
    "CC"	        ->	List("deber.EC-CC.sv","jZedfr5e"),
    "CLA"	        ->	List("deber.ECcla.sv","L8rK!y93x"),
    "DART"	      ->	List("deber.ECdart.sv","jTdgje4q"),
    "DIGS"	      ->	List("deber.ECdigs.sv","46fvtQ89xQW"),
    "DLAKE"	      ->	List("deber.EC-dlake.sv","hqpde2a"),
    "GPS"	        ->	List("deber.ECgps.sv","73KwBx9mR"),
    "GRS"	        ->	List("deber.ECgrs.sv","uj85ftPZ81Q"),
    "HITS"	      ->	List("deber.ECHiTS.sv","8Def4WSq"),
    "MDS"	        ->	List("deber.ECmdsvc.sv","hZjuhg4i"),
    "MEDIAP"	    ->	List("deber.ECmediap.sv","5hPQ!nmTg"),
    "MUSGAD"	    ->	List("deber.ECmusgadNP.sv","Fg4hjo947Hqcj615RRQAA"),
    "OHI"	        ->	List("deber.EC-OHI.sv","F6F7vbn!9"),
    "PILOT"	      ->	List("deber.EC-Pilot.sv","pKrtzg2q"),
    "PROXY"	      ->	List("deber.ECproxy.sv","D158bc4KtqZ6eTusX63XG"),
    "PUBSUB"	    ->	List("deber.EC-PubSub.sv","X3C4ert!2"),
    "RGTHB"	      ->	List("deber.EC-rgthb.sv","kPzhuj7a"),
    "SDC"	        ->	List("deber.ECSDC.sv","iEwfgz1a"),
    "STDHUB"	    ->	List("deber.EC-stdHb.sv","T6Z7fgh!7"),
    "ARTIFACTS"	  ->	List("deber.ES-Artifacts.sv","G6D3sgtQ1"),
    "INTERNAL"	  ->	List("es-internal","password"))

  def getUsername(pSource: String) : String =
  {
    userCredentials(pSource)(0)
  }

  def getPassword(pSource: String) : String =
  {
    userCredentials(pSource)(1)
  }

}




