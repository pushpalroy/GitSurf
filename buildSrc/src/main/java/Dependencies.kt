object Dependencies {
  const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
  const val junit = "junit:junit:${Versions.junit}"
  const val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
  const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object Android {
  const val support_appcompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
  const val support_design = "com.google.android.material:material:${Versions.material}"
  const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"

  const val activity = "androidx.activity:activity-ktx:${Versions.activityVersion}"
  const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"

  const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
  const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"
}

object Hilt {
  const val hilt_android_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_android}"
  const val hilt_android = "com.google.dagger:hilt-android:${Versions.hilt_android}"
  const val hilt_android_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt_android}"
  const val hilt_viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt}"
  const val hilt_compiler = "androidx.hilt:hilt-compiler:${Versions.hilt}"
}

object Navigation {
  const val navigation_safe_args = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationSafeArgs}"
  const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
  const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
  const val navigation_dynamic_feature = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigationVersion}"
  const val navigation_testing = "androidx.navigation:navigation-testing:${Versions.navigationVersion}"
}

object Room {
  const val room = "androidx.room:room-runtime:${Versions.roomVersion}"
  const val room_compiler = "androidx.room:room-compiler:${Versions.roomVersion}"
  const val room_ktx = "androidx.room:room-ktx:${Versions.roomVersion}"
}

object Lifecycle {
  const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
  const val lifecycle_view_model = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
  const val lifecycle_live_data = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
  const val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVersion}"
}

object Networking {
  const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
  const val ok_http =  "com.squareup.okhttp3:okhttp:${Versions.okHttpVersion}"
  const val ok_http_logger =  "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpVersion}"
  const val coil_kt = "io.coil-kt:coil:${Versions.coil}"
}

object Moshi {
  const val retrofit_moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
  const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
  const val moshi_adapter = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
}

object Rx {
  const val rx_java = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
  const val rx_android = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
}

object Mockito {
  const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
  const val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
  const val mockito_android = "org.mockito:mockito-android:${Versions.mockito}"
}

object Testing {
  const val arch_core_testing = "androidx.arch.core:core-testing:${Versions.archCoreTesting}"
  const val mock_web_server = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
  const val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
  const val android_junit_runner = "androidx.test.runner.AndroidJUnitRunner"
}

object Util {
  const val three_ten_abp = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenApb}"
  const val chuckDebug = "com.readystatesoftware.chuck:library:${Versions.chuck}"
  const val chuckRelease = "com.readystatesoftware.chuck:library-no-op:${Versions.chuck}"
  const val dbInspector = "im.dino:dbinspector:${Versions.dbInspector}"
  const val progress_button = "com.github.razir.progressbutton:progressbutton:${Versions.progressButton}"
  const val circularImageView = "de.hdodenhof:circleimageview:${Versions.circularImageView}"
  const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
  const val memory_leak_debug = "com.squareup.leakcanary:leakcanary-android:${Versions.memoryLeak}"
}