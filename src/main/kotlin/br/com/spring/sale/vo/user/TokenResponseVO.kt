package br.com.spring.sale.vo.user

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenResponseVO(
    val expiration: String? = null,
    @JsonProperty(value = "access_token")
    val accessToken: String? = null,
    @JsonProperty(value = "refresh_token")
    val refreshToken: String? = null
)
