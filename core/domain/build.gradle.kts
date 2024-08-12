plugins {
    alias(libs.plugins.queryquill.android.library)
}

android {
    namespace = "com.yas.domain"
}

dependencies {
    implementation(libs.koin.androidx.compose)

    implementation(projects.core.model)
    implementation(projects.data.settings)
}