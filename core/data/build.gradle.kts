plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.room)
    alias(libs.plugins.serialization)
}

android {
    namespace = "org.queryquill.app.core.data"
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.koin.androidx.compose)
    implementation(libs.bundles.datastore)

    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(projects.core.utils)
    implementation(projects.core.datastore)
    implementation(projects.core.database)
    implementation(projects.core.network)
}