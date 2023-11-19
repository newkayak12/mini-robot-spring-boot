import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.17"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    kotlin("kapt") version "1.7.10"
    idea
}

group = "com.project"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2021.0.8"
val snippetsDir by extra { file("build/generated-snippets") }


dependencies {
    val queryDslVersion = "5.0.0"


    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    // https://mvnrepository.com/artifact/com.nhaarman.mockitokotlin2/mockito-kotlin
    testImplementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0-alpha01")

    testImplementation("org.assertj:assertj-core:3.23.1")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.11")

    implementation ("com.querydsl:querydsl-jpa:${queryDslVersion}")
    kapt ("com.querydsl:querydsl-apt:${queryDslVersion}:jpa")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


idea {
    module {
        val kaptMain = file("build/generated/source/querydsl/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}
tasks {
    test {
        outputs.dir(snippetsDir)
    }

    asciidoctor {
        inputs.dir(snippetsDir)
        dependsOn(test)
        doFirst {
            delete {
                file("src/main/resources/static/docs")
            }
        }
    }

    register("copyHTML", Copy::class) {
        dependsOn(asciidoctor)
        from(file("build/asciidoc/html5"))
        into(file("src/main/resources/static/docs"))
    }

    build {
        dependsOn(getByName("copyHTML"))
    }

    bootJar {
        dependsOn(asciidoctor)
        dependsOn(getByName("copyHTML"))
    }
}