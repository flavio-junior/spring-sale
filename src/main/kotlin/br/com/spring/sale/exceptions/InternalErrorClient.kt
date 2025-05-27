package br.com.spring.sale.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InternalErrorClient(message: String) : RuntimeException(message)
