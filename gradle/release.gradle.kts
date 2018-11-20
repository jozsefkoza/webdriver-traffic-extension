import com.jfrog.bintray.gradle.BintrayExtension
import groovy.util.Node
import groovy.util.NodeList

fun Node.getFirst(name: String) = (this.get(name) as NodeList)[0] as Node

fun Node.getAll(name: String): Iterable<Node> {
    return (this.get(name) as NodeList).asIterable() as Iterable<Node>
}

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.+")
    }
}

apply(plugin = "maven-publish")
apply(plugin = "com.jfrog.bintray")

val releaseName = "traffic-sniffer-${project.name}"
val releaseVersion = fun(): String = (findProperty("releaseVersion") ?: project.version) as String
val releaseMode = findProperty("releaseMode")?.let {
    it as String
    ReleaseMode.valueOf(it.toUpperCase())
} ?: ReleaseMode.DEV

val repositoryHost = "github.com"
val repositoryName = "jozsefkoza/webdriver-traffic-sniffer"
val projectUrl = "https://$repositoryHost/$repositoryName"
val projectVcsUrl = "$projectUrl.git"
val projectIssueTrackerUrl = "$projectUrl/issues"

val sourcesJar by tasks.getting
val javadocJar by tasks.getting

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("releaseJar") {
            from(components["java"])

            artifact(sourcesJar)
            artifact(javadocJar)

            artifactId = releaseName

            afterEvaluate {
                this@create.version = releaseVersion()
                this@create.pom.description.set(project.description)
            }

            pom {
                url.set(projectUrl)
                scm {
                    url.set(projectUrl)
                    connection.set("scm:git:$projectVcsUrl")
                    developerConnection.set("scm:git:git@$repositoryHost:$repositoryName.git")
                }
                issueManagement {
                    system.set("GitHub Issues")
                    url.set(projectIssueTrackerUrl)
                }
                licenses {
                    license {
                        name.set("Apache License, Version 2.0")
                        distribution.set("repo")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("jkoza")
                        name.set("Jozsef Koza")
                        email.set("jozsef.koza@gmail.com")
                    }
                }
                withXml {
                    asNode().getFirst("dependencies")
                        .getAll("dependency")
                        .filter {
                            val artifactId = it.getFirst("artifactId").text()
                            configurations[JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME].allDependencies
                                .find { it.name == artifactId }
                                .let { true }
                        }
                        .map { it.getFirst("scope") }
                        .filter { it.text() == "runtime" }
                        .forEach { it.setValue("compile") }
                }
            }
        }
    }
}

val install by tasks.creating {
    group = tasks.getByName("publish").group
    description = "Installs artifacts to various sources based on 'releaseMode' property."
}

if (releaseMode == ReleaseMode.DEV) {
    install.finalizedBy(tasks.getByName("publishToMavenLocal"))
}

if (releaseMode == ReleaseMode.RELEASE && hasProperty("releaseVersion")) {
    configure<BintrayExtension> {
        user = findProperty("bintray.user") as String?
        key = findProperty("bintray.apiKey") as String?

        setPublications("releaseJar")

        publish = true

        pkg = PackageConfig().apply {
            repo = "maven"
            name = releaseName

            websiteUrl = projectUrl
            vcsUrl = projectVcsUrl
            issueTrackerUrl = projectIssueTrackerUrl
            githubRepo = repositoryName

            setLicenses("Apache-2.0")

            afterEvaluate {
                this@apply.desc = project.description

                this@apply.version = VersionConfig().apply {
                    name = releaseVersion()
                    vcsTag = name
                }
            }
        }
    }

    install.finalizedBy(tasks.getByName("bintrayUpload"))
}

enum class ReleaseMode {
    DEV, RELEASE
}
