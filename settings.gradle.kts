pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
rootProject.name = "compose-multiplatform-charts"

enableFeaturePreview("VERSION_CATALOGS")

include(":multiplatform-charts")
include(":example-app:application")
include(":example-app:common")
include(":example-app:android")
include(":example-app:desktop")
