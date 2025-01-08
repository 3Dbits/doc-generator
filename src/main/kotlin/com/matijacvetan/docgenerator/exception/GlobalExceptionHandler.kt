package com.matijacvetan.docgenerator.exception

import com.matijacvetan.docgenerator.models.ErrorResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.apache.catalina.connector.ClientAbortException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.async.AsyncRequestNotUsableException
import org.springframework.web.servlet.NoHandlerFoundException
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {
    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(Exception::class)
    fun handleException(
        ex: Exception,
        request: HttpServletRequest,
    ): ResponseEntity<Any> {
        // Handle client abort exceptions gracefully
        if (ex is ClientAbortException ||
            ex is AsyncRequestNotUsableException ||
            ex.cause is ClientAbortException
        ) {
            logger.debug(ex) { "Client disconnected prematurely" }
            return ResponseEntity.status(HttpStatus.OK).build()
        }

        logger.error(ex) { "Unhandled exception occurred" }

        val errorResponse =
            ErrorResponse(
                timestamp = LocalDateTime.now(),
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
                message = "An unexpected error occurred",
                path = request.requestURI,
                details = if (isDevelopmentProfile()) ex.stackTraceToString() else null,
            )

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse)
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNotFound(
        ex: NoHandlerFoundException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        logger.info { "Path ${request.requestURI} not found" }
        val error =
            ErrorResponse(
                timestamp = LocalDateTime.now(),
                status = HttpStatus.NOT_FOUND.value(),
                error = "Not Found",
                message = "Path ${request.requestURI} not found",
                path = request.requestURI,
            )
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    private fun isDevelopmentProfile(): Boolean {
        val environment = System.getProperty("spring.profiles.active")
        return environment != null && environment.contains("dev")
    }
}
