plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.appjohn"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.appjohn"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        dataBinding = true
    }
    viewBinding {
        enable=true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))
        // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")

    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    // Firebase Authentication
    implementation ("com.google.firebase:firebase-auth-ktx")

    // Firebase Firestore
    implementation ("com.google.firebase:firebase-firestore-ktx")

    // Firebase Google Auth
    implementation ("com.google.android.gms:play-services-auth:20.7.0")

    // SwipeRefresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    }