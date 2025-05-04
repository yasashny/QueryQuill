plugins {
    alias(libs.plugins.queryquill.android.library)
}

android {
    namespace = "org.queryquill.app.core.datastore"
}

dependencies {
    implementation(libs.koin.androidx.compose)
    implementation(libs.datastore.preferences)
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.core.model)
    implementation(projects.core.common)

    testImplementation(projects.core.testing)
}