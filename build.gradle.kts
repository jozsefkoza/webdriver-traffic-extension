buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.+")
    }
}

allprojects {
    group = "com.joezee.webdriver.extension"
    version = "0.1"

    repositories {
        mavenCentral()
    }

    ext {
        set("githubMetadata", GithubMetadata("jozsefkoza/webdriver-traffic-sniffer-extension"))
        set("githubRepository", "jozsefkoza/webdriver-traffic-sniffer-extension")
        set("github.projectUrl", "https://github.com/${ext["githubRepository"]}")
        set("githubVcsUrl", "https://github.com/${ext["githubRepository"]}.git")
    }
}

subprojects {
    apply(from = "$rootDir/module.gradle.kts")
}

data class GithubMetadata(val repositoryName: String) {
    val baseUrl = "https://github.com"
    fun url(): String = "$baseUrl/$repositoryName"
    fun vcsUrl(): String = "${url()}.git"
    fun issueTrackerUrl(): String = "${url()}/issues"
}
