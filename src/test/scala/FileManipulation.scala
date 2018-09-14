
/**
  *************************************************

   NAME:  FileManipulation
   AUTHOR: Yogesh Ruperai
   DATE: 12/09/2018

  DESCRIPTION: This contains two functions to store file locations in an
              array and obtain a list of folders.

  *************************************************
  **/

package PerfFileManipulation

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

object FileManipulation extends Simulation
{

  def getListOfFiles(dir: String): List[File] =
  {
    val d = new File(dir)
    d.listFiles.filter(_.isFile).toList
  }

  def getListOfFolders(dir: String): List[File] =
  {
    val d = new File(dir)
    d.listFiles.toList
  }

}



