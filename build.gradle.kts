import org.hidetake.groovy.ssh.connection.AllowAnyHosts
import org.hidetake.groovy.ssh.core.Remote
import org.hidetake.groovy.ssh.core.RunHandler
import org.hidetake.groovy.ssh.session.SessionHandler
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    id ("org.hidetake.ssh") version  "2.11.2"
}

group = "zinc.doiche"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.bootJar {
    this.archiveFileName.set("AnifAdmin.jar")
}

tasks.create(name = "deployBootJar") {
    dependsOn("bootJar")

    val server = Remote(
        mapOf<String, Any>(
            "host" to project.property("publicHost") as String,
            "port" to (project.property("publicPort") as String).toInt(),
            "user" to project.property("publicUser") as String,
            "password" to project.property("publicPassword") as String,
            "knownHosts" to AllowAnyHosts.instance
        )
    )

    doLast {
        ssh.run(delegateClosureOf<RunHandler> {
            session(server, delegateClosureOf<SessionHandler> {
                val file = tasks.getByName<BootJar>("bootJar").archiveFile.get().asFile
                val directory = project.property("publicDirectory") as String

                put(
                    hashMapOf(
                        "from" to file,
                        "into" to directory
                    )
                )
            })
        })
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
//    implementation("com.google.code.gson:gson:2.10.1")
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("net.dv8tion:JDA:5.0.0-beta.21")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
