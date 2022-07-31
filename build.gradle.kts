plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    
    id("net.mamoe.mirai-console") version "2.13.0-dev-057708e6"
}

group = "cc.gxstudio"
version = "BETA"

repositories {
    maven("https://maven.aliyun.com/repository/public") // 阿里云国内代理仓库
    mavenLocal()
    mavenCentral()
    
    maven("https://repo.mirai.mamoe.net/snapshots")
}
dependencies {
    implementation("io.ktor:ktor-server-core-jvm:2.0.2")
    implementation("io.ktor:ktor-server-netty-jvm:2.0.2")
    testImplementation("io.ktor:ktor-server-test-host-jvm:2.0.2")
    
    
    implementation("io.fusionauth:fusionauth-jwt:5.2.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    
    implementation("com.google.zxing:core:3.5.0")
    implementation("com.google.zxing:javase:3.5.0")
    
    implementation("io.ktor:ktor-server-status-pages:2.0.2")
    implementation("io.ktor:ktor-server-default-headers:2.0.2")
//    implementation ("io.ktor:ktor-server-core:1.5.2")
//    implementation ("io.ktor:ktor-server-netty:1.5.2")
//    implementation ("io.ktor:ktor-serialization:1.5.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("mysql:mysql-connector-java:8.0.29")
    compileOnly("net.luckperms:api:5.4")
}