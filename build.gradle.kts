allprojects {
    group = "com.joezee.webdriver.extension"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

plugins {
    java
    checkstyle
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "checkstyle")

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_11
    }

    checkstyle {
        toolVersion = "8.14"
    }

    dependencies {
        implementation("org.slf4j:slf4j-api:1.7.25")
        runtime("ch.qos.logback:logback-classic:1.2.3")

        testImplementation("org.assertj:assertj-core:3.+")
        testImplementation("org.mockito:mockito-core:2.+")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.+")
        testRuntime("org.junit.jupiter:junit-jupiter-engine:5.+")
    }

    tasks {
        getByName<Test>("test", configure = {
            useJUnitPlatform()
        })
    }
}
