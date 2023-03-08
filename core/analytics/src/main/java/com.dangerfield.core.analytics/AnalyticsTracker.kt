package com.dangerfield.core.analytics

import com.dangerfield.core.common.doNothing
import javax.inject.Inject

/**
 * class responsible for collecting, batching, and sending analytics
 */
class AnalyticsTracker @Inject constructor() {

    /**
     * adds an event to the analytics queue
     */
    fun trackEvent(event: Event) {
        doNothing(event)
    }

    /**
     * adds an interaction to the analytics queue
     */
    fun trackInteraction(interaction: Interaction) {
        doNothing(interaction)
    }

    /**
     * adds a page view to the analytics queue
     */
    fun trackPageView(pageName: String, extras: Map<String, Any>) {
        doNothing(pageName, extras)
    }
}
