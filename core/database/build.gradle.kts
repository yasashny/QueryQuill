plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.room)
    alias(libs.plugins.serialization)
}

android {
    namespace = "org.queryquill.app.core.database"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.koin.androidx.compose)

    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(projects.core.utils)
}