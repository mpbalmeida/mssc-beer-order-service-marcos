plugins {
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    java
    jacoco
}

group = "dev.marcosalmeida"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("br.com.faroltech:mssc-brewery-bom:1.0.2")
    }
}

val mapStructVersion = dependencyManagement.importedProperties["org.mapstruct.version"]
val lombokMapStructVersion = dependencyManagement.importedProperties["lombok.mapstruct.binding.version"]

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.mapstruct:mapstruct")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("mysql:mysql-connector-java")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:${mapStructVersion}")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:${lombokMapStructVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.isEnabled = false
    }
}

tasks.withType(JavaCompile::class) {
    options.compilerArgs = listOf(
            "-Amapstruct.defaultComponentModel=spring"
    )
}
