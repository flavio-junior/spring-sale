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

    @Query(value = "SELECT u FROM User u WHERE u.email =:email")
    fun fetchByEmail(email: String?): User?

    @Query(value = "SELECT u FROM User u WHERE u.id =:id")
    fun findUserById(
        @Param(value = "id") userId: Long? = null
    ): User?

    @Query(value = "SELECT s FROM User s WHERE s.userName = :username")
    fun checkUsernameAlreadyExisting(
        @Param(value = "username") username: String
    ): User?

    @Modifying
    @Query(value = "UPDATE User u SET u.name =:name, u.surname =:surname, u.userName =:username WHERE u.id =:id")
    fun changeInfoUserLogged(
        @Param(value = "id") userId: Long? = null,
        @Param(value = "name") name: String,
        @Param(value = "surname") surname: String,
        @Param(value = "username") username: String
    )
}
