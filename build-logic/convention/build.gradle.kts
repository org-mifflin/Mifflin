
plugins {
    `kotlin-dsl`
}

group = "com.dangerfield.mifflin.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${libs.versions.detekt.get()}")
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "mifflin.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "mifflin.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "mifflin.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "mifflin.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "mifflin.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("androidTest") {
            id = "mifflin.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("androidHilt") {
            id = "mifflin.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidDetekt") {
            id = "mifflin.android.detekt"
            implementationClass = "AndroidDetektConventionPlugin"
        }

        register("androidCheckstyle") {
            id = "mifflin.android.checkstyle"
            implementationClass = "AndroidCheckstyleConventionPlugin"
        }
    }
}
