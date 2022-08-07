rootProject.name = "cc.gxstudio.gmanager"
pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        
        gradlePluginPortal()
        maven("https://repo.mirai.mamoe.net/snapshots")
    }
}
plugins {
    id("com.gradle.enterprise") version("3.10.3")
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
    }
}