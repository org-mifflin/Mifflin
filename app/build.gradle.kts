plugins {
    id("mifflin.android.application")
    id("mifflin.android.application.compose")
    id("mifflin.android.hilt")
    id("org.jetbrains.kotlin.android")
}

android {

    namespace = "com.dangerfield.mifflin"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }

    packagingOptions {
        resources.excludes.add("META-INF/gradle/incremental.annotation.processors")
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.process)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.compose.navigation)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.javax.inject)
    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    implementation(libs.coil)

    implementation(project(":core:analytics"))
    implementation(project(":core:auth"))
    implementation(project(":core:common"))
    implementation(project(":core:config"))
    implementation(project(":core:config:api"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:users"))
    implementation(project(":core:users:api"))
    implementation(project(":core:users:local"))
    implementation(project(":core:users:remote"))
    implementation(project(":core:session"))
    implementation(project(":core:ui"))

    implementation(project(":features:conversation"))
    implementation(project(":features:login"))
    implementation(project(":features:matchmaker"))
    implementation(project(":features:onboarding"))
    implementation(project(":features:profile"))
    implementation(project(":features:signup"))

    testImplementation(project(":core:test"))
}
