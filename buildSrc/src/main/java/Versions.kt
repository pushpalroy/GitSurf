@file:Suppress("MemberVisibilityCanBePrivate")

object Versions {
  const val verMajor = 1
  const val verMinor = 0
  const val verPatch = 0
  const val verBuild = 0

  const val compileSdk = 29
  const val minSdk = 24
  const val targetSdk = 29
  const val versionCode = verMajor * 10000 + verMinor * 1000 + verPatch * 100 + verBuild
  const val versionName = "$verMajor.$verMinor.$verPatch.$verBuild"

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
  const val hilt_android = "2.28-alpha"
  const val hilt = "1.0.0-alpha01"
  const val rxJava = "2.2.11"
  const val rxAndroid = "2.1.1"
  const val retrofit = "2.9.0"
  const val okHttpVersion = "4.2.1"
  const val moshi = "1.9.2"
  const val timber = "4.7.1"
  const val memoryLeak = "2.0-beta-2"
  const val markdown_view = "1.0.8"
  const val progressButton = "2.0.1"
  const val coil = "0.11.0"
  const val circularImageView = "3.1.0"
  const val threeTenApb = "1.2.4"
  const val chuck = "1.1.0"
  const val dbInspector = "4.0.0"
  const val mockito = "3.0.0"
  const val mockitoKotlin = "2.1.0"
  const val mockWebServer = "3.14.0"
  const val espresso = "3.0.2"
}