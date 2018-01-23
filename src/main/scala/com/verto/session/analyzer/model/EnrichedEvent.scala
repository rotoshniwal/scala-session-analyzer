package com.verto.session.analyzer.model

import play.api.libs.json.JsNumber

/**
  * Model class to store the additional event info derived through
  * some business logic or computation
  *
  * @author rohitt
  * Date : 20/01/2018
  */
class EnrichedEvent(
                     var articleId:JsNumber = null, var category: String = null, var parentId: JsNumber = null, var timestamp: JsNumber = null,
                     var parentCategory: String = null, var isTransition: Boolean = false, var transitionWeight: Long = 0L) {

  override def toString: String =
    s"($articleId, $category, $parentId, $timestamp, $parentCategory, $isTransition, $transitionWeight)"
}
