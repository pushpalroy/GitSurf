buildscript {
  repositories {
    google()
    jcenter()
  }
  dependencies {
    classpath(Dependencies.gradle)
    classpath(Dependencies.kotlin_plugin)
    classpath(Navigation.navigation_safe_args)
    classpath(Hilt.hilt_android_plugin)
  }
}

allprojects {
  repositories {
    google()
    jcenter()
    maven(url = "https://jitpack.io")
  }
}

tasks {
  val clean by registering(Delete::class) {
    delete(buildDir)
  }
}