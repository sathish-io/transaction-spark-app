package com.sathish.spark.app

import java.text.SimpleDateFormat

import org.apache.spark.sql._
import org.apache.spark.{SparkConf, SparkContext}

case class Transaction(id: Long, txnDate: String, acctNum: Long, merchantCode: Long, zip: Int, amt: Double, desc: String)

object TxnSparkApp extends App {

  println("App Started")

  val file = args(0)
  println(s"Input file name: $file")

  def dateConverter(raw: String): String = raw //formatter.parse(raw.trim)
  def amtConverter(raw: String): Double = raw.substring(1, raw.size).trim.toDouble

  val conf = new SparkConf().setAppName("Txn Spark App")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)
  import sqlContext.implicits._

  val txnData = sc.textFile(file,  10).cache()

  val formatter = new SimpleDateFormat("MM-dd-yyyy")

  val txns = txnData.map(_.split(",")).map(t =>
    Transaction( t(0).trim.toLong, dateConverter(t(1)), t(2).trim.toLong, t(3).trim.toLong, t(4).trim.toInt, amtConverter(t(5)), t(6) ))
    .toDF

  txns.registerTempTable("transactions")
  val smalltxns = sqlContext.sql("SELECT amt FROM transactions WHERE amt < 2.0")
  smalltxns.collect().foreach(println)

  sc.stop()
  println("App Ended")

}
