package io.findify.databricks

import io.findify.databricks.api._
import io.findify.databricks.calls.{Jobs, Runs}
import org.asynchttpclient.{DefaultAsyncHttpClient, DefaultAsyncHttpClientConfig}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

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

    val mySparkJarTask = Some(SparkJarTask(
      jar_uri = Some("dbfs:/FileStore/jars/53103934_a7f2_47ec_918e_5d2eb7b60cad-job_manager_3_0-1f476.jar"),
      main_class_name = Some("com.example.Main")
    ))

    val myExistingCluster = Some("1109-190151-surer305")

    val myEmailNotifications = Notifications(Some(List("bgxavier@gmail.com")))

    val mySubmit = Submit(
      run_name=Some("teste"),
      existing_cluster_id = myExistingCluster,
      spark_jar_task = mySparkJarTask,
      libraries = myLibraries
    )

    val client = new DefaultAsyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().setCompressionEnforced(true).setKeepAlive(true).build())

    val runs = new Runs(auth, client)
    val myRun = runs.submit(mySubmit)
    val myWait = Await.result(myRun,Duration.Inf)
    println(myWait)
  }
}
