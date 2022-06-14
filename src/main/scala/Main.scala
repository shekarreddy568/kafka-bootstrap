//import org.apache.kafka.clients.admin.AdminClient
//import KafkaUtils._
//import com.typesafe.scalalogging.LazyLogging
//
//object Main extends LazyLogging {
//  def main(args: Array[String]): Unit = {
//
//    if (args.length < 3) {
//      logger.error("Please pass all the required parameters (bootstrapServers, userName, password)")
//      throw new Exception("Please pass all the required parameters (bootstrapServers, userName, password)")
//    }
//
//    val bootstrapServers = args(0)
//    val userName = args(1)
//    val password = args(2)
//
//    val adminClient = AdminClient.create(getProperties(bootstrapServers, userName, password))
//
//    val clusterConfig = adminClient.describeCluster()
//    logger.info("cluster metadata.....")
//    clusterConfig.nodes().get().forEach(x => logger.info("id: " + x.id() + ", rack: " + x.rack() + ", listener: " + x.host() + ":" + x.port()))
//
//    val controller = clusterConfig.controller().get().host()
//    logger.info(s"controller: $controller")
//
//    val clusterId = clusterConfig.clusterId().get()
//    logger.info(s"clusterId: $clusterId")
//
//  }
//}