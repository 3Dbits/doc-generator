package com.matijacvetan.docgenerator.controller.specification

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Tag(name = "Resume Documentation API")
fun interface ResumeDocumentation {
    @Operation(
        method = "GET",
        summary = "Endpoint for getting resume documentation",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Resume documentation is generated",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_PDF_VALUE,
                ),
            ],
        ),
        ApiResponse(
            responseCode = "500",
            description = "There was a server error",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = Exception::class),
                ),
            ],
        ),
    )
    @GetMapping("/api/v1/resume-documents/{language}")
    fun getResumeDocumentation(
        @PathVariable language: String,
    ): ResponseEntity<ByteArray>
}
