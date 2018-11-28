package io.findify.databricks.api

case class Submit(run_name: Option[String],
                  existing_cluster_id: Option[String] = None,
                  new_cluster: Option[Cluster] = None,
                  notebook_task: Option[NotebookTask] = None,
                  spark_jar_task: Option[SparkJarTask] = None,
                  spark_python_task: Option[SparkPythonTask] = None,
                  spark_submit_task: Option[SparkSubmitTask] = None,
                  timeout_seconds: Option[Int] = None,
                  libraries: Option[List[Library]] = None
                 )

case class SubmitResponse(run_id: Float)