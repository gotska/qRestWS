package util

import models._

object Parser {

  def getSqlQuery(name: String): String = {
    if (name.contains('(')) {
      val str = SqlQuery.findByNameWithParam(parseName(name))
      addParam(str, parseParam(name))
    } else
      SqlQuery.findByName(name)
  }
  
  def parseName(name: String):String = {
    name.subSequence(0, name.indexOf('(')).toString()
   }
  
  def parseParam(name: String):String = {
    name.subSequence(name.indexOf('(')+1, name.indexOf(')')).toString()
  }
  
  def addParam(str: String,param:String):String = {
    str.subSequence(0, str.indexOf('{'))+param
  }
}