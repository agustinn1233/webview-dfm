plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.mercadopago.wallet"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mercadopago.wallet"
        minSdk = 23
        targetSdk = 35
        versionCode = 1816180603
        versionName = "2.359.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isDebuggable = true
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
    dynamicFeatures += setOf(":meliphone")

    bundle {
        language {
            enableSplit = false
        }
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.feature.delivery)
    implementation(libs.androidx.emoji2)
    implementation(libs.androidx.emoji2.views)
    implementation(libs.androidx.emoji2.views.helper)
    implementation("androidx.fragment:fragment-ktx:1.8.5")
}