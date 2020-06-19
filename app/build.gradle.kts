import java.util.Properties

plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("android.extensions")
  kotlin("kapt")
  id("androidx.navigation.safeargs.kotlin")
  id("dagger.hilt.android.plugin")
}

val preDexEnabled = "true" == System.getProperty("pre-dex", "true")

android {
  compileSdkVersion(Versions.compileSdk)

  buildTypes {
    named("release") {
      isDebuggable = false
      versionNameSuffix = "-release"
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(
        getDefaultProguardFile(Versions.proguard_android), Versions.proguard_common,
        Versions.proguard_specific
      )
      buildConfigField("boolean", "ENABLE_LOGGING", "false")
    }
  }

  defaultConfig {
    applicationId = Versions.applicationId
    minSdkVersion(Versions.minSdk)
    targetSdkVersion(Versions.targetSdk)
    versionCode = Versions.versionCode
    versionName = Versions.versionName
    testInstrumentationRunner = Testing.android_junit_runner
    buildConfigField("String", "CLIENT_ID", "\"${loadPropertiesFile()["CLIENT_ID"]}\"")
    buildConfigField("String", "CLIENT_SECRET", "\"${loadPropertiesFile()["CLIENT_SECRET"]}\"")
    buildConfigField("String", "CALLBACK_URL", "\"${loadPropertiesFile()["CALLBACK_URL"]}\"")
  }

  dexOptions {
    preDexLibraries = preDexEnabled
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

tasks {
  fun getVersionName(): String {
    return "${Versions.verMajor}.${Versions.verMinor}.${Versions.verPatch}"
  }
}

fun loadPropertiesFile(): Properties {
  val propertiesFile = file("$rootDir/privateConfig/private.properties")
  val properties = Properties()
  if (propertiesFile.exists()) {
    properties.load(propertiesFile.inputStream())
  }
  return properties
}

dependencies {
  implementation(Dependencies.kotlin_stdlib)

  implementation(Android.support_appcompat)
  implementation(Android.support_design)
  implementation(Android.constraint_layout)
  implementation(Android.paging)
  implementation(Android.swipeRefresh)
  implementation(Android.activity)
  implementation(Android.fragment)

  implementation(Lifecycle.lifecycle_extensions)
  implementation(Lifecycle.lifecycle_view_model)
  implementation(Lifecycle.lifecycle_live_data)
  kapt(Lifecycle.lifecycle_compiler)

  implementation(Navigation.navigation_fragment)
  implementation(Navigation.navigation_ui)
  implementation(Navigation.navigation_dynamic_feature)

  implementation(Room.room)
  implementation(Room.room_ktx)
  kapt(Room.room_compiler)

  implementation(Hilt.hilt_android)
  implementation(Hilt.hilt_viewmodel)
  kapt(Hilt.hilt_android_compiler)
  kapt(Hilt.hilt_compiler)

  implementation(Rx.rx_java)
  implementation(Rx.rx_android)

  implementation(Networking.retrofit)
  implementation(Networking.ok_http)
  implementation(Networking.ok_http_logger)
  implementation(Networking.coil_kt)

  implementation(Moshi.moshi)
  implementation(Moshi.moshi_adapter)
  implementation(Moshi.retrofit_moshi)

  implementation(Util.three_ten_abp)
  implementation(Util.timber)
  debugImplementation(Util.memory_leak_debug)
  implementation(Util.circularImageView)
  implementation(Util.progress_button)
  debugImplementation(Util.chuckDebug)
  releaseImplementation(Util.chuckRelease)
  debugImplementation(Util.dbInspector)

  testImplementation(Navigation.navigation_testing)
  testImplementation(Testing.arch_core_testing)
  testImplementation(Testing.mock_web_server)
  testImplementation(Dependencies.junit)
  testImplementation(Mockito.mockito)
  testImplementation(Mockito.mockito_kotlin)
  androidTestImplementation(Mockito.mockito_android)
  androidTestImplementation(Testing.espresso)
}