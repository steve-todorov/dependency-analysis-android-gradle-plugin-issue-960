import com.autonomousapps.tasks.GraphViewTask

plugins {
    id("java")
    id("com.autonomousapps.dependency-analysis") version "1.22.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.4")
}

tasks {

    val copyDockerResourcesTask = create<Copy>("copyDockerResourcesTask") {
        from("${projectDir}/src/main/docker")
        destinationDir = project.layout.buildDirectory.get().asFile
        outputs.cacheIf { false }
        outputs.upToDateWhen { false }
    }

    val processResources = named("processResources") {
        dependsOn(copyDockerResourcesTask)
    }

    // Adding this should have fixed the problem, but Gradle says it can't find the task
    named<GraphViewTask>("graphViewWebappResource") {
        dependsOn(processResources)
    }

}

