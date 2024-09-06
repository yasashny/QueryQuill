plugins {
    alias(libs.plugins.queryquill.android.library)
}

android {
    namespace = "org.queryquill.app.data.settings"
}

dependencies {
    implementation(libs.koin.androidx.compose)
    implementation(libs.bundles.datastore)

    implementation(projects.core.model)
    implementation(projects.core.common)
}