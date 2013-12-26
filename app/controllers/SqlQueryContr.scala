package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import models._
import java.lang.String
import play.api.db._
import play.api.Play.current


object SqlQueryContr extends Controller {

  /**
   * Describe the order form (used in both edit and create screens).
   */ 
  val queryForm = Form(
    mapping( 
      "cName" -> text,
      "cQuery" -> text
    )(SqlQuery.apply)(SqlQuery.unapply)
  )
  
  def newQuery = Action { implicit request =>
    Ok(views.html.query("", queryForm))
     
  }
 
  /**
   * Handle the 'order form' submission.
   */
  def save(name: String) = Action { implicit request =>
 
  	queryForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.query(name,formWithErrors)),
      q => {
        SqlQuery.insert(q) 
        Redirect(routes.Application.index)
      }
    )    
  }
  
}


