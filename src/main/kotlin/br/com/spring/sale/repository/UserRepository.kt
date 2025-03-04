package br.com.spring.sale.repository

import br.com.spring.sale.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User?, Long?> {

    fun findByEmail(email: String?): UserDetails?

    @Query("SELECT u FROM User u WHERE u.email =:email")
    fun fetchByEmail(email: String?): User?

    @Modifying
    @Query("UPDATE User u SET u.enabled = false WHERE u.id =:id AND u.email =:email")
    fun disabledProfileEmployee(
        @Param("id") userId: Long,
        @Param("email") email: String
    )

    @Modifying
    @Query("UPDATE User u SET u.enabled = true WHERE u.id =:id AND u.email =:email")
    fun enabledProfileEmployee(
        @Param("id") userId: Long,
        @Param("email") email: String
    )
}