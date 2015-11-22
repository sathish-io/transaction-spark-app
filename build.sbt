//
// http://spark.apache.org/docs/latest/quick-start.html#a-standalone-app-in-scala
//

name := "transaction-spark-app"

version := "1.0"

// 2.11 doesn't seem to work
scalaVersion := "2.10.4"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.5.1",
  "org.apache.spark" %% "spark-sql" % "1.5.1",
  "org.apache.hadoop"  % "hadoop-client" % "2.5.1",
  "com.datastax.spark" %% "spark-cassandra-connector" % "1.5.0-M2",
  "org.apache.spark" %% "spark-streaming" % "1.5.1",
  "org.apache.spark" %% "spark-streaming-kafka" % "1.5.1"
)

net.virtualvoid.sbt.graph.Plugin.graphSettings

fork := true

//enablePlugins(JavaAppPackaging)

//resolvers += "Akka Repository" at "http://repo.akka.io/releases/"
