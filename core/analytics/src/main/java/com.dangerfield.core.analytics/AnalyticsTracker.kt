package com.dangerfield.core.analytics

import com.dangerfield.core.common.doNothing
import javax.inject.Inject

class AnalyticsTracker @Inject constructor() {

    fun trackEvent(event: Event) {
        doNothing(event)
    }

    fun trackInteraction(interaction: Interaction) {
        doNothing(interaction)
    }

    fun trackPageView(pageName: String, extras: Map<String, Any>) {
        doNothing(pageName, extras)
    }
}
