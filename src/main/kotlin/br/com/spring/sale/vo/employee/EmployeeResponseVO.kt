package br.com.spring.sale.vo.employee

import br.com.spring.sale.utils.common.Function
import br.com.spring.sale.utils.common.StatusEmployee

data class EmployeeResponseVO(
    var id: Long = 0,
    var createdAt: String? = "",
    var name: String = "",
    var function: Function? = null,
    var status: StatusEmployee? = null
)
