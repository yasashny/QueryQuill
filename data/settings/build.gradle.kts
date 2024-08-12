plugins {
    alias(libs.plugins.queryquill.android.library)
}

android {
    namespace = "com.yas.settings"
}

dependencies {
    implementation(libs.koin.androidx.compose)
    implementation(libs.bundles.datastore)

    implementation(projects.core.model)
    implementation(projects.core.common)
}