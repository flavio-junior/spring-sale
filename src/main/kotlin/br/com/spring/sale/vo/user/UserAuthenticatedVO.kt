package br.com.spring.sale.vo.user

import br.com.spring.sale.utils.common.TypeAccount

data class UserAuthenticatedVO(
    var id: Long? = 0,
    var name: String? = null,
    var surname: String? = null,
    var email: String? = null,
    var type: TypeAccount? = null
)
