plugins {
    alias(libs.plugins.queryquill.android.library)
}

android {
    namespace = "com.yas.common"
}

dependencies {
    implementation(libs.koin.androidx.compose)
}