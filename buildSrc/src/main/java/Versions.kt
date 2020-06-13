@file:Suppress("MemberVisibilityCanBePrivate")

object Versions {
  const val versionMajor = 1
  const val versionMinor = 0
  const val versionPatch = 0
  const val versionBuild = 0

  const val compileSdk = 29
  const val minSdk = 24
  const val targetSdk = 29
  const val versionCode =
    versionMajor * 10000 + versionMinor * 1000 + versionPatch * 100 + versionBuild
  const val versionName = "$versionMajor.$versionMinor.$versionPatch.$versionBuild"

  const val proguard_android = "proguard-android.txt"
  const val proguard_common = "proguard-common.txt"
  const val proguard_specific = "proguard-specific.txt"
  const val applicationId = "com.gitsurfer.gitsurf"

  const val gradle = "4.0.0"
  const val junit = "4.12"
  const val appCompat = "1.1.0"
  const val constraint = "1.1.3"
  const val material = "1.1.0"
  const val kotlin = "1.3.50"
  const val activityVersion = "1.1.0"
  const val fragmentVersion = "1.2.3"
  const val lifecycleVersion = "2.2.0"
  const val navigationVersion = "2.3.0-alpha06"
  const val navigationSafeArgs = "2.2.2"
  const val roomVersion = "2.2.5"
  const val paging = "2.1.2"
  const val swipeRefresh = "1.0.0"
  const val archCoreTesting = "2.1.0"
  const val dagger = "2.28"
  const val hilt_android = "2.28-alpha"
  const val hilt = "1.0.0-alpha01"
  const val rxJava = "2.2.11"
  const val rxAndroid = "2.1.1"
  const val retrofit = "2.9.0"
  const val okHttpVersion = "4.2.1"
  const val moshi = "1.9.2"
  const val timber = "4.7.1"
  const val memoryLeak = "2.0-beta-2"
  const val progressButton = "2.0.1"
  const val glide = "4.9.0"
  const val baseAdapter = "0.1.3"
  const val androidVeil = "1.0.6"
  const val circularImageView = "3.1.0"
  const val threeTenApb = "1.2.4"
  const val chuck = "1.1.0"
  const val dbInspector = "4.0.0"
  const val mockito = "3.0.0"
  const val mockitoKotlin = "2.1.0"
  const val mockWebServer = "3.14.0"
  const val espresso = "3.0.2"
  const val jaxbCore = "2.3.0.1"
  const val jaxbApi = "2.3.1"
  const val jaxbImpl = "2.3.2"
}