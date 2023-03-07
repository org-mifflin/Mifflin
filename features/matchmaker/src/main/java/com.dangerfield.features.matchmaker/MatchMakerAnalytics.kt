package com.dangerfield.features.matchmaker

import com.dangerfield.core.analytics.AnalyticsTracker
import com.dangerfield.core.analytics.Interaction
import com.dangerfield.core.analytics.InteractionType.Press
import com.dangerfield.core.analytics.InteractionType.Scroll
import javax.inject.Inject

class MatchMakerAnalytics @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) {

    fun trackProfileImpression(id: Int) {
        analyticsTracker.trackPageView(pageName = MatchMakerProfilePageName, extras = mapOf(UserId to id))
    }

    fun trackProfileScroll(percentScroll: Int, id: Int) {
        analyticsTracker.trackInteraction(
            Interaction(
                extras = mapOf(ScrollPosition to percentScroll, UserId to id),
                elementId = ProfileViewId,
                interactionType = Scroll
            )
        )
    }

    fun trackNextClick() {
        analyticsTracker.trackInteraction(
            Interaction(extras = mapOf(), elementId = NextButtonId, interactionType = Press)
        )
    }

    companion object {
        const val MatchMakerProfilePageName = "match_maker_profile"
        const val NextButtonId = "next"
        const val UserId = "id"
        const val ProfileViewId = "profile_view"
        const val ScrollPosition = "scrollPosition"
    }
}
