package com.example

import java.io.File
import java.io.PrintWriter

import scala.io.Source
import com.example.config.ConfigUtils
import pureconfig.generic.auto._
import com.typesafe.scalalogging.LazyLogging

import scala.util.Try

// config for input and output paths
case class DataConfig(celsiusdata: String, fahrenheitdata: String)

object Problem1 extends LazyLogging {

  // function to convert celsius to fahrenheit
  def celsiusToFahrenheit(temp: Double): Double = 32.0 + (temp * 9.0 / 5.0)

  /**
    * convert raw string to Double ignoring
    * 1. any lines that start with '#'
    * 2. conversion errors
    * @param line
    * @return
    */
  def stringToTemperature(line: String): Option[Double] =
    if (line.startsWith("#"))
      None
    else {
      Try {
        line.toDouble
      }.toOption
    }

  def main(args: Array[String]) = {
    // get file location from config
    val fileConfig = ConfigUtils.loadAppConfig[DataConfig]("com.example.problem1")
    logger.info(s"reading from ${fileConfig.celsiusdata} and writing to ${fileConfig.fahrenheitdata}")

    val fahrenheitLines: List[Double] =
      readFileLines(fileConfig.celsiusdata)                                   // 1. read input file as lines
      .map(stringToTemperature)                                               // 2. convert strings to Double
      .flatten                                                                // 3. ignore unprocessed lines
      .map(celsiusToFahrenheit)                                               // 4. convert to fahrenheit

    writeToFile(fileConfig.fahrenheitdata, fahrenheitLines.mkString("\n"))    // 5. write to output file
    logger.info(s"${fahrenheitLines.size} lines converted")                   // 6. log number of converted lines
  }

  // read from file
  def readFileLines(path: String): List[String] =
    Source
      .fromFile(path)
      .getLines()
      .toList

  // write to file
  def writeToFile(path: String, contents: String): Unit = {
    val writer = new PrintWriter(new File(path))
    writer.write(contents)
    writer.close()
  }
}
