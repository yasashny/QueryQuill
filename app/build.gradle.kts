/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

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
        versionName = "0.1.0"

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
    implementation(projects.feature.cookie)
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.network)
    implementation(projects.core.data)
}