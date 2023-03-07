plugins {
    id("mifflin.android.library")
    id("mifflin.android.library.compose")
}

android {
    namespace = "com.dangerfield.mifflin.core.test"
}

dependencies {
    // WARNING: Must use implementation here to expose dependencies in this modules main dir.
    implementation(project(":core:common"))
    implementation(libs.kotlinx.coroutines)
    implementation(libs.google.truth)
    implementation(libs.androidx.test.junit)
    implementation(libs.androidx.datastore)

    // api to expose these dependencies to dependants
    api(libs.mockk)
    api(libs.junit)
    api(libs.androidx.test.core)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)
    api(libs.google.truth)
    api(libs.androidx.test.runner)
    api(libs.androidx.test.rules)
    api(libs.androidx.compose.ui.test)

    debugApi(libs.androidx.compose.ui.testManifest)
}
