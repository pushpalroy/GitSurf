plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("android.extensions")
  kotlin("kapt")
}

android {
  compileSdkVersion(Versions.compileSdk)

  buildTypes {
    named("release") {
      isDebuggable = false
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile(Versions.proguard_android), Versions.proguard_common,
        Versions.proguard_specific
      )
    }

    defaultConfig {
      minSdkVersion(Versions.minSdk)
      targetSdkVersion(Versions.targetSdk)
      versionCode = 1
      versionName = "1.0"
      testInstrumentationRunner = Testing.android_junit_runner
    }

    kapt {
      correctErrorTypes = true
    }

    buildFeatures {
      dataBinding = true
    }

    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_1_8
      targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets["test"].java.srcDir("src/test-common/java")
  }
}

dependencies {
  implementation(Dependencies.kotlin_stdlib)
  implementation(Android.support_appcompat)
  testImplementation(Dependencies.junit)
  androidTestImplementation(Testing.espresso)
}