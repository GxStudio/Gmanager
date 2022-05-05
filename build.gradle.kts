plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.10.0"
}

group = "cc.gxstudio"
version = "BETA"

repositories {
   // maven("https://maven.aliyun.com/repository/public") // 阿里云国内代理仓库
    mavenCentral()
}
dependencies {
    
    implementation("io.fusionauth:fusionauth-jwt:5.2.0")
    implementation("com.alibaba:fastjson:2.0.2")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.google.zxing:javase:3.5.0")
    
    implementation("io.ktor:ktor-server-core:2.0.1")
    implementation("io.ktor:ktor-server-netty:2.0.1")
    implementation("io.ktor:ktor-server-status-pages:2.0.1")
    implementation("io.ktor:ktor-server-default-headers:2.0.1")
//    implementation ("io.ktor:ktor-server-core:1.5.2")
//    implementation ("io.ktor:ktor-server-netty:1.5.2")
//    implementation ("io.ktor:ktor-serialization:1.5.2")
    testImplementation("io.ktor:ktor-server-test-host:2.0.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}