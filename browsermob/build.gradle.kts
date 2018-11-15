description = "Traffic sniffing extension for WebDriver with BrowserMob proxy"

plugins {
    `java-library`
}

dependencies {
    api(project(":api"))

    implementation("net.lightbody.bmp:browsermob-core:2.+")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("com.google.guava:guava:27.0-jre")
    implementation("io.reactivex.rxjava2:rxjava:2.2.3")

    testImplementation("com.google.code.gson:gson:2.8.5")
}
