plugins {
    id("fabric-loom").version(Dependency.Loom.Version)
    kotlin("jvm").version(Dependency.Kotlin.Version)
    id("org.jetbrains.dokka") version "1.8.10"
    `maven-publish`
    signing
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

    create<Jar>("dokkaJar") {
        archiveClassifier.set("javadoc")
        dependsOn("dokkaHtml")

        from("$buildDir/dokka/html/") {
            include("**")
        }
    }

    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.extra["maven_group"] as String
            artifactId = project.extra["mod_id"] as String
            version = project.extra["mod_version"] as String

            from(components["java"])

            pom {
                name.set("UILib")
                description.set("Simple Minecraft Fabric Mod Loader UI Library")
                url.set("https://github.com/forceload/uilib")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/license/mit")
                    }
                }

                developers {
                    developer {
                        id.set("forceload")
                        name.set("Project Forceload")
                        email.set("forceload.official@gmail.com")
                        url.set("https://github.com/forceload")
                        timezone.set("Asia/Seoul")
                    }
                }

                val modId = project.extra["mod_id"] as String
                scm {
                    connection.set("scm:git:git://github.com/forceload/${modId}.git")
                    developerConnection.set("scm:git:ssh://github.com:forceload/${modId}.git")
                    url.set("https://github.com/forceload/${modId}")
                }
            }
        }
    }

    repositories {
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/releases/")
            credentials {
                username = "forceload"
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
