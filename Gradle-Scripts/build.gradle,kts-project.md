plugins {
    alias(libs.plugins.android.application) apply false
    kotlin("jvm") version "1.8.22" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.15") // 최신 버전

    }
}
