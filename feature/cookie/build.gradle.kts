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
    testImplementation(projects.core.testing)
    implementation(libs.androidx.lifecycle.runtime.testing)
    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
}