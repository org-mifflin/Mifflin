package com.dangerfield.features.matchmaker

import com.dangerfield.core.analytics.AnalyticsTracker
import com.dangerfield.core.analytics.InteractionType
import com.dangerfield.features.matchmaker.MatchMakerAnalytics.Companion.MatchMakerProfilePageName
import com.dangerfield.features.matchmaker.MatchMakerAnalytics.Companion.NextButtonId
import com.dangerfield.features.matchmaker.MatchMakerAnalytics.Companion.ScrollPosition
import com.dangerfield.features.matchmaker.MatchMakerAnalytics.Companion.UserId
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

internal class MatchMakerAnalyticsTest {

    private val analyticsTracker: AnalyticsTracker = mockk(relaxed = true)

    lateinit var matchMakerAnalytics: MatchMakerAnalytics

    @Before
    fun setup() {
        matchMakerAnalytics = MatchMakerAnalytics(analyticsTracker)
    }

    @Test
    fun `GIVEN page view of a user, WHEN tracking analytics, THEN user id should be in extras `() {
        val userid = 12
        matchMakerAnalytics.trackProfileImpression(userid)
        verify {
            analyticsTracker.trackPageView(
                eq(MatchMakerProfilePageName),
                withArg {
                    assertThat(it[UserId]).isEqualTo(userid)
                }
            )
        }
    }

    @Test
    fun `GIVEN scroll on profile of a user, WHEN tracking analytics, THEN user id should be in extras `() {
        val userid = 34
        val scrollPosition = 50
        matchMakerAnalytics.trackProfileScroll(scrollPosition, userid)
        verify {
            analyticsTracker.trackInteraction(
                withArg {
                    assertThat(it.interactionType).isEqualTo(InteractionType.Scroll)
                    assertThat(it.extras[UserId]).isEqualTo(userid)
                    assertThat(it.extras[ScrollPosition]).isEqualTo(scrollPosition)
                }
            )
        }
    }

    @Test
    fun `GIVEN next click, WHEN on a users profile, THEN analytics should be tracked with next click `() {
        matchMakerAnalytics.trackNextClick()
        verify {
            analyticsTracker.trackInteraction(
                withArg {
                    assertThat(it.interactionType).isEqualTo(InteractionType.Press)
                    assertThat(it.elementId).isEqualTo(NextButtonId)
                }
            )
        }
    }
}
