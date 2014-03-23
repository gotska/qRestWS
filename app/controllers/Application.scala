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

   def get(name:String) = Action { request =>
    val map = request.queryString.map { case (k,v) => k -> v.mkString }
    val query = Parser.getSqlQuery(map)
    val result = Table.sqlQueryWithName(query)
    val json = Json.toJson(result)
   
    Ok(json)
  }
    
}