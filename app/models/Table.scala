package models

import play.api._
import play.api.mvc._
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.session.{Database, Session}
import scala.slick.jdbc.{GetResult, StaticQuery => Q}
import Database.threadLocalSession
import Q.interpolation

 object Table{
  
  lazy val db2 = Database.forURL("jdbc:postgresql://192.168.10.101:5432/ishop", "play", "play", null, "org.postgresql.Driver") 
   
  implicit val resultAsStringMap = GetResult[Map[String,String]] ( prs => 
      (1 to prs.numColumns).map(_ => 
         prs.rs.getMetaData.getColumnName(prs.currentPos+1) -> prs.nextString
      ).toMap
   )
 
  implicit val getListStringResult = GetResult[List[String]] (
    r => (1 to r.numColumns).map(_ => r.nextString).toList
)
   
  def sqlQueryWithName(sql: String)  = {
   db2 withSession {
	   Q.queryNA[Map[String,String]](sql).list
	 }
  }
   
  def sqlQueryOnlyDate(sql: String)  = {
   db2 withSession {
	   Q.queryNA[List[String]](sql).list
	 }
  }
  
}