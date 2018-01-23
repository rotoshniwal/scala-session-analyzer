package com.va.session.analyzer.service

trait EventService {
  def isEventTransition(eventCategory: String, parentCategory: String): Boolean

  def getEventTransitionWeight(eventCategory: String, categoryTraversedCountMap: collection.mutable.Map[String, Long]): Long
}
