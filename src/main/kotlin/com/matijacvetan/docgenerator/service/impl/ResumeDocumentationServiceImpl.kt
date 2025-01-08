package com.matijacvetan.docgenerator.service.impl

import com.matijacvetan.docgenerator.service.ResumeDocumentationService
import com.matijacvetan.docgenerator.service.impl.helper.DocumentGeneration.Companion.convertDocumentToPdfResponse
import com.matijacvetan.docgenerator.service.impl.helper.DocumentGeneration.Companion.generatePdfAndEncodeToBase64
import com.matijacvetan.docgenerator.service.impl.helper.DocumentGeneration.Companion.getResumeDocumentParametersWithPaths
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ResumeDocumentationServiceImpl : ResumeDocumentationService {
    private val logger = KotlinLogging.logger {}

    /**
     * This class provides the implementation for generating resume documents.
     *
     * @param language The language of the resume document.
     * @return A ResponseEntity containing the generated resume document as a byte array.
     */
    override fun generateResumeDocument(language: String): ResponseEntity<ByteArray> {
        logger.info { "Generating resume document for language: $language" }
        val documentBase64 = resumeDocumentGeneration()

        return convertDocumentToPdfResponse(documentBase64)
    }

    /**
     * Generates the resume document as a Base64 encoded string.
     *
     * @return the Base64 encoded string of the generated PDF document.
     * @throws IllegalStateException if the template path is not found.
     */
    private fun resumeDocumentGeneration(): String {
        val documentPath =
            this::class.java.getResource("/templates/mc.jasper")?.path
                ?: throw IllegalStateException("Template path not found")

        val params = getResumeDocumentParametersWithPaths()

        return generatePdfAndEncodeToBase64(documentPath, params)
    }
}
