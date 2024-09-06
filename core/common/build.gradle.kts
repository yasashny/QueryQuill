plugins {
    alias(libs.plugins.queryquill.android.library)
}

android {
    namespace = "org.queryquill.app.core.common"
}

dependencies {
    implementation(libs.koin.androidx.compose)
}