package com.matijacvetan.docgenerator

import org.springframework.boot.test.context.TestConfiguration

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {
    // @Bean
    // @ServiceConnection
    // fun postgresContainer(): PostgreSQLContainer<*> = PostgreSQLContainer(DockerImageName.parse("postgres:latest"))

    // @Bean
    // @ServiceConnection(name = "redis")
    // fun redisContainer(): GenericContainer<*> = GenericContainer(DockerImageName.parse("redis:latest")).withExposedPorts(6379)
}
