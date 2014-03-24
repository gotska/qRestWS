package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import java.util.Date

case class SqlQuery(name: String, query: String)

  
object SqlQuery{
  
  /**
   * Parse a Comment from a ResultSet
   */
  val simple = {
    get[String]("sql_query.name") ~
    get[String]("sql_query.query") map {
      case name~query => SqlQuery(name,query)
    }
  }
  
  // -- Queries
 
  def findByName(name: String): Option[String] = {
    DB.withConnection("h2") { implicit connection =>
      SQL("select query from sql_query where name = {name}").on(
        'name -> name
      ).as(scalar[String].singleOpt)
    }
  }
 
  def findAll: Seq[SqlQuery] = {
    DB.withConnection("h2") { implicit connection =>
      SQL("select * from sql_query").as(SqlQuery.simple.*)
    }
  }
  
 def insert(q: SqlQuery) {
    DB.withConnection("h2") { implicit s =>
      SQL(
        """
          INSERT INTO sql_query (name,query) 
          VALUES ({name},  {query})
        """
      ).on(
        'name -> q.name,  
        'query -> q.query
      ).executeUpdate() 
    }
}
  
   def delete(name: String) {
    DB.withConnection("h2") { implicit s =>
      SQL(
        """
          delete from sql_query where name = {id};
        """
      ).on(
        'id -> name
        ).executeUpdate()                                       
    }
   }
}