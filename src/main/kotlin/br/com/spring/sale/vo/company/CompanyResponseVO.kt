package br.com.spring.sale.vo.company

import java.time.LocalDate
import java.time.LocalTime

data class CompanyResponseVO(
    var id: Long? = 0,
    var identifier: Long? = 0,
    var date: LocalDate? = null,
    var hour: LocalTime? = null,
    var name: String = "",
    var mainImage: String? = ""
)
