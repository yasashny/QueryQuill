plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.room)
    alias(libs.plugins.serialization)
}

android {
    namespace = "org.queryquill.app.data.requests"
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.koin.androidx.compose)
    implementation(libs.bundles.datastore)

    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(projects.core.utils)
}