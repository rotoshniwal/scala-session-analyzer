package com.verto.session.analyzer.controller

import com.verto.session.analyzer.model.{EnrichedEvent, Session}
import com.verto.session.analyzer.service.impl.EventServiceImpl
import com.verto.session.analyzer.utils.{CollectionUtils, JsonUtils}
import play.api.libs.json.{JsNumber, JsValue}
/**
  * Main program controlling the flow of operations and delegating tasks
  *
  * @author rohitt
  *         Date : 20/01/2018
  */
object EventsController extends App {

  //Read a JSON input file with event data and parse it
  println("Reading the input JSON events from the file & parsing them")
  val EventsMap = JsonUtils.readFromFile("src/main/resources/events.json")
  println("Successfully read and parsed the input events file")

  var eventDataExists: Boolean = true

  if (EventsMap.equals(Option.empty)) {
    println("There is no event data to process for session weight analysis. Hence, aborting program")
    eventDataExists = false
  }

  var maxSessionWeight: Long = 0L
  var maxTransitionWeightEvent: EnrichedEvent = null
  var session: Session = new Session()

  if (eventDataExists) {
    //Create a map of (event category) -> (No. of events traversed so far for this category)
    var categoryTraversedCountMap = CollectionUtils.getStringLinkedHashMap()
    var enrichedEvent: EnrichedEvent = null
    var enrichedEventMap = CollectionUtils.getEnrichedLinkedHashMap()

    //Iterate over the events data collection and enrich event data
    EventsMap.asInstanceOf[collection.mutable.LinkedHashMap[JsValue, JsValue]].foreach(p => {
      //println("Key=" + p._1 + ", value=" + p._2)
      enrichedEvent = new EnrichedEvent()

      //Set the values of input json fields from map in the instance
      enrichedEvent.articleId = (p._2 \ "article_id").get.asInstanceOf[JsNumber]
      enrichedEvent.category = (p._2 \ "category").get.toString()
      enrichedEvent.parentId = (p._2 \ "parent_id").get.asInstanceOf[JsNumber]
      enrichedEvent.timestamp = (p._2 \ "timestamp").get.asInstanceOf[JsNumber]

      if (enrichedEvent.parentId.toString() == "-1") { // Case when no parent for the event
        /**
          * Do nothing as isTransition, transitionWeight & parentCategory member variables
          * already set to correct default values
          */
      } else { // Case when the event has a parent
        val event: JsValue = EventsMap.asInstanceOf[collection.mutable.LinkedHashMap[JsValue, JsValue]].getOrElse(enrichedEvent.parentId, null)

        //Compute and set the following member variables : isTransition, transitionWeight & parentCategory
        if (event == null) { // Case when parent_id is not -1 for event, but parent_id record (as article_id) absent in json input file
          /**
            * Do nothing as isTransition, transitionWeight & parentCategory member variables
            * already set to correct default values
            */
        } else {
          enrichedEvent.parentCategory = (event \ "category").get.toString()
          enrichedEvent.isTransition = EventServiceImpl.isEventTransition(enrichedEvent.category, enrichedEvent.parentCategory)
          enrichedEvent.transitionWeight = EventServiceImpl.getEventTransitionWeight(enrichedEvent.category, categoryTraversedCountMap)
        } //End of else
      } //End of else

      if (categoryTraversedCountMap contains (enrichedEvent.category)) {
        categoryTraversedCountMap(enrichedEvent.category) = categoryTraversedCountMap(enrichedEvent.category) + 1L
      } else {
        categoryTraversedCountMap += (enrichedEvent.category -> 1L)
      }

      //Add the enriched event data instance to the Map : (article_id) -> (EnrichedEvent)
      enrichedEventMap += (enrichedEvent.articleId.toString() -> enrichedEvent)

      //Check if the event transition weight greater than current max session weight
      //The event with max transition weight will be part of some session, and this transition weight will be the SESSION WEIGHT
      if (enrichedEvent.transitionWeight > maxSessionWeight) {
        maxSessionWeight = enrichedEvent.transitionWeight
        maxTransitionWeightEvent = enrichedEvent
      }
    }) //End of foreach loop

    //Instantiate and populate the Session class instance. Compute session duration for event with max transition weight
    var parentId: String = maxTransitionWeightEvent.parentId.toString()
    var sessionRoot: EnrichedEvent = null

    //Store the previously computed max session weight in weight member variable of Session instance
    session.weight = maxSessionWeight

    //Backtrack from event with max weight to find first connected event of the same session with parent = -1
    while (parentId != "-1") {
      sessionRoot = enrichedEventMap.get(parentId).get
      parentId = sessionRoot.parentId.toString()
    }

    session.startEvent = sessionRoot

    //Find last connected event of session with maximum weight
    /**
      * PENDING IMPLEMENTATION LOGIC TO FIND SESSION's LAST CONNECTED ELEMENT WITH MAX TIMESTAMP
      * ----------------------------------------------------------------------------------------
      *
      * 1) We can build a map of (Parent ID) -> (Sorted Set by timestamp of all events to which this key is parent).
      * 2) In other words, we are building a map of parent ID vs all child of this parent in sorted timestamp order.
      * 3) Then, we can start from maxTransitionWeightEvent and lookup for the child event with max timestamp from above sorted map,
      * for which maxTransitionWeightEvent is parent.
      * 4) We repeat the same process for child event with max timestamp, and in turn search for its child with max timestamp.
      * We continue this until current iterated over event is not parent for any child events.
      * 5) Once we have session's events with lowest and highest timestamp, we can find out session duration by taking their difference.
      */
  } else {
    "There is no event data to process for session weight analysis. Hence, aborting program"
  }

  println("The maximum session weight is : " + maxSessionWeight)
  println("The event with max transition weight is : " + maxTransitionWeightEvent)
  println("For the session with maximum weight, lowest timestamp of starting event is :" + session.startEvent.timestamp)
}
