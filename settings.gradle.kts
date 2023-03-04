pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

enableFeaturePreview("VERSION_CATALOGS")

rootProject.name = "mifflin"

include("app")
include("core:analytics")
include("core:auth")
include("core:common")
include("core:config")
include("core:designsystem")
include("core:people")
include("core:people:api")
include("core:people:local")
include("core:people:remote")
include("core:session")
include("core:ui")
include("features:auth:api")
include("features:conversation")
include("features:matchmaker")
include("features:onboarding")
include("features:profile")
