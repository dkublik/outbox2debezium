subprojects {

	group = "pl.dk"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

tasks.withType(Wrapper::class.java).configureEach {
	gradleVersion = "5.4.1"
}
