package controllers

import play.api._
import play.api.mvc._
import models._
import util._
import play.api.libs.json.Json

object Application extends Controller {

  def jsonN(name: String) = Action {
   val query = Parser.getSqlQuery(name) 
   val result = Table.sqlQueryWithName(query)
   val json = Json.toJson(result)
   
   Ok(json)
  }

  def jsonD(name: String) = Action {
   val query = Parser.getSqlQuery(name) 
   val result = Table.sqlQueryOnlyDate(query)
   val json = Json.toJson(result)
   
   Ok(json)
  }
  
  def index = Action {
    Ok(views.html.index(Option(SqlQuery.findAll)))
  }

    
}