package br.com.spring.sale.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class ObjectDuplicateException(message: String) : RuntimeException(message)
