val hiltVersion: String by rootProject.extra
val lifecycleVersion: String by rootProject.extra
val coroutinesVersion: String by rootProject.extra
val retrofitVersion: String by rootProject.extra
val httpLoggingVersion: String by rootProject.extra
val gsonVersion: String by rootProject.extra
val ktxCoreVersion: String by rootProject.extra
val materialVersion: String by rootProject.extra
val composeVersion: String by rootProject.extra
val ktxExtensionVersion: String by rootProject.extra
val mockitoVersion: String by rootProject.extra
val jUnitVersion: String by rootProject.extra
val navigationCompose: String by rootProject.extra

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.public_apis_list_showcase"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.public_apis_list_showcase"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:$ktxCoreVersion")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.activity:activity-compose:$composeVersion")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.navigation:navigation-compose:$navigationCompose")

    //Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    //  Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$httpLoggingVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")

    // kt
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-extensions:$ktxExtensionVersion")
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")

    // unit test
    testImplementation("junit:junit:$jUnitVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
}