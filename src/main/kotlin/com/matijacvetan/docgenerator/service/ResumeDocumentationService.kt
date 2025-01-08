package com.matijacvetan.docgenerator.service

import org.springframework.http.ResponseEntity

fun interface ResumeDocumentationService {
    fun generateResumeDocument(language: String): ResponseEntity<ByteArray>
}
