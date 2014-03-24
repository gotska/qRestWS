package util

import models._
import akka.dispatch.Foreach

case class DidNotFindQuery(message: String) extends Exception(message)

case class FoundDifferentParamCount(message: String) extends Exception(message)

object Parser {

  val name = "name"

  // param for finding query
  // prepare sql query for select
  def getSqlQuery(params: Map[String, String]): String = {
    val queryName = parseName(params)
    val str = SqlQuery.findByName(queryName)
    if (str.isEmpty)
      throw DidNotFindQuery(s"ERR-001: SQL query with name ${queryName} did not find")

    val countParamInMap = params.size - 1
    val countParamInQuery = str.get.count(_ == '{')

    if (countParamInMap != countParamInQuery)
      throw FoundDifferentParamCount(s"ERR-002: Expected ${countParamInQuery} parameters, but was ${countParamInMap}")

    createSqlQueryWithParam(str.get, params)

  }

  /*
   get parameter NAME
    */
  def parseName(params: Map[String, String]): String = {
    params.get(name).get
  }

  /*
   create sql query with parameters
    */
  def createSqlQueryWithParam(str: String, params: Map[String, String]): String = {
    addParam(str, params)
  }

  def addParam(str: String, params: Map[String, String]): String = {
    if (!params.tail.isEmpty) {
      addParam(paramReplace(str, params.head._1, params.head._2), params.tail)
    }
    else {
      paramReplace(str, params.head._1, params.head._2)
    }
  }

  /*
  replace {words} to values
   */
  def paramReplace(str: String, k: String, v: String): String = {
    if (k != name) {
      val str1 = str.subSequence(str.indexOf('{' + k), str.indexOf('}') + 1)
      str.replace(str1, v)
    }
    else {
      str
    }
  }

  def countParamsInQuery(q: String): Int = {
    q.count(_ == '{')
  }

}