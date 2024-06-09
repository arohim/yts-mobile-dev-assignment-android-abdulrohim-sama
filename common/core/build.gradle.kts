plugins {
    id("ytstest.android.library")
    id("com.google.devtools.ksp")
    id("ytstest.android.hilt")
}

android {
    namespace = "com.him.sama.ytstest.common.core"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    api(libs.androidx.compose.runtime.livedata.ktx)
    api(libs.androidx.lifecycle.viewmodel.compose)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}