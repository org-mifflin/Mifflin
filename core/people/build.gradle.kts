plugins {
    id("mifflin.android.library")
    id("mifflin.android.hilt")
}

android {
    namespace = "com.dangerfield.mifflin.core.people"
}

dependencies {
    implementation(project(":core:people:api"))
    implementation(project(":core:people:remote"))
    implementation(project(":core:people:local"))

    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.google.truth)
}
