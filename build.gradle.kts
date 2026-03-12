import io.papermc.paperweight.tasks.TinyRemapper

plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "9.3.2"
    id("com.playmonumenta.paperweight-aw.userdev") version "2.0.0-build.5+2.0.0-beta.18" // from https://maven.playmonumenta.com/releases/
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    maven("https://maven.playmonumenta.com/releases/")
}

dependencies {
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")

    implementation("io.github.llamalad7:mixinextras-common:0.5.0")
    implementation("com.floweytf.fabricpaperloader:fabric-paper-loader:2.1.0+fabric.0.18.4")

    remapper("net.fabricmc:tiny-remapper:0.11.1") {
        artifact {
            classifier = "fat"
        }
    }
}

val include: Configuration by configurations.creating
val shade: Configuration by configurations.creating

shade.extendsFrom(include)
configurations {
    implementation { extendsFrom(include) }
    runtimeClasspath { extendsFrom(mojangMappedServerRuntime.get()) }
    runtimeClasspath { extendsFrom(mojangMappedServer.get()) }
}

tasks {
    jar {
        archiveClassifier.set("dev")
    }

    shadowJar {
        configurations = listOf(shade)
        archiveClassifier.set("dev")
    }

    reobfJar {
        remapperArgs = TinyRemapper.createArgsList() + "--mixin"
    }
}