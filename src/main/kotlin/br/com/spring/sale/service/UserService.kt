package br.com.spring.sale.service

import br.com.spring.sale.entity.user.User
import br.com.spring.sale.exceptions.ResourceNotFoundException
import br.com.spring.sale.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        val user: UserDetails? = userRepository.findByEmail(username)
        return user ?: throw UsernameNotFoundException("$username not found")
    }

    fun findUserById(
        userId: Long
    ): User? {
        return userRepository.findById(userId).orElseThrow {
            throw ResourceNotFoundException(message = USER_NOT_FOUND)
        }
    }

    @Transactional
    fun disabledProfileEmployee(
        userId: Long,
        email: String
    ) {
        userRepository.disabledProfileEmployee(userId = userId, email = email)
    }

    @Transactional
    fun enabledProfileEmployee(
        userId: Long,
        email: String
    ) {
        userRepository.enabledProfileEmployee(userId = userId, email = email)
    }

    @Transactional
    fun deleteMyAccount(
        userId: Long
    ) {
        userRepository.deleteById(userId)
    }

    companion object {
        const val USER_NOT_FOUND = "User not found"
    }
}