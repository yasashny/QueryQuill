plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.library.compose)
}

android {
    namespace = "org.queryquill.app.feature.settings"
}

dependencies {
    implementation(libs.koin.androidx.compose)
    implementation(libs.play.services.oss.licenses)

    implementation(projects.core.ui)
    implementation(projects.core.model)
    implementation(projects.core.data)
}