package br.com.spring.sale.exceptions.handler

import br.com.spring.sale.exceptions.ForbiddenActionRequestException
import br.com.spring.sale.exceptions.InternalErrorClient
import br.com.spring.sale.exceptions.InvalidJwtAuthenticationException
import br.com.spring.sale.exceptions.ObjectDuplicateException
import br.com.spring.sale.exceptions.OperationUnauthorizedException
import br.com.spring.sale.exceptions.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@RestController
class CustomizedResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(InvalidJwtAuthenticationException::class)
    fun handleInvalidJwtAuthenticationExceptions(
        exception: Exception,
        request: WebRequest,
    ): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            status = HttpStatus.FORBIDDEN.value(),
            message = exception.message
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(ForbiddenActionRequestException::class)
    fun handleForbiddenActionRequestExceptions(
        exception: Exception,
        request: WebRequest
    ): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            status = HttpStatus.FORBIDDEN.value(),
            message = exception.message
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(InternalErrorClient::class)
    fun handleInternalErrorClientExceptions(
        exception: Exception,
        request: WebRequest
    ): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = exception.message
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidRequest::class)
    fun handleInvalidRequestException(
        exception: Exception,
        request: WebRequest
    ): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = exception.message
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(OperationUnauthorizedException::class)
    fun handleOperationUnauthorizedExceptions(request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            status = HttpStatus.FORBIDDEN.value(),
            message = "Alert! Operation Unauthorized!"
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        exception: Exception,
        request: WebRequest
    ): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = exception.message
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ObjectDuplicateException::class)
    fun handleObjectDuplicateException(
        exception: Exception,
        request: WebRequest
    ): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            status = HttpStatus.CONFLICT.value(),
            message = exception.message
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT)
    }
}
