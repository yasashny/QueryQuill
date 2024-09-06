plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.library.compose)
}

android {
    namespace = "org.queryquill.app.feature.response"
}

dependencies {
    implementation(libs.activity.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.koin.androidx.compose)

    implementation(libs.coil.compose)

    implementation(projects.core.ui)
    implementation(projects.core.model)
    implementation(projects.core.utils)
    implementation(projects.data.requests)
    implementation(projects.core.designsystem)
}