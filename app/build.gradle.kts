plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "io.mehow.nova"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.mehow.nova"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

androidComponents {
    beforeVariants { variantBuilder ->
        println(variantBuilder.buildType)
        variantBuilder.enable = variantBuilder.buildType == "debug"
    }
}

dependencies {
    implementation(libs.androidx.core)
}
