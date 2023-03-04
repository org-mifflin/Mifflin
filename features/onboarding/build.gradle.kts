

plugins {
    id("mifflin.android.feature")
}

android {
    namespace = "com.dangerfield.mifflin.features.onboarding"
}

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.google.truth)
}
