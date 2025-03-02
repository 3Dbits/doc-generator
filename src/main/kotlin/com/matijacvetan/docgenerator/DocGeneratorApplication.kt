package com.matijacvetan.docgenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class DocGeneratorApplication

fun main(args: Array<String>) {
    runApplication<DocGeneratorApplication>(*args)
}
