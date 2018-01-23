package com.verto.session.analyzer.service.impl

import com.verto.session.analyzer.service.EventService

/**
  * Contains the business logic functions around event data
  * and associated computations.
  *
  * @author rohitt
  * Date : 20/01/2018
  */
object EventServiceImpl extends EventService {

  /**
    * Checks if an event is a transition or not
    *
    * @param eventCategory
    * @param parentCategory
    * @return
    */
  def isEventTransition(eventCategory:String, parentCategory:String): Boolean = {
    if(eventCategory == parentCategory)
      false
    else
      true
  }

  /**
    * Calculates and returns the transition weight for an event
    *
    * @param categoryTraversedCountMap
    * @return
    */
  def getEventTransitionWeight(eventCategory:String, categoryTraversedCountMap:collection.mutable.Map[String, Long]): Long = {
    if(categoryTraversedCountMap contains(eventCategory)) { // Case when this category is already added in Map as KEY
        categoryTraversedCountMap(eventCategory)
    } else {
        0L
    }
  }

}
