plugins {
    alias(libs.plugins.queryquill.android.library)
}

android {
    namespace = "org.queryquill.app.core.domain"
}

dependencies {
    implementation(libs.koin.androidx.compose)

    implementation(projects.core.model)
    implementation(projects.core.data)

}