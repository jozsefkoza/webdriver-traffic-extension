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

    repositories {
        mavenCentral()
    }
}

val releaseTag = findProperty("releaseTag")?.let { ReleaseTag(it as String) }

subprojects {
    apply(from = "$rootDir/gradle/module.gradle.kts")

    releaseTag?.let {
        if (it.project.isNullOrEmpty() || it.project == this.name) {
            ext.set("releaseVersion", it.version)
        }
    }

    apply(from = "$rootDir/gradle/release.gradle.kts")
}

class ReleaseTag(tag: String) {
    val project: String?
    val version: String
    private val tagPattern = Regex("""^(?:([a-z]+):)?(?:v(\d+.\d+.\d+))${'$'}""")

    init {
        val match = tagPattern.matchEntire(tag)
            ?: throw GradleException("releaseTag is expected to be in '(<project>:)?v<semanticVersion>' format")
        project = match.groupValues[1].let {
            if (findProject(":$it") == null) {
                throw GradleException("Project '$this' specified in releaseTag does not exist")
            }
            it
        }
        version = match.groupValues[2]
    }
}
