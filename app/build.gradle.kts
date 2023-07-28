plugins {
	kotlin("jvm") version "1.6.10"
	application
}

repositories {
	mavenCentral()
}

dependencies {
	// BOMs
	implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

application {
	mainClass.set("main.MainKt")
}

tasks.getByName<JavaExec>("run") {
	standardInput = System.`in`
}