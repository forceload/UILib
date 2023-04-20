plugins {
    id("fabric-loom").version(Dependency.Loom.Version)
    kotlin("jvm").version(Dependency.Kotlin.Version)
}

base { archivesName.set(project.extra["archives_base_name"] as String) }
version = project.extra["mod_version"] as String
group = project.extra["maven_group"] as String

val fabricLanguageKotlin =
    project.extra["fabric_language_kotlin_version"] as String + "+kotlin.${Dependency.Kotlin.Version}"

val yarnBuild =
    Dependency.Minecraft.Version + "+build.${Dependency.Yarn.Build}"

dependencies {
    minecraft("com.mojang", "minecraft", Dependency.Minecraft.Version)
    mappings("net.fabricmc", "yarn", yarnBuild, null, "v2")
    modImplementation("net.fabricmc", "fabric-loader", project.extra["loader_version"] as String)
    modImplementation("net.fabricmc.fabric-api", "fabric-api", project.extra["fabric_version"] as String)
    modImplementation("net.fabricmc", "fabric-language-kotlin", fabricLanguageKotlin)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-Beta")
}

tasks {
    val javaVersion = JavaVersion.toVersion(Dependency.Java.Version.toInt())
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
        options.release.set(javaVersion.toString().toInt())
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
        }
    }

    jar { from("LICENSE") { rename { "${it}_${base.archivesName}" } } }

    processResources {
        filesMatching("fabric.mod.json") {
            expand(
                mutableMapOf(
                    "version" to project.extra["mod_version"] as String,
                    "fabricloader" to project.extra["loader_version"] as String,
                    "fabric_api" to project.extra["fabric_version"] as String,
                    "fabric_language_kotlin" to fabricLanguageKotlin,
                    "minecraft" to Dependency.Minecraft.Version,
                    "java" to Dependency.Java.Version,

                    "mod_id" to project.extra["mod_id"] as String
                )
            )
        }

        filesMatching("*.mixins.json") {
            expand(mutableMapOf("java" to Dependency.Java.Version, "mod_id" to project.extra["mod_id"] as String))
        }

        filesMatching("**/*.json") {
            expand(mutableMapOf("mod_id" to project.extra["mod_id"] as String))
        }
    }

    java {
        toolchain { languageVersion.set(JavaLanguageVersion.of(javaVersion.toString())) }
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        withSourcesJar()
    }
}
