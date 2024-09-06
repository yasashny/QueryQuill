plugins {
    alias(libs.plugins.queryquill.android.library)
    alias(libs.plugins.queryquill.android.library.compose)
}

android {
    namespace = "org.queryquill.app.core.designsystem"
}
dependencies {
    api(projects.core.model)
}