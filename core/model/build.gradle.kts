plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.library.compose)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.yas.model"
}
dependencies {
    api(platform(libs.sora.editor.bom))
    api(libs.bundles.sora.editor)
    implementation(libs.kotlinx.serialization.json)
}