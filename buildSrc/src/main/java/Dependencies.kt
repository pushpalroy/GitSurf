@Suppress("unused", "MayBeConstant")
object Dependencies {
  val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
  val junit = "junit:junit:${Versions.junit}"
  val support_appcompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
  val support_design = "com.google.android.material:material:${Versions.material}"
  val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"

  val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
  val lifecycle_view_model = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
  val lifecycle_live_data = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
  val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVersion}"

  val navigation_safe_args = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationSafeArgs}"
  val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
  val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
  val navigation_dynamic_feature = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigationVersion}"
  val navigation_testing = "androidx.navigation:navigation-testing:${Versions.navigationVersion}"

  val activity = "androidx.activity:activity-ktx:${Versions.activityVersion}"
  val fragment = "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"

  val room = "androidx.room:room-runtime:${Versions.roomVersion}"
  val room_compiler = "androidx.room:room-compiler:${Versions.roomVersion}"
  val room_ktx = "androidx.room:room-ktx:${Versions.roomVersion}"

  val paging = "androidx.paging:paging-runtime:${Versions.paging}"
  val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"

  val dagger = "com.google.dagger:dagger:${Versions.dagger}"
  val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
  val hilt_android_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_android}"
  val hilt_android = "com.google.dagger:hilt-android:${Versions.hilt_android}"
  val hilt_android_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt_android}"
  val hilt_viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt}"
  val hilt_compiler = "androidx.hilt:hilt-compiler:${Versions.hilt}"

  val arch_core_testing = "androidx.arch.core:core-testing:${Versions.archCoreTesting}"
  val mock_web_server = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"

  val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
  val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

  val rx_java = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
  val rx_android = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

  val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
  val retrofit_moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
  val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
  val moshi_adapter = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
  val ok_http =  "com.squareup.okhttp3:okhttp:${Versions.okHttpVersion}"
  val ok_http_logger =  "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpVersion}"
  val timber = "com.jakewharton.timber:timber:${Versions.timber}"
  val memory_leak_debug = "com.squareup.leakcanary:leakcanary-android:${Versions.memoryLeak}"
  val coil_kt = "io.coil-kt:coil:${Versions.coil}"
  val progress_button = "com.github.razir.progressbutton:progressbutton:${Versions.progressButton}"
  val base_adapter = "com.github.skydoves:baserecyclerviewadapter:${Versions.baseAdapter}"
  val android_veil = "com.github.skydoves:androidveil:${Versions.androidVeil}"

  val circularImageView = "de.hdodenhof:circleimageview:${Versions.circularImageView}"

  val three_ten_abp = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenApb}"
  val chuckDebug = "com.readystatesoftware.chuck:library:${Versions.chuck}"
  val chuckRelease = "com.readystatesoftware.chuck:library-no-op:${Versions.chuck}"

  val dbInspector = "im.dino:dbinspector:${Versions.dbInspector}"

  val mockito = "org.mockito:mockito-core:${Versions.mockito}"
  val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
  val mockito_android = "org.mockito:mockito-android:${Versions.mockito}"
  val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
  val jaxb_core = "com.sun.xml.bind:jaxb-core:${Versions.jaxbCore}"
  val jaxb_api = "javax.xml.bind:jaxb-api:${Versions.jaxbApi}"
  val jaxb_impl = "com.sun.xml.bind:jaxb-impl:${Versions.jaxbImpl}"
  val android_junit_runner = "androidx.test.runner.AndroidJUnitRunner"
}