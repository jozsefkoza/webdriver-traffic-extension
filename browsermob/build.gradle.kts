description = "Browsermob proxy based traffic sniffer for WebDriver"
version = "0.1-SNAPSHOT"

plugins {
    `java-library`
}

dependencies {
    api(project(":api"))

    implementation("net.lightbody.bmp:browsermob-core:2.1.5")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("io.reactivex.rxjava2:rxjava:2.2.3")

    testImplementation("com.google.code.gson:gson:2.8.5")
}
