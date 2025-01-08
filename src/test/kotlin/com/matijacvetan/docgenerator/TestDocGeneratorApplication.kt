package com.matijacvetan.docgenerator

import org.springframework.boot.fromApplication
import org.springframework.boot.with

fun main(args: Array<String>) {
    fromApplication<DocGeneratorApplication>().with(TestcontainersConfiguration::class).run(*args)
}
