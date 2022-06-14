name := "KafkaClients"

version := "0.1"

scalaVersion := "2.13.0"

resolvers ++= Seq(
  "io.confluent" at "https://packages.confluent.io/maven/"
)

libraryDependencies ++= {
  sys.props += "packaging.type" -> "jar"
  Seq(
    "org.apache.kafka"            % "kafka-clients"         % "7.1.1-ccs",
    "io.confluent"                % "kafka-avro-serializer" % "7.1.1",
    "com.typesafe.scala-logging" %% "scala-logging"         % "3.9.5",
    "ch.qos.logback"              % "logback-classic"       % "1.2.11",
    "io.minio"                    % "minio"                 % "8.4.2",
    "org.apache.kafka"            % "connect-json"          % "3.2.0"
  )
}

assembly / assemblyJarName := "kafkaclients.jar"

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case "reference.conf"              => MergeStrategy.concat
  case x                             => MergeStrategy.first
}
