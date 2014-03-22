package util

import models._
import akka.dispatch.Foreach

object Parser {

  val name = "name" // param for finding query
   // prepare sql query for select
  def getSqlQuery(params: Map[String,String]): String = {
      val queryName = parseName(params)
      val str = SqlQuery.findByName(queryName)
   
      createSqlQueryWithParam(str, params)
   
  }
  
   // get name 
  def parseName(params: Map[String,String]):String = {
    params.get(name).get
   }
  
  
  // create sql query from string
  def createSqlQueryWithParam(str: String,params: Map[String,String]):String = {
   
	addParam(str,params)
 
  }  
    
  def addParam(str: String, params: Map[String,String]): String = {
    if(!params.tail.isEmpty){
     addParam(paramReplace(str,params.head._1,params.head._2),params.tail)
    }
    else{ 
	   paramReplace(str,params.head._1,params.head._2)
    }
  }                                       
 
   def paramReplace (str:String, k: String, v: String): String ={
     if(k!=name){
		val str1 = str.subSequence(str.indexOf('{'+k), str.indexOf('}')+1)
		str.replace(str1, v)
   }
     else{
       str
     }
	}                                              
	
}