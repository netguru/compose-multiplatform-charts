plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlinJvm)
}

repositories {
    google()
    mavenCentral()
}
dependencies {
    // Required to access libs inside our plugin in buildSrc
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.plugin.android)
    implementation(libs.plugin.kotlin)
    implementation(gradleKotlinDsl())
    implementation(libs.plugin.poet)
    implementation(libs.plugin.json)
}
