plugins {
    id("mifflin.android.library")
    id("mifflin.android.hilt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.dangerfield.mifflin.core.people.local"
}

dependencies {
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.retrofit.gson)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.google.truth)
}
