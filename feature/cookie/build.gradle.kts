import org.queryquill.app.implementation

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
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
}