package br.com.digital.order.exceptions

import com.amazonaws.AmazonServiceException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class AWSServiceException(exception: String?) : AmazonServiceException(exception)
