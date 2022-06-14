import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.connect.json.JsonDeserializer
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.serialization.StringDeserializer

import java.util.Properties

object KafkaUtils extends LazyLogging {

  def getProperties(bootstrapServers: String, userName: String, password: String): Properties = {
    val props: Properties = new Properties
    props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    props.put(AdminClientConfig.CLIENT_ID_CONFIG, "test_admin_client")
    props.put(SaslConfigs.SASL_MECHANISM, "PLAIN")
    props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT")
    props.put(
      SaslConfigs.SASL_JAAS_CONFIG,
      s"""org.apache.kafka.common.security.plain.PlainLoginModule required username="$userName" password="$password";"""
    )
    props
  }

  def getConsumerProperties(bootstrapServers: String, userName: String, password: String): Properties = {
    val props: Properties = new Properties
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "test_consumer")
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "test_cg")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getCanonicalName)
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[JsonDeserializer].getCanonicalName)
//    props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT")
//    props.put(SaslConfigs.SASL_MECHANISM, "PLAIN")
//    props.put(
//      SaslConfigs.SASL_JAAS_CONFIG,
//      s"""org.apache.kafka.common.security.plain.PlainLoginModule required username="$userName" password="$password";"""
//    )
    props

  }

}
