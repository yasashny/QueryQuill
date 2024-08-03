plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.yas.queryquill"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yas.queryquill"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    //Coroutines
    implementation(libs.kotlinx.coroutines.android)

    //Koin
    implementation(libs.koin.androidx.compose)

    //Navigation
    implementation(libs.navigation.compose)

    //Serialization
    implementation(libs.kotlinx.serialization.json)

    //CoreLibraryDesugaring
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    //SoraEditor
    implementation(platform(libs.sora.editor.bom))
    implementation(libs.sora.editor)
    implementation(libs.sora.editor.textmate)

    //WindowSizeClass
    implementation(libs.material3.window.size.classs)

    //SplashScreen
    implementation(libs.core.splashscreen)

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":core:settings-data"))
    implementation(project(":feature:settings"))
    implementation(project(":common"))
    implementation(project(":core:requests-data"))

}