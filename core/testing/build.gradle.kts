plugins {
    alias(libs.plugins.queryquill.android.library)
}

android {
    namespace = "org.queryquill.app.core.testing"
}
dependencies {
    api(projects.core.common)
    api(projects.core.data)
    api(projects.core.model)
    api(libs.junit)
    api(libs.androidx.test.rules)
    api(libs.kotlinx.coroutines.test)
    api(libs.robolectric)
    api(libs.androidx.core.ktx)
}