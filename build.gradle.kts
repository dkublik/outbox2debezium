import org.gradle.api.JavaVersion.VERSION_11

plugins {
	java
	groovy
	id("org.springframework.boot") version "2.1.4.RELEASE"
}


apply(plugin = "io.spring.dependency-management")

group = "pl.dk"
version = "0.0.1-SNAPSHOT"

configure<JavaPluginConvention> {
	sourceCompatibility = VERSION_11
	targetCompatibility = VERSION_11
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
