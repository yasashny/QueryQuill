plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.library.compose)
}

android {
    namespace = "org.queryquill.app.core.ui"
}

dependencies {
    api(platform(libs.sora.editor.bom))
    api(libs.bundles.sora.editor)

    api(projects.core.model)
}