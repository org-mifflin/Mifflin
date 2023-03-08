plugins {
    id("mifflin.android.library")
}

android {
    namespace = "com.dangerfield.mifflin.core.users.api"
}

dependencies {
    implementation(project(":core:common"))
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.google.truth)
}
