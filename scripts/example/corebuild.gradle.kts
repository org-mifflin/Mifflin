plugins {
    id("mifflin.android.library")
}

android {
    namespace = "com.dangerfield.example"
}

dependencies {
    implementation(libs.timber)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.google.truth)
}
