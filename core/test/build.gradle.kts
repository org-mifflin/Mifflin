plugins {
    id("mifflin.android.library")
}

android {
    namespace = "com.dangerfield.mifflin.core.test"
}

dependencies {
    // WARNING: Must use implementation here to expose dependencies in this modules main dir.
    implementation(project(":core:common"))
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.junit)
    implementation(libs.androidx.test.junit)
    implementation(libs.androidx.test.core)
    implementation(libs.google.truth)
}
