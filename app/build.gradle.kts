plugins {
    alias(libs.plugins.queryquill.android.application)
    alias(libs.plugins.queryquill.android.application.compose)
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    namespace = "com.yas.queryquill"

    defaultConfig {
        applicationId = "com.yas.queryquill"
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

}
dependencies {

    implementation(libs.activity.compose)

    //Coroutines
    implementation(libs.kotlinx.coroutines.android)

    //Koin
    implementation(libs.koin.androidx.compose)

    //Navigation
    implementation(libs.navigation.compose)

    //SoraEditor
    implementation(platform(libs.sora.editor.bom))
    implementation(libs.bundles.sora.editor)

    //WindowSizeClass
    implementation(libs.material3.window.size.classs)

    //SplashScreen
    implementation(libs.core.splashscreen)

    implementation(projects.data.settings)
    implementation(projects.data.requests)
    implementation(projects.core.model)
    implementation(projects.core.ui)
    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.core.utils)
    implementation(projects.feature.settings)
    implementation(projects.feature.response)
    implementation(projects.feature.newTransaction)
    implementation(projects.feature.requestCodeEditor)
    implementation(projects.feature.request)
    implementation(projects.feature.transaction)

}