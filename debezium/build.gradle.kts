plugins {
	java
	groovy
	id("org.springframework.boot") version "2.1.4.RELEASE"
}

apply(plugin = "io.spring.dependency-management")

configure<JavaPluginConvention> {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
	annotationProcessor("org.projectlombok:lombok")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.codehaus.groovy:groovy-all:2.5.6")
	implementation("org.projectlombok:lombok")
	implementation("org.postgresql:postgresql")

	implementation("io.debezium:debezium-embedded:0.9.5.Final")
	implementation("io.debezium:debezium-connector-postgres:0.9.5.Final")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.spockframework:spock-spring:1.3-groovy-2.5")
	testImplementation("org.testcontainers:spock:1.11.2")
	testImplementation("org.testcontainers:postgresql:1.11.2")
	testImplementation("org.awaitility:awaitility-groovy:3.1.6")
}
