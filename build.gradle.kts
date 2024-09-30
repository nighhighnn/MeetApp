buildscript {
    val hiltVersion by extra("2.52") // Use the latest version

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}