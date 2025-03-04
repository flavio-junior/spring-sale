package br.com.spring.sale.entity.security

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "tb_recover_password")
data class RecoverPassword(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    @Column(nullable = false, unique = true)
    var token: Long? = 0,
    @Column(nullable = false, unique = true)
    var email: String? = "",
    @Column(nullable = false)
    var expiration: Instant? = null
)
