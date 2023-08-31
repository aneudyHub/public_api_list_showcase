// Top-level build file where you can add configuration options common to all sub-projects/modules.
val kotlinVersion by extra { "1.9.10" }
val retrofitVersion by extra { "2.6.0" }
val httpLoggingVersion by extra { "3.12.0" }
val gsonVersion by extra { "2.8.9" }
val coroutinesVersion by extra { "1.5.0" }
val hiltVersion by extra { "2.46.1" }
val lifecycleVersion by extra { "2.5.1" }
val ktxCoreVersion by extra { "1.10.1" }
val composeVersion by extra { "1.7.2" }
val materialVersion by extra { "1.9.0" }
val ktxExtensionVersion by extra { "2.2.0" }
val mockitoVersion by extra { "3.+" }
val jUnitVersion by extra { "4.13.2" }
val navigationComposeVersion by extra { "2.7.1" }
val mockServerVersion by extra { "3.12.0" }
val testRunnerVersion by extra { "1.5.2" }

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.dagger.hilt.android") version "2.46.1" apply false
}
