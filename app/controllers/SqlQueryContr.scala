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
  
  /*def editCategory(catId: Long) = { implicit request =>
    val mapCat = Category.findAll.map(t =>t.id.toString -> t.name)
    Category.findById(catId).map { cat =>
      Ok(views.html.admin.category(catId,categoryForm.fill(cat), mapCat))
    }.getOrElse(NotFound) 
  }*/ 
 
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
  
  /*def delCategory(catId: String) = Action { implicit request =>
    SqlQuery.delete(catId)
      Ok(views.html.admin.categories(Category.findAll()))
     
  }*/
  
}


