import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.gms)
    alias(libs.plugins.navigation.plugin)
}

android {
    namespace = "com.bytezaptech.jawlineexercise_faceyoga"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bytezaptech.jawlineexercise_faceyoga"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField(
            type = "String",
            name = "ADS_API_KEY",
            value = "\"${properties.getProperty("ADS_API_KEY")}\""
        )

    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Guava
    implementation(libs.guava)
    // ViewModel LiveData
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    // Dagger
    implementation(libs.dagger)
    kapt(libs.daggerCompiler)
    // Splash
    implementation(libs.splash)
    // Coroutines
    implementation(libs.coroutines)
    implementation(libs.coroutinesCore)
    // Shimmer
    implementation(libs.shimmer)
    // Lottie
    implementation(libs.lottie)
    // Firebase
    implementation(platform(libs.firebase))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.gson)
    // Google play services
    implementation(libs.play.services.auth)
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    // Navigation Library
    implementation(libs.navigation.ui.ktx)
    implementation(libs.navigation.fragment.ktx)
    // Glide
    implementation(libs.glide)
    // Circle Image View
    implementation(libs.circle.image.view)
    // CameraX
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.video)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)
    // Chart and graph library
    implementation(libs.eazegraph)
    implementation(libs.library)
    // Gif
    implementation(libs.android.gif.drawable)
    // Calendar
    implementation(libs.material.calendar.view)
    // Work Manger
    implementation(libs.workmanager)
    // Admob
    implementation(libs.play.services.ads)
}
