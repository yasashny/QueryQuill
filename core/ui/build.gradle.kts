plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.library.compose)
}

android {
    namespace = "com.yas.ui"
}

dependencies {
    implementation(platform(libs.sora.editor.bom))
    implementation(libs.bundles.sora.editor)

    implementation(projects.core.model)
}