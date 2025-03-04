package br.com.spring.sale.vo.user

import br.com.spring.sale.utils.common.TypeAccount

data class TokenVO(
    val user: String? = null,
    val authenticated: Boolean? = null,
    val created: String? = null,
    val type: TypeAccount? = null,
    val expiration: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
