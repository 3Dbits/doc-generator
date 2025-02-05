package com.matijacvetan.docgenerator.service.impl

import com.matijacvetan.docgenerator.service.ResumeDocumentationService
import com.matijacvetan.docgenerator.service.impl.helper.DocumentGeneration.Companion.convertDocumentToPdfResponse
import com.matijacvetan.docgenerator.service.impl.helper.DocumentGeneration.Companion.generatePdfAndEncodeToBase64
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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
        val templateStream =
            this::class.java.getResourceAsStream("/templates/mc.jasper")
                ?: throw IllegalStateException("Template not found")

        val formattedDateTime = formattedDateTimeNow()
        val params =
            mutableMapOf(
                "FooterBuildText" to
                    "Created on $formattedDateTime based on repository <a href=\"https://github.com/3Dbits/doc-generator\" target=\"_blank\" style=\"text-decoration: underline;\">doc-generator</a>.",
            )

        return generatePdfAndEncodeToBase64(templateStream, params)
    }

    private fun formattedDateTimeNow(): String? {
        val zoneId = ZoneId.of("Europe/Zagreb")
        val currentDateTime = ZonedDateTime.now(zoneId)
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        return currentDateTime.format(formatter)
    }
}
