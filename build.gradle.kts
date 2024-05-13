buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath(libs.navigation.safe.args)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.kotlinKapt) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.navigation.plugin) apply false
}