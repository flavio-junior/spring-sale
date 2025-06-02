package br.com.spring.sale.exceptions

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT)
class DatabaseException(exception: String = "Operation failed due to a data integrity conflict!") :
    DataIntegrityViolationException(exception)
