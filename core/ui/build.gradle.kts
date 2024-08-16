plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.library.compose)
}

android {
    namespace = "com.yas.ui"
}

dependencies {
    api(platform(libs.sora.editor.bom))
    api(libs.bundles.sora.editor)

    api(projects.core.model)
}