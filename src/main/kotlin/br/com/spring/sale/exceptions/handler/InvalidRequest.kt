package br.com.spring.sale.exceptions.handler

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidRequest(message: String) : RuntimeException(message)
