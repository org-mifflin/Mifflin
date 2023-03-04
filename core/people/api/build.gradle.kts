plugins {
    id("mifflin.android.library")
}

android {
    namespace = "com.dangerfield.mifflin.core.people.api"
}

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.google.truth)
}
