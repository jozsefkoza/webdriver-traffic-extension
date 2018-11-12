plugins {
    `java-library`
}

dependencies {
    api(project(":api"))

    implementation("net.lightbody.bmp:browsermob-core:2.1.5")
    implementation("io.reactivex.rxjava2:rxjava:2.2.3")

    testImplementation("com.google.code.gson:gson:2.8.5")
}