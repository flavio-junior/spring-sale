package br.com.spring.sale.exceptions

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class OperationUnauthorizedException(exception: String? = "") : AuthenticationException(exception)
