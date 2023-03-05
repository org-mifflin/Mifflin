plugins {
    id("mifflin.android.library")
    id("mifflin.android.hilt")
}

android {
    namespace = "com.dangerfield.mifflin.core.config"
}

dependencies {
    implementation(project(":core:config:api"))
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.google.truth)
}
