package br.com.spring.sale.vo.user

import com.fasterxml.jackson.annotation.JsonProperty

data class UserAuthenticatedVO(
    var id: Long? = 0,
    var name: String? = null,
    var surname: String? = null,
    @JsonProperty(value = "username")
    var userName: String? = null,
    var email: String? = null
)
