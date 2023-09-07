plugins {
	id("plugins.kotlin-with-spring-boot-conventions")
}

dependencies {

	/**
	 * Web
	 */
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
	implementation("org.webjars:webjars-locator-core")
	implementation("org.webjars:bootstrap:5.3.1")
	implementation("org.webjars:jquery:3.7.1")

	/**
	 * Test
	 */
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("net.sourceforge.htmlunit:htmlunit")
}