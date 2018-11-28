package io.findify.databricks.api



case class RunsList(runs: List[RunsListEntry], has_more: Boolean)

case class RunsListEntry(job_id: Long,
                         run_id: Long,
                         creator_user_name: Option[String],
                         number_in_job: Long,
                         original_attempt_run_id: Option[Long],
                         state: Option[RunState] = None,
                         schedule: Option[CronSchedule] = None,
                         task: JobTask,
                         cluster_spec: Option[ClusterSpec] = None,
                         cluster_instance: Option[ClusterInstance] = None,
                         overriding_parameters: Option[RunParameters] = None,
                         start_time: Option[Long],
                         setup_duration: Option[Long],
                         execution_duration: Option[Long],
                         cleanup_duration: Option[Long],
                         trigger: Option[String]
)

case class RunState(life_cycle_state: Option[String], result_state: Option[String], state_message: Option[String])

case class JobTask(notebook_task: Option[NotebookTask] = None, spark_jar_task: Option[SparkJarTask] = None, spark_python_task: Option[SparkPythonTask] = None, spark_submit_task: Option[SparkSubmitTask] = None)

case class NotebookTask(notebook_path: String, base_parameters: Option[List[(String, String)]] = None)

case class SparkJarTask(jar_uri: Option[String] = None, main_class_name: Option[String] = None, parameters: Option[List[String]] = None)

case class SparkPythonTask(python_file: String, parameters: Option[List[String]] = None)

case class SparkSubmitTask(parameters: Option[List[String]] = None)

case class ClusterSpec(existing_cluster_id: Option[String] = None, new_cluster: Option[Cluster] = None, libraries: Option[List[Library]] = None)

case class ClusterInstance(cluster_id: String, spark_context_id: String)

case class RunParameters(jar_params: Option[List[String]] = None, notebook_params: Option[List[(String, String)]] = None, python_params: Option[List[String]] = None, spark_submit_params: Option[List[String]] = None)

object RunResultState extends Enumeration {
  val SUCCESS, FAILED, TIMEDOUT, CANCELED	= Value
}


