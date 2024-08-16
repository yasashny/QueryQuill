plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.library.compose)
}

android {
    namespace = "com.yas.transaction"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.koin.androidx.compose)

    implementation(projects.core.ui)
    implementation(projects.core.model)
    implementation(projects.core.utils)
    implementation(projects.data.requests)
}