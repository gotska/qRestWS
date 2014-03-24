package controllers

import play.api._
import play.api.mvc._
import models._
import util._
import play.api.libs.json.Json

object Application extends Controller {


  def index = Action {
    Ok(views.html.index(Option(SqlQuery.findAll)))
  }

  def test = Action {
    Ok(views.html.test())
  }

  def get(name: String) = Action {
    request =>
      val map = request.queryString.map {
        case (k, v) => k -> v.mkString
      }

      try {
        val query = Parser.getSqlQuery(params)
        val result = Table.sqlQueryWithName(query)
        val json = Json.toJson(result)
        Ok(json)
      }
      catch {
        case DidNotFindQuery(msg) => Ok(msg)
        case FoundDifferentParamCount(msg) => Ok(msg)
      }

  }

  def post = Action {
    request =>
      val map = request.body.asFormUrlEncoded.getOrElse(Map()).map {
        case (k: String, v: List[String]) => k -> v.mkString
      }
      try {
        val query = Parser.getSqlQuery(params)
        val result = Table.sqlQueryWithName(query)
        val json = Json.toJson(result)
        Ok(json)
      }
      catch {
        case DidNotFindQuery(msg) => Ok(msg)
        case FoundDifferentParamCount(msg) => Ok(msg)
      }
  }


}