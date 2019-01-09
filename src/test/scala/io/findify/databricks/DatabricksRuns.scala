package io.findify.databricks

import io.findify.databricks.api._
import io.findify.databricks.calls.{Jobs, Runs}
import org.asynchttpclient.{DefaultAsyncHttpClient, DefaultAsyncHttpClientConfig}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object DatabricksRuns {
  def main(args: Array[String]): Unit = {
    val username = "token"
    val password = ""
    val endpoint = "eastus2.azuredatabricks.net"

    val auth: Auth = Auth(endpoint, username, password)
    val databricks = new Databricks(auth)

    val myLibraries = Some(List(
      Library(jar = Some(s"dbfs:/FileStore/jars/job-manager-3.6-SNAPSHOT.jar")),
      Library(jar = Some(s"dbfs:/FileStore/jars/retail-transformations-1.1-SNAPSHOT.jar"))
    ))

    // walgreens
    // 20181226124315114walgreens8lxae19ccfd0fb959930a31

    // aesportiva
    // "20180808113910024aesportiva2lr6f4c895638c01def9a5

    val myParameters = Some(List(
      "20181226124315114walgreens8lxae19ccfd0fb959930a31",
      "walgreens",
      "1533728350023",
      "true",
      "false"
    ))

    val mySparkJarTask = Some(SparkJarTask(
      main_class_name = Some(s"com.neogrid.atena.jobmanager.StartJobManager"),
      parameters = myParameters
    ))

    val myExistingCluster = Some(s"1130-134746-leapt425")

    val myEmailNotifications = Notifications(Some(List(s"bgxavier@gmail.com")))


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
