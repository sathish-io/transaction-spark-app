package com.sathish.spark.app

import com.datastax.spark.connector.SomeColumns
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import com.datastax.spark.connector.streaming._

/**
  * Created by skandasa on 11/18/15.
  */
object StreamingApp extends  App {

  val sparkConf = new SparkConf().setAppName("streaming-app1").setMaster("local[*]")
    .set("spark.cassandra.connection.host", "host1")

  val ssc = new StreamingContext(sparkConf, Seconds(10))

  val topics = Set("test")
  val kafkaParams = Map[String, String]("metadata.broker.list" -> "host1:9092")
  val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)

  messages.map(_._2).flatMap((_.split(" "))).map(x => (x, 1L)).reduceByKey(_ + _).map(x => ( System.currentTimeMillis(), x._1, x._2))
    .saveToCassandra("sparkdb", "word_count", SomeColumns("event_time", "word", "total_count"))

  ssc.start()
  ssc.awaitTermination()
}
