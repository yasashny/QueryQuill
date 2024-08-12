plugins {
    alias(libs.plugins.queryquill.android.library)
}

android {
    namespace = "com.yas.utils"
}

dependencies {
    implementation(projects.core.model)
}