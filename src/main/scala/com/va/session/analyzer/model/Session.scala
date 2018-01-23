package com.va.session.analyzer.model

import scala.collection.mutable

/**
  * Model class to store the session related information
  *
  * @author rohitt
  * Date : 20/01/2018
  */
class Session (
                var startEvent:EnrichedEvent = null, var endEvent: EnrichedEvent = null,
                var duration: Long = 0L, var weight: Long = 0L, var eventSet:mutable.Set[EnrichedEvent] = null) {

  override def toString: String =
    s"($startEvent, $endEvent, $duration, $weight, $eventSet)"

}
