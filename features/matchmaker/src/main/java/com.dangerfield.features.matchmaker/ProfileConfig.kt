package com.dangerfield.features.matchmaker

import api.ProfileSection
import com.dangerfield.core.config.api.AppConfig
import javax.inject.Inject

private const val ProfileOrderKey = "profile"

class ProfileConfig @Inject constructor(
    private val appConfig: AppConfig
) {

    val profileSectionOrder: List<ProfileSection>
        get() {
            val values = appConfig.value<List<String>>(ProfileOrderKey)
            val sections = values?.mapNotNull {
                ProfileSection.values().find { section -> section.sectionName == it }
            }?.takeIf { it.size == ProfileSection.values().size }

            return sections ?: listOf(
                ProfileSection.Name,
                ProfileSection.Photo,
                ProfileSection.About,
                ProfileSection.Gender,
                ProfileSection.School,
                ProfileSection.Hobbies,
            )
        }
}
