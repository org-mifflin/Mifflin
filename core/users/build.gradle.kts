plugins {
    id("mifflin.android.library")
    id("mifflin.android.hilt")
}

android {
    namespace = "com.dangerfield.mifflin.core.users"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:common"))
    implementation(project(":core:users:api"))
    implementation(project(":core:users:remote"))
    implementation(project(":core:users:local"))
    implementation(libs.timber)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.google.truth)
}
