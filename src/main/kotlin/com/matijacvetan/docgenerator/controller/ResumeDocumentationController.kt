package com.matijacvetan.docgenerator.controller

import com.matijacvetan.docgenerator.controller.specification.ResumeDocumentation
import com.matijacvetan.docgenerator.service.ResumeDocumentationService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class ResumeDocumentationController(
    var resumeDocumentationService: ResumeDocumentationService,
) : ResumeDocumentation {
    override fun getResumeDocumentation(language: String): ResponseEntity<ByteArray> =
        resumeDocumentationService.generateResumeDocument(language)
}
