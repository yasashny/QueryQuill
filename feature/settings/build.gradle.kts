plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.library.compose)
}

android {
    namespace = "com.yas.feature_settings"
}

dependencies {
    implementation(libs.koin.androidx.compose)

    implementation(projects.core.ui)
    implementation(projects.core.model)
    implementation(projects.data.settings)
}