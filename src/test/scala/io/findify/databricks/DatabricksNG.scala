package io.findify.databricks

import io.findify.databricks.api._
import io.findify.databricks.calls.{Jobs}
import org.asynchttpclient.{DefaultAsyncHttpClient, DefaultAsyncHttpClientConfig}

object DatabricksNGTest {
  def main(args: Array[String]): Unit = {
    val username = "token"
    val password = "dapiba8fbc63008ec2f87c2f03c33269a4b6"
    val endpoint = "eastus2.azuredatabricks.net"

    val auth: Auth = Auth(endpoint, username, password)
    val databricks = new Databricks(auth)

    val myLibraries = Some(List(
      Library(jar = Some("dbfs:/FileStore/jars/53103934_a7f2_47ec_918e_5d2eb7b60cad-job_manager_3_0-1f476.jar"))
    ))

    val mySparkJarTask = Some(JarTask(
      jar_uri = Some("53103934_a7f2_47ec_918e_5d2eb7b60cad-job_manager_3_0-1f476.jar"),
      main_class_name = "com.example.Main"
    ))

    val myExistingCluster = Some("1109-190151-surer305")

    val myEmailNotifications = Notifications(Some(List("bgxavier@gmail.com")))

    val new_job = Job(
      "teste",
      existing_cluster_id=myExistingCluster,
      libraries = myLibraries,
      spark_jar_task = mySparkJarTask,
      email_notifications = myEmailNotifications
    )

    val client = new DefaultAsyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().setCompressionEnforced(true).setKeepAlive(true).build())

    val jobs = new Jobs(auth, client)
    jobs.create(new_job)

  }
}
