package util

import models._

object Parser {

   // prepare sql query for select
  def getSqlQuery(name: String): String = {
    if (name.contains('(')) {
      val queryName = parseName(name)
      val str = SqlQuery.findByNameWithParam(queryName)
      createSqlQueryWithParam(str, name)
    } else
      SqlQuery.findByName(name)
  }
  
   // get name 
  def parseName(name: String):String = {
    name.subSequence(0, name.indexOf('(')).toString()
   }
  
  // create sql query from string
  def createSqlQueryWithParam(str: String,fn: String):String = {
   val strWithOutBrackets = getStringParamBetweenBrackets(fn)
   val strSeq = stringToStringList(strWithOutBrackets).seq
  
   addParam(str,strSeq)
   
  }          
  
  def getStringParamBetweenBrackets(str: String):String = {
    str.subSequence(str.indexOf('(')+1, str.indexOf(')')).toString()
  }                                              
  
  def stringToStringList (str: String): Seq[String] ={
	str.split(',')
  }                                              
  
  
  def addParam(str: String, param:Seq[String]): String = {
    if(!param.tail.isEmpty){
     addParam(paramReplace(str,param.head),param.tail)
    }
    else{
	   paramReplace(str,param.head)
    }
  }                                              
 
  def paramReplace (str:String, prm: String): String ={
		val str1 = str.subSequence(str.indexOf('{'), str.indexOf('}')+1)
		str.replace(str1, prm)
	}                                        
	
}