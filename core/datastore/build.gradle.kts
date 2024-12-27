plugins {
    alias(libs.plugins.queryquill.android.library)
}

android {
    namespace = "org.queryquill.app.core.datastore"
}

dependencies {
    implementation(libs.koin.androidx.compose)
    implementation(libs.bundles.datastore)

    implementation(projects.core.model)
    implementation(projects.core.common)
}