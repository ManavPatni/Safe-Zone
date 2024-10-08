plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("com.google.gms.google-services")
}

android {
    namespace = "com.thecodeproject.in.safezone"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thecodeproject.in.safezone"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.location)
    implementation(libs.androidx.work.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics")
    //Firebase Auth
    implementation ("com.google.firebase:firebase-auth-ktx:23.0.0")

    //Google Play Services
    //Google Auth
    implementation ("com.google.android.gms:play-services-auth:21.2.0")

    //lottie
    implementation("com.airbnb.android:lottie:6.5.0")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    //SDP & SSP-auto sizing
    implementation("com.intuit.sdp:sdp-android:1.1.1")
    runtimeOnly("com.intuit.ssp:ssp-android:1.1.1")

    //Image loading library Glider
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    //chart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    //country picker
    implementation("com.hbb20:ccp:2.7.3")

}