package io.findify.databricks.calls

import io.findify.databricks.Auth
import io.findify.databricks.api._
import org.asynchttpclient.AsyncHttpClient
import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{writePretty, read => readjson}

import scala.concurrent.Future

class Runs(auth:Auth, client:AsyncHttpClient) extends ApiCall(s"https://${auth.hostname}/api/2.0/jobs/runs", auth, client) {
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val formats = Serialization.formats(NoTypeHints)

  def list(params:Map[String,String]) = getJson("list", params).map(readjson[RunsList])

  def get(id:Int) = getJson("get", Map("job_id" -> id.toString)).map(readjson[JobWithId])
}

