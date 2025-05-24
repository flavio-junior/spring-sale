package br.com.spring.sale.security

import br.com.spring.sale.exceptions.InvalidJwtAuthenticationException
import br.com.spring.sale.utils.common.TypeAccount
import br.com.spring.sale.utils.common.getLocalDateTime
import br.com.spring.sale.utils.others.toDate
import br.com.spring.sale.vo.user.TokenResponseVO
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.DecodedJWT
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class JwtTokenProvider {

    @Value("\${security.jwt.token.secret-key:secret}")
    private var secretKey = "secret"

    @Value("\${security.jwt.token.expires-at}")
    private var tokenExpiresAt: Long = 0

    @Autowired
    private lateinit var userDetailsService: UserDetailsService
    private lateinit var algorithm: Algorithm

    @PostConstruct
    internal fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        algorithm = Algorithm.HMAC256(secretKey.toByteArray())
    }

    fun createAccessToken(
        username: String, typeAccount: TypeAccount
    ): TokenResponseVO {
        val currentLocalDateTime = getLocalDateTime()
        val validity = currentLocalDateTime.plusDays(tokenExpiresAt)
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return TokenResponseVO(
            expiration = validity.format(formatter),
            accessToken = getAccessToken(
                username = username,
                typeAccount = typeAccount,
                now = currentLocalDateTime,
                validity = validity
            ),
            refreshToken = getRefreshToken(
                username = username,
                typeAccount = typeAccount,
                now = currentLocalDateTime
            )
        )
    }

    fun refreshToken(
        refreshToken: String
    ): TokenResponseVO {
        var token = ""
        if (refreshToken.contains(other = "Bearer ")) token = refreshToken.substring(startIndex = "Bearer ".length)
        val verifier: JWTVerifier = JWT.require(algorithm).build()
        val decodedJWT: DecodedJWT = verifier.verify(token)
        val username: String = decodedJWT.subject
        val typeAccount: TypeAccount = decodedJWT.getClaim("type_account").`as`(TypeAccount::class.java)
        val currentLocalDateTime = getLocalDateTime()
        val validity = currentLocalDateTime.plusDays(tokenExpiresAt)
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val expirationFormatted = validity.format(formatter)
        return TokenResponseVO(
            expiration = expirationFormatted,
            accessToken = getAccessToken(
                username = username,
                typeAccount = typeAccount,
                now = currentLocalDateTime,
                validity = validity
            ),
            refreshToken = getRefreshToken(
                username = username,
                typeAccount = typeAccount,
                now = currentLocalDateTime
            )
        )
    }

    fun getAccessToken(
        username: String,
        typeAccount: TypeAccount,
        now: LocalDateTime,
        validity: LocalDateTime
    ): String {
        val issuerURL: String = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
        return JWT.create()
            .withClaim("type_account", typeAccount.toString())
            .withIssuedAt(now.toDate())
            .withExpiresAt(validity.toDate())
            .withSubject(username)
            .withIssuer(issuerURL)
            .sign(algorithm)
            .trim()
    }

    fun getRefreshToken(
        username: String,
        typeAccount: TypeAccount,
        now: LocalDateTime
    ): String {
        val createdNewPasswordVORefreshToken = now.plusDays(tokenExpiresAt)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .let { Date.from(it) }
        return JWT.create()
            .withClaim("type_account", typeAccount.toString())
            .withExpiresAt(createdNewPasswordVORefreshToken)
            .withSubject(username)
            .sign(algorithm)
            .trim()
    }

    fun getAuthentication(
        token: String
    ): Authentication {
        val decodedJWT: DecodedJWT = decodedToken(token)
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(decodedJWT.subject)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun decodedToken(
        token: String
    ): DecodedJWT {
        val algorithm = Algorithm.HMAC256(secretKey.toByteArray())
        val verify: JWTVerifier = JWT.require(algorithm).build()
        return verify.verify(token)
    }

    fun resolveToken(
        httpServletRequest: HttpServletRequest
    ): String? {
        val bearerToken = httpServletRequest.getHeader("Authorization")
        return if (!bearerToken.isNullOrBlank() && bearerToken.startsWith(prefix = "Bearer ")) {
            bearerToken.substring(startIndex = "Bearer ".length)
        } else {
            null
        }
    }

    fun validateToken(
        token: String
    ): Boolean {
        try {
            val decodedJWT = decodedToken(token)
            if (decodedJWT.expiresAt.before(Date())) {
                throw InvalidJwtAuthenticationException(exception = "Token has expired")
            }
            return true
        } catch (e: TokenExpiredException) {
            throw e
        } catch (e: Exception) {
            throw InvalidJwtAuthenticationException(exception = "Expired or invalid JWT token!")
        }
    }
}
