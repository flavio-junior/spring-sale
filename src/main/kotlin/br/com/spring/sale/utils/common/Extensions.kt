package br.com.spring.sale.utils.common

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun getLocalDateTime(): LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
