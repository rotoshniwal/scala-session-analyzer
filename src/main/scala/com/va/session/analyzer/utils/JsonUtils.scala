package com.va.session.analyzer.utils

import play.api.libs.json.{JsValue, Json}

import scala.io.Source
/**
  * Contains all the JSON related utility functions
  *
  * @author rohitt
  * Date : 20/01/2018
  */
object JsonUtils {

  /**
    * Reads the json string line-by-line from an input file,
    * parses it and stores it in a map
    *
    * @param filename
    */
  def readFromFile(filename: String) = {

    //LinkedHashMap to preserve the order of events with increasing timestamp
    var eventsMap = CollectionUtils.getLinkedHashMap()

    for(line <- Source.fromFile(filename).getLines()) {
      var json: JsValue = Json.parse(line)
      var articleId:JsValue = (json \ "article_id").get
      eventsMap += (articleId -> json)
    }// End of for-loop

    if(eventsMap.isEmpty)
      Option.empty
    else
      eventsMap
  }
}
