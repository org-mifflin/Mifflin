package com.dangerfield.features.matchmaker

import api.ProfileSection
import com.dangerfield.core.config.api.AppConfig
import javax.inject.Inject

private const val ProfileOrderKey = "profile"

class ProfileConfig @Inject constructor(
    private val appConfig: AppConfig
) {

    private val defaultProfileSectionOrder = listOf(
        ProfileSection.Name,
        ProfileSection.Photo,
        ProfileSection.About,
        ProfileSection.Gender,
        ProfileSection.School,
        ProfileSection.Hobbies,
    )

    fun getProfileSectionOrder(): List<ProfileSection> {
        val values = appConfig.value<List<String>>(ProfileOrderKey)
        val sections = values?.mapNotNull {
            ProfileSection.values().find { section -> section.sectionName == it }
        }?.takeIf { it.size == ProfileSection.values().size }

        return sections ?: defaultProfileSectionOrder
    }
}
