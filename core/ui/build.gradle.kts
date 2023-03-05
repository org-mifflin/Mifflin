plugins {
    id("mifflin.android.library")
    id("mifflin.android.library.compose")
}

android {
    namespace = "com.dangerfield.core.ui"
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.coil)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.androidx.lifecycle.vm)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.google.truth)
}
