package br.com.spring.sale.entity.security

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tb_security")
data class Security(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(nullable = false, unique = true)
    var code: Long = 0,
    @Column(nullable = false, unique = true)
    var email: String = "",
    @Column(nullable = false)
    var expiration: LocalDateTime
)
