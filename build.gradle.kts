plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"

}

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("libs/parcs.jar"))
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.github.msemitkin.Main"
        )
    }
}
