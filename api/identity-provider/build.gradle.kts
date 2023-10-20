plugins {
    id("plugins.kotlin-with-spring-boot-conventions")
}

dependencies {

    /**
     * Web
     */
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    implementation("org.webjars:webjars-locator-core")
    implementation("org.webjars:bootstrap:5.3.1")
    implementation("org.webjars:jquery:3.7.1")
    api(project(":oauth2-authorization"))
    implementation(project(":core:user:domain"))
    implementation(project(":core:user:infrastructure:persistence:jpa"))

    compileOnly("org.springframework.data:spring-data-jpa")
    testCompileOnly("org.springframework.data:spring-data-jpa")

    /**
     * Test
     */
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("net.sourceforge.htmlunit:htmlunit")
}
