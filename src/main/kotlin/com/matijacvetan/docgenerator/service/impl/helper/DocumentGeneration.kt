package com.matijacvetan.docgenerator.service.impl.helper

import io.github.oshai.kotlinlogging.KotlinLogging
import net.sf.jasperreports.engine.JREmptyDataSource
import net.sf.jasperreports.engine.JRException
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.util.JRLoader
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.io.InputStream
import java.util.Base64

class DocumentGeneration private constructor() {
    companion object {
        private val logger = KotlinLogging.logger {}

        /**
         * Generates a PDF from the given template path and data source list, and encodes the PDF to Base64.
         *
         * @return Base64 encoded PDF as a String.
         * @throws JRException if there is an error during PDF generation or export.
         */
        fun generatePdfAndEncodeToBase64(
            templateStream: InputStream,
            params: Map<String, Any>? = null,
        ): String =
            try {
                val jasperReport = JRLoader.loadObject(templateStream) as JasperReport
                val jasperPrint =
                    JasperFillManager.fillReport(jasperReport, params, JREmptyDataSource())

                logger.info { "Exporting filled template to PDF" }
                val exportedPdfReport = JasperExportManager.exportReportToPdf(jasperPrint)

                logger.info { "Encoding PDF to Base64" }
                Base64.getEncoder().encodeToString(exportedPdfReport)
            } catch (e: JRException) {
                logger.error(e) { "Failed to generate PDF: " }
                throw e
            }

        /**
         * Converts a Base64-encoded PDF string to a ResponseEntity containing the PDF as a byte array.
         *
         * This function decodes the provided Base64 string into a byte array representing the PDF document.
         * It then constructs a ResponseEntity with appropriate headers to serve the PDF inline.
         *
         * @param base64String The Base64-encoded string of the PDF document.
         * @return A ResponseEntity containing the decoded PDF byte array.
         * @throws IllegalArgumentException if the Base64 string is invalid.
         */
        fun convertDocumentToPdfResponse(base64String: String): ResponseEntity<ByteArray> {
            val pdfBytes =
                try {
                    logger.info { "Decoding Base64 string to PDF bytes" }
                    Base64.getDecoder().decode(base64String)
                } catch (e: IllegalArgumentException) {
                    logger.error(e) { "Failed to decode Base64 string to PDF bytes, invalid Base64 scheme:" }
                    return ResponseEntity.status(500).body(null)
                }
            return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"MatijaCvetanCV.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes)
        }
    }
}
