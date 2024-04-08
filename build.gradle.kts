plugins {
    kotlin("jvm") version "1.9.23"
}

group = "me.lished"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(8)
}