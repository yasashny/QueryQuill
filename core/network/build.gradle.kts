plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.serialization)
}

android {
    namespace = "org.queryquill.app.core.network"
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.koin.androidx.compose)

    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(projects.core.utils)
}