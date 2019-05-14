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
	annotationProcessor("org.projectlombok:lombok")
	
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.codehaus.groovy:groovy-all:2.5.6")
	implementation("org.projectlombok:lombok")
	implementation("org.postgresql:postgresql")


	/*implementation("org.aspectj:aspectjrt")
	implementation("com.h2database:h2")
	implementation("cglib:cglib-nodep:3.2.9")
	implementation("org.aspectj:aspectjweaver")*/

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.spockframework:spock-spring:1.3-groovy-2.5")
	testImplementation("org.testcontainers:spock:1.11.2")
	testImplementation("org.testcontainers:postgresql:1.11.2")
}

tasks.withType(Wrapper::class.java).configureEach {
	gradleVersion = "5.4.1"
}
