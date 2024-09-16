plugins {
    alias(libs.plugins.queryquill.android.application)
    alias(libs.plugins.queryquill.android.application.compose)
    alias(libs.plugins.firebase.crashlytics)
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    namespace = "org.queryquill.app"

    defaultConfig {
        applicationId = "org.queryquill.app"
        versionCode = 1
        versionName = "1.0.0"

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

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

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
    implementation(projects.core.designsystem)

}