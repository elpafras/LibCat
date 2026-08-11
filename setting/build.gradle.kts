plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.compose)
    id("com.vanniktech.maven.publish") version "0.37.0"
    id("signing")
}

group = "io.github.elpafras"
version = project.findProperty("version") as? String ?: "2.0.0"

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "LibCat"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.components.ui.tooling.preview)
            
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.lifecycle.runtime.compose)
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

    android {
        namespace = "mr.cat.setting"
        compileSdk = 37
        minSdk = 26
        
        androidResources {
            enable = true
        }
        
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
}

mavenPublishing {
    coordinates("io.github.elpafras", "libcat", version.toString())
    
    publishToMavenCentral()
    signAllPublications()
    
    pom {
        name.set("LibCat")
        description.set(
            "Kotlin Multiplatform library untuk manajemen tema (font, " +
            "ukuran font, color scheme) yang tersinkronisasi secara " +
            "reaktif ke UI native Compose Multiplatform dan konten " +
            "WebView, berjalan di Android dan iOS."
        )
        url.set("https://github.com/elpafras/LibCat")
        
        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
                // Menggunakan distribution.set jika tersedia, atau lewatkan jika terjadi konflik resolusi
            }
        }
        
        developers {
            developer {
                id.set("elpafras")
                name.set("Dri Handoko")
                url.set("https://github.com/elpafras")
            }
        }
        
        scm {
            url.set("https://github.com/elpafras/LibCat")
            connection.set("scm:git:git://github.com/elpafras/LibCat.git")
            developerConnection.set(
                "scm:git:ssh://git@github.com/elpafras/LibCat.git"
            )
        }
    }
}


signing {
    // Menggunakan binary GPG sistem (gpg command) untuk menghindari masalah parsing
    // private key in-memory (Bouncy Castle) pada format GnuPG modern (AEAD/OCB).
    useGpgCmd()
}
