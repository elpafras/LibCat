plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

group = project.findProperty("group") as? String ?: "com.github.elpafras"
version = project.findProperty("version") as? String ?: "1.3.1"

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "LibCatSetting"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.datastore.preferences.core)
            implementation(libs.okio)
        }
        
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat)
            implementation(libs.material)
        }
        
        iosMain.dependencies {
            // iOS specific dependencies if any
        }
    }
}

android {
    namespace = "mr.cat.setting"
    compileSdk = 37

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// KMP plugin will automatically create publications for all targets.
// We only need to configure them if we need custom metadata.
