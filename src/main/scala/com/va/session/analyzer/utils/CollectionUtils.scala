package com.va.session.analyzer.utils

import com.data.session.analyzer.model.EnrichedEvent
import com.va.session.analyzer.model.EnrichedEvent
import com.verto.session.analyzer.model.EnrichedEvent
import play.api.libs.json.JsValue
/**
  * Returns the desired collection
  *
  * @author rohitt
  * Date : 20/01/2018
  */
object CollectionUtils {
  def getLinkedHashMap() = {
    //LinkedHashMap to preserve the order of events with increasing timestamp
    collection.mutable.LinkedHashMap[JsValue, JsValue]()
  }

  def getStringLinkedHashMap() = {
    collection.mutable.LinkedHashMap[String, Long]()
  }

  def getEnrichedLinkedHashMap() = {
    collection.mutable.LinkedHashMap[String, EnrichedEvent]()
  }
}
