package br.com.spring.sale.vo.user

import br.com.spring.sale.entity.employee.Employee
import br.com.spring.sale.utils.common.TypeAccount

data class SignUpVO(
    var name: String,
    var surname: String,
    val email: String,
    val password: String,
    var type: TypeAccount? = null,
    var employee: Employee? = null
)
