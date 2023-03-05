package com.dangerfield.features.matchmaker

import com.dangerfield.core.analytics.AnalyticsTracker
import com.dangerfield.core.analytics.Event
import com.dangerfield.core.analytics.Interaction
import com.dangerfield.core.analytics.InteractionType.Press
import javax.inject.Inject

class MatchMakerAnalytics @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) {

    fun trackProfileImpression(id: Int) {
        analyticsTracker.trackPageView(pageName = MatchMakerProfilePageName, extras = mapOf("id" to id))
    }

    fun trackProfileScroll(percentScroll: Int, id: Int) {
        analyticsTracker.trackEvent(Event(mapOf("scrollPosition" to percentScroll, "id" to id)))
    }

    fun trackNextClick() {
        analyticsTracker.trackInteraction(
            Interaction(extras = mapOf(), elementId = NextButtonId, interactionType = Press)
        )
    }

    companion object {
        const val MatchMakerProfilePageName = "match_maker_profile"
        const val NextButtonId = "next"
    }
}
