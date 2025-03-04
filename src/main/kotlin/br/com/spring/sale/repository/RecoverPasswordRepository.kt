package br.com.spring.sale.repository

import br.com.spring.sale.entity.security.RecoverPassword
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface RecoverPasswordRepository : JpaRepository<RecoverPassword, Long> {

    @Query(value = "SELECT rp FROM RecoverPassword rp WHERE rp.email =:email")
    fun findByEmail(@Param("email") email: String?): RecoverPassword?

    @Query(value = "SELECT COUNT(rp) FROM RecoverPassword rp WHERE rp.token = :code")
    fun checkCodeAlreadyExists(@Param("code") code: String): Long

    @Modifying
    @Query(value = "UPDATE RecoverPassword rp SET rp.token =:token, rp.expiration =:expiration WHERE rp.email =:email")
    fun updateTokenAndDataExpiration(
        @Param("email") email: String?,
        @Param("token") token: Long?,
        @Param("expiration") expiration: Instant?
    )
}
