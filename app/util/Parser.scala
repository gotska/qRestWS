package util

import models._

object Parser {

  def getSqlQuery(name: String) = {
    
    SqlQuery.findByName(name)
  }
}