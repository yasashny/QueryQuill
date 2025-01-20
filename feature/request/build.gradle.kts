plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.library.compose)
}

android {
    namespace = "org.queryquill.app.feature.request"
}

dependencies {
    implementation(libs.activity.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.koin.androidx.compose)

    implementation(projects.core.ui)
    implementation(projects.core.model)
    implementation(projects.core.utils)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
}