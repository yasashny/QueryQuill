plugins {
    alias(libs.plugins.queryquill.android.library)
}

android {
    namespace = "org.queryquill.app.core.utils"
}

dependencies {
    implementation(projects.core.model)
}