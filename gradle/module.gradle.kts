apply(plugin = "java")
apply(plugin = "checkstyle")

fun DependencyHandler.testImplementation(dependency: Any) {
    add(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME, dependency)
}

fun DependencyHandler.testRuntimeOnly(dependency: Any) {
    add(JavaPlugin.TEST_RUNTIME_ONLY_CONFIGURATION_NAME, dependency)
}

fun DependencyHandler.implementation(dependency: Any) {
    add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, dependency)
}

dependencies {
    implementation("com.google.guava:guava:27.0-jre")

    testImplementation("org.assertj:assertj-core:3.+")
    testImplementation("org.mockito:mockito-core:2.+")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.+")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.+")
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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
