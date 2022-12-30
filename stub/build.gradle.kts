plugins {
    id("com.android.library")
}

android {
    namespace = "stub"
    compileSdk = 33

    defaultConfig {
        minSdk = 33
        targetSdk = 33
    }

    buildFeatures {
        buildConfig = false
    }
}

dependencies {
    annotationProcessor("dev.rikka.tools.refine:annotation-processor:3.1.1")
    compileOnly("dev.rikka.tools.refine:annotation:3.1.1")
    implementation("androidx.annotation:annotation:1.5.0")
}