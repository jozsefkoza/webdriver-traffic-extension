import com.jfrog.bintray.gradle.BintrayExtension

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.+")
    }
}

apply(plugin = "java")
apply(plugin = "checkstyle")
apply(plugin = "maven-publish")
apply(plugin = "com.jfrog.bintray")

fun DependencyHandler.testImplementation(dependency: Any) {
    add(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME, dependency)
}

fun DependencyHandler.testRuntimeOnly(dependency: Any) {
    add(JavaPlugin.TEST_RUNTIME_ONLY_CONFIGURATION_NAME, dependency)
}

dependencies {
    testImplementation("org.assertj:assertj-core:3.+")
    testImplementation("org.mockito:mockito-core:2.+")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.+")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.+")
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_11
}

configure<CheckstyleExtension> {
    toolVersion = "8.14"
}

tasks.getting(Test::class) {
    useJUnitPlatform()
}

val javadoc by tasks.getting(Javadoc::class) {
    isFailOnError = false
}

val sourcesJar by tasks.creating(Jar::class) {
    dependsOn(tasks.getByName("classes"))
    from(project.the<SourceSetContainer>()["main"].allSource)
    classifier = "sources"
}

val javadocJar by tasks.creating(Jar::class) {
    dependsOn(javadoc)
    from(javadoc.destinationDir)
    classifier = "javadoc"
}

val githubMetadata = GithubMetadata()

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("releaseJar") {
            from(components["java"])

            afterEvaluate {
                artifactId = "${rootProject.name}-${project.name}"
            }

            artifact(sourcesJar)
            artifact(javadocJar)

            pom {
                description.set(project.description)
                url.set(githubMetadata.projectUrl())
                scm {
                    url.set(githubMetadata.projectUrl())
                    connection.set("scm:git:${githubMetadata.vcsUrl()}")
                    developerConnection.set("scm:git:git@github.com:${githubMetadata.repository}.git")
                }
                issueManagement {
                    system.set("GitHub Issues")
                    url.set(githubMetadata.issueTrackerUrl())
                }
                licenses {
                    license {
                        name.set("Apache License, Version 2.0")
                        distribution.set("repo")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }
            }
        }
    }
}

configure<BintrayExtension> {
    user = properties["bintray.user"] as String
    key = properties["bintray.apiKey"] as String

    setPublications("releaseJar")

    publish = true
    override = true

    pkg = PackageConfig().apply {
        repo = "maven"
        name = "${rootProject.name}-${project.name}"

        afterEvaluate {
            desc = project.description
        }

        websiteUrl = githubMetadata.projectUrl()
        vcsUrl = githubMetadata.vcsUrl()
        issueTrackerUrl = githubMetadata.issueTrackerUrl()
        githubRepo = githubMetadata.repository

        setLicenses("Apache-2.0")
    }
}

class GithubMetadata {
    private val baseUrl = "https://github.com"
    val repository = "jozsefkoza/webdriver-traffic-sniffer-extension"
    fun projectUrl(): String = "$baseUrl/$repository"
    fun vcsUrl(): String = "${projectUrl()}.git"
    fun issueTrackerUrl(): String = "${projectUrl()}/issues"
}
