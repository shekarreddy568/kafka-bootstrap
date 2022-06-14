import io.minio.{BucketExistsArgs, MakeBucketArgs, MinioClient, UploadObjectArgs}

object MinioUtils {

  def createMinioClient(host: String, userName: String, passWord: String): MinioClient = {
    MinioClient
      .builder()
      .endpoint(host)
      .credentials(userName, passWord)
      .build()
  }

  def createBucket(client: MinioClient, bucketName: String): Unit = {

    val bucketExists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())
    if(!bucketExists) client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build())
  }

  def uploadFilesToBucket(client: MinioClient, bucket: String, objectName: String): Unit = {

    client.uploadObject(
      UploadObjectArgs.builder
        .bucket(bucket)
        .`object`(objectName)
        .filename(s"/tmp/$objectName")
        .build
    )
  }

}
