plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    id("maven-publish")
}

group = "io.github.elpafras"
version = project.findProperty("version") as? String ?: "2.1.0"

kotlin {
    android {
        namespace = "mr.cat.chat"
        compileSdk = 37
        minSdk = 26
        
        withJava()
        
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    iosArm64()
    iosSimulatorArm64()

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ChatEngine"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.androidx.datastore.preferences.core)
            implementation(libs.okio)
        }
        
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat)
        }
        
        iosMain.dependencies {
            // iOS specific dependencies
        }
    }

    android {
        namespace = "mr.cat.chat"
        compileSdk = 37
        minSdk = 26
        
        withJava()
        
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                name.set("ChatEngine")
                description.set("Library orkestrasi logika chat generik untuk Kotlin Multiplatform.")
                url.set("https://github.com/elpafras/LibCat")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("elpafras")
                        name.set("Dri Handoko")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            name = "LocalRepo"
            url = uri(rootProject.layout.buildDirectory.dir("repo"))
        }
    }
}
