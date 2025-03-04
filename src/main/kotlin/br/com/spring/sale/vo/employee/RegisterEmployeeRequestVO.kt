package br.com.spring.sale.vo.employee

import br.com.spring.sale.utils.common.Function

data class RegisterEmployeeRequestVO(
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val function: Function? = null
)
