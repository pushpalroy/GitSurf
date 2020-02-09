buildscript {
  repositories {
    google()
    jcenter()
  }
  dependencies {
    classpath(Dependencies.gradle)
    classpath(Dependencies.kotlin_plugin)
  }
}

allprojects {
  repositories {
    google()
    jcenter()
  }
}

tasks {
  val clean by registering(Delete::class) {
    delete(buildDir)
  }
}