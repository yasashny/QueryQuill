plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.library.compose)
}

android {
    namespace = "com.yas.model"
}
dependencies {
    api(platform(libs.sora.editor.bom))
    api(libs.bundles.sora.editor)
}