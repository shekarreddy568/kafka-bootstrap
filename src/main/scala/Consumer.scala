import com.typesafe.scalalogging.LazyLogging
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.KafkaConsumer

import java.util
import KafkaUtils._
import MinioUtils._

import java.io.{BufferedWriter, FileWriter}
import java.time.Duration

object Consumer extends LazyLogging {

  def main(args: Array[String]): Unit = {

    if(args.length < 8) {
      logger.error("Please pass all the required parameters")
      throw new Exception("Please pass all the required parameters")
    }
    logger.info("starting consumer application.......")

    val bootstrapServers = args(0)
    val userName         = args(1)
    val password         = args(2)
    val topic            = args(3)
    val minioHost        = args(4)
    val minioUserName    = args(5)
    val minioPassWord    = args(6)
    val minioBucket      = args(7)

    try {

      val consumerProps = getConsumerProperties(bootstrapServers, userName, password)
      val consumer      = new KafkaConsumer[String, GenericRecord](consumerProps)
      consumer.subscribe(util.Collections.singletonList(topic))

      val minioClient = createMinioClient(minioHost, minioUserName, minioPassWord)
      createBucket(minioClient, minioBucket)

      while(true) {
        val records = consumer.poll(Duration.ofMillis(2000))
        val it      = records.iterator()

        while(it.hasNext) {

          val record   = it.next()
          val fileName = s"topic-$topic-partition-${record.partition()}-offset-${record.offset()}.bytes"
          logger.info("key: " + record.key() + " , " + "value: " + record.value())

          val buffWriter = new BufferedWriter(new FileWriter(s"/tmp/$fileName"))
          buffWriter.write(record.value() + System.lineSeparator())
          buffWriter.flush()
          uploadFilesToBucket(minioClient, minioBucket, fileName)
        }

      }
    } catch {
      case e: Exception => logger.error(e.getMessage)
    }
  }

}
