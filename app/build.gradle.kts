

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")  //Agregar el firebase a la aplicacion
}

android {
    namespace = "com.example.reservaplusapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.reservaplusapp"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //Agregar el firebase a la aplicacion
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")

    // Sheets Compose Dialogs - Core
    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")

    // Sheets Compose Dialogs - Calendar
    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.2.0")

    // Coil para cargar imágenes de forma asíncrona
    implementation ("io.coil-kt:coil-compose:2.2.2")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.core.ktx.v1101)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.core.ktx.v1150)
    implementation(libs.androidx.lifecycle.runtime.ktx.v261)
    implementation(libs.androidx.activity.compose.v172)
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation (libs.coil.compose) // Usa la última versión estable
    implementation (libs.androidx.runtime.livedata)

    implementation(libs.coil.compose)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.foundation.android)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation ("androidx.navigation:navigation-compose:2.6.0")  // Verifica la versión
    implementation ("androidx.compose.material3:material3:1.1.0" )   // Verifica la versión

}