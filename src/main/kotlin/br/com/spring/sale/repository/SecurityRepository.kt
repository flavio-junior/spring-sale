package br.com.spring.sale.repository

import br.com.spring.sale.entity.security.Security
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface SecurityRepository : JpaRepository<Security?, Long?> {

    @Query(value = "SELECT s FROM Security s WHERE s.code =:code")
    fun checkCodeSend(@Param("code") code: String?): Security?

    @Query(value = "SELECT s FROM Security s WHERE s.email =:email")
    fun checkEmailAlreadyExists(@Param("email") email: String): Int?

    @Modifying
    @Query(value = "UPDATE Security s SET s.code =:code, s.expiration =:expiration WHERE s.email =:email")
    fun updateCodeVerificationEmail(
        @Param("code") code: Long,
        @Param("expiration") expiration: LocalDateTime,
        @Param("email") email: String
    )
}
