plugins {
    id("net.neoforged.gradle.userdev") version "7.0.57"
}

group = "com.uchiha"
version = "1.0.0"
base.archivesName.set("uchiha-mod")

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.neoforged.net/releases/")
    }
}

dependencies {
    implementation("net.neoforged:neoforge:21.1.211")
}

tasks.named("jar", Jar::class) {
    from("LICENSE") {
        rename { "${it}_${base.archivesName.get()}" }
    }
}
