plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.google.services)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.habitsapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.habitsapp"
        minSdk = 24
        targetSdk = 36
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
        compose = true
    }
}

dependencies {

    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0") {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0") {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }

    coreLibraryDesugaring (libs.desugar.jdk.libs)

    implementation (libs.core)
    implementation (libs.clock)

    // Compose Navigation
    implementation (libs.androidx.navigation.compose)

    // Firebase
    implementation (platform(libs.firebase.bom))
    implementation (libs.firebase.analytics.ktx)
    implementation (libs.firebase.auth.ktx)
    implementation (libs.play.services.auth)

    // Dagger Hilt
    implementation (libs.hilt.android)
    ksp (libs.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)
    ksp (libs.androidx.hilt.compiler)

    // Coil
    implementation (libs.coil.compose)

    // Pager
    implementation (libs.accompanist.pager)
    implementation (libs.google.accompanist.pager.indicators)

    // Permissions
    implementation (libs.accompanist.permissions)

    // Room
    implementation (libs.androidx.room.ktx)
    ksp (libs.androidx.room.compiler)
    implementation (libs.androidx.room.runtime)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.logging.interceptor)

    // WorkManager
    implementation (libs.androidx.work.runtime.ktx)
    implementation (libs.androidx.hilt.work)

    // Testing
    testImplementation (libs.junit)
    testImplementation (libs.kotlinx.coroutines.test)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.espresso.core.v351)
    androidTestImplementation (libs.ui.test.junit4)
    kspAndroidTest (libs.hilt.android.compiler)
    debugImplementation (libs.ui.tooling)
    debugImplementation (libs.ui.test.manifest)
    testImplementation (libs.turbine)
    androidTestImplementation (libs.hilt.android.testing)
    testImplementation (libs.mockk)
    androidTestImplementation (libs.androidx.work.testing)

    //CORe
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.material.icons.extended)
}