plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.library.compose)
}

android {
    namespace = "org.queryquill.app.feature.cookie"
}

dependencies {
    implementation(libs.koin.androidx.compose)

    implementation(projects.core.ui)
    implementation(projects.core.model)
}