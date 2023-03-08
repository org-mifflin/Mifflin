plugins {
    id("mifflin.android.library")
}

android {
    namespace = "com.dangerfield.mifflin.core.analytics"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.javax.inject)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.google.truth)
}
