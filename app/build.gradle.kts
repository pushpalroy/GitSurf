import java.util.Properties

plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("android.extensions")
  kotlin("kapt")
  id("androidx.navigation.safeargs.kotlin")
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
    testInstrumentationRunner = Dependencies.android_junit_runner
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

  dataBinding {
    isEnabled = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  sourceSets["test"].java.srcDir("src/test-common/java")
}

tasks {
  fun getVersionName(): String {
    return "${Versions.versionMajor}.${Versions.versionMinor}.${Versions.versionPatch}"
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
  implementation(Dependencies.support_appcompat)
  implementation(Dependencies.support_design)
  implementation(Dependencies.constraint_layout)

  implementation(Dependencies.lifecycle_extensions)
  implementation(Dependencies.lifecycle_view_model)
  implementation(Dependencies.lifecycle_live_data)
  kapt(Dependencies.lifecycle_compiler)

  implementation(Dependencies.navigation_fragment)
  implementation(Dependencies.navigation_ui)
  implementation(Dependencies.navigation_dynamic_feature)

  implementation(Dependencies.room)
  implementation(Dependencies.room_ktx)
  kapt(Dependencies.room_compiler)

  implementation(Dependencies.paging)
  implementation(Dependencies.swipeRefresh)

  implementation(Dependencies.dagger)
  implementation(Dependencies.dagger_android)
  implementation(Dependencies.dagger_android_support)
  kapt(Dependencies.dagger_android_compiler)
  kapt(Dependencies.dagger_compiler)

  implementation(Dependencies.rx_java)
  implementation(Dependencies.rx_android)

  implementation(Dependencies.gson)
  implementation(Dependencies.retrofit)
  implementation(Dependencies.retrofit_gson)
  implementation(Dependencies.ok_http)
  implementation(Dependencies.ok_http_logger)

  implementation(Dependencies.timber)
  debugImplementation(Dependencies.memory_leak_debug)

  implementation(Dependencies.glide)
  kapt(Dependencies.glide_compiler)

  implementation(Dependencies.circularImageView)
  implementation(Dependencies.progress_button)

  debugImplementation(Dependencies.chuckDebug)
  releaseImplementation(Dependencies.chuckRelease)

  testImplementation(Dependencies.navigation_testing)
  testImplementation(Dependencies.arch_core_testing)
  testImplementation(Dependencies.mock_web_server)
  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.mockito)
  testImplementation(Dependencies.mockito_kotlin)
  androidTestImplementation(Dependencies.mockito_android)
  androidTestImplementation(Dependencies.espresso)
}