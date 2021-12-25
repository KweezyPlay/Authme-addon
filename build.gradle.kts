plugins {
    java
}

group = "com.kweezy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    compileOnly(files("libs/spigot152.jar"))
    compileOnly(files("libs/AuthMe-5.2-SNAPSHOT-legacy.jar"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}