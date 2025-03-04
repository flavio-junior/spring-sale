package br.com.spring.sale.service

import br.com.spring.sale.vo.user.NewPasswordVO
import br.com.spring.sale.vo.user.SignInRequestVO
import br.com.spring.sale.vo.user.SignUpVO
import br.com.spring.sale.security.JwtTokenProvider
import br.com.spring.sale.entity.security.RecoverPassword
import br.com.spring.sale.entity.security.Security
import br.com.spring.sale.entity.user.User
import br.com.spring.sale.exceptions.ForbiddenActionRequestException
import br.com.spring.sale.exceptions.ObjectDuplicateException
import br.com.spring.sale.exceptions.ResourceNotFoundException
import br.com.spring.sale.repository.RecoverPasswordRepository
import br.com.spring.sale.repository.SecurityRepository
import br.com.spring.sale.repository.UserRepository
import br.com.spring.sale.utils.others.ConstantsUtils.BODY
import br.com.spring.sale.utils.others.ConstantsUtils.EXPIRED_CODE
import br.com.spring.sale.utils.others.ConstantsUtils.SUBJECT
import br.com.spring.sale.utils.others.ConstantsUtils.SUBJECT_RECOVER_PASSWORD
import br.com.spring.sale.utils.others.generateCode
import br.com.spring.sale.vo.user.EmailVO
import br.com.spring.sale.vo.user.TokenVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.Instant

@Service
class AuthService {

    @Autowired
    private lateinit var securityRepository: SecurityRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    @Autowired
    private lateinit var recoverPasswordRepository: RecoverPasswordRepository

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var emailService: EmailService

    @Value("\${email.password-recover.token.minutes}")
    private val tokenMinutes: Long = 0

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Transactional
    fun confirmEmailAddress(emailVO: EmailVO) {
        val checkUserAlreadyExists: UserDetails? = userRepository.findByEmail(email = emailVO.email)
        if (checkUserAlreadyExists != null) {
            throw ObjectDuplicateException(message = EMAIL_DUPLICATE_EXCEPTION)
        } else {
            val checkEmailAlreadyExists: Int? = securityRepository.checkEmailAlreadyExists(email = emailVO.email)
            if (checkEmailAlreadyExists == 1) {
                val code = generateCode()
                securityRepository.updateCodeVerificationEmail(
                    code = code,
                    expiration = LocalDateTime.now().plusMinutes(tokenMinutes),
                    email = emailVO.email
                )
                emailService.sendEmailToConfirmation(
                    to = emailVO.email,
                    subject = SUBJECT,
                    body = "$BODY: $code"
                )
            } else {
                val saveNewKeySecurity = Security(
                    code = generateCode(),
                    email = emailVO.email,
                    expiration = LocalDateTime.now().plusMinutes(tokenMinutes)
                )
                securityRepository.save(saveNewKeySecurity)
                emailService.sendEmailToConfirmation(
                    to = emailVO.email,
                    subject = SUBJECT,
                    body = "$BODY: ${saveNewKeySecurity.code}"
                )
            }
        }
    }

    fun checkCodeSendToConfirmEmail(code: String) {
        val entity: Security? = securityRepository.checkCodeSend(code = code)
        entity?.let {
            if (it.expiration.isBefore(LocalDateTime.now())) {
                throw ForbiddenActionRequestException(EXPIRED_CODE)
            }
        } ?: throw ResourceNotFoundException(message = CODE_NOT_FOUND)
    }

    fun signUp(data: SignUpVO): UserDetails {
        val authentication: UserDetails? = userRepository.findByEmail(data.email)
        if (authentication != null) {
            throw ObjectDuplicateException(message = EMAIL_DUPLICATE_EXCEPTION)
        } else {
            val newUser = User()
            newUser.createdAt = Instant.now()
            newUser.name = data.name
            newUser.surname = data.password
            newUser.email = data.email
            newUser.password = passwordEncoder.encode(data.password)
            newUser.typeAccount = data.type
            return userRepository.save(newUser)
        }
    }

    fun signIn(signInVO: SignInRequestVO): TokenVO {
        return try {
            val authentication: Authentication =
                authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        signInVO.email,
                        signInVO.password
                    )
                )
            val user: User? = userRepository.fetchByEmail(email = signInVO.email)
            tokenProvider.createAccessToken(username = authentication.name, typeAccount = user?.typeAccount!!)
        } catch (e: AuthenticationException) {
            throw ForbiddenActionRequestException(exception = INVALID_CREDENTIALS)
        }
    }

    @Transactional
    fun createRecoverPassword(emailVO: EmailVO) {
        val user: User? = userRepository.fetchByEmail(emailVO.email)
        user?.let {
            val recoverPassword: RecoverPassword? = recoverPasswordRepository.findByEmail(it.email)
            if (recoverPassword != null) {
                val token = generateCode()
                val expiration: Instant = Instant.now().plusSeconds(tokenMinutes * 60)
                recoverPasswordRepository.updateTokenAndDataExpiration(
                    email = recoverPassword.email,
                    token = token,
                    expiration = expiration
                )
                val text =
                    "Utilize o código abaixo para redefinir sua senha:\n Código: $token\n O código gerado tem validade de $tokenMinutes minutos."
                emailService.sendEmailToConfirmation(emailVO.email, SUBJECT_RECOVER_PASSWORD, text)
            } else {
                val token = generateCode()
                val expiration: Instant = Instant.now().plusSeconds(tokenMinutes * 60)
                val recover = RecoverPassword(
                    token = token,
                    email = emailVO.email,
                    expiration = expiration
                )
                recoverPasswordRepository.save(recover)
                val text =
                    "Utilize o código abaixo para redefinir sua senha:\nCódigo: $token\nO código gerado tem validade de $tokenMinutes minutos."
                emailService.sendEmailToConfirmation(emailVO.email, SUBJECT_RECOVER_PASSWORD, text)
            }
        } ?: throw ResourceNotFoundException(message = EMAIL_NOT_FOUND)
    }

    @Transactional
    fun checkRecoverPassword(code: String) {
        val checkCodeAlreadyExists: Long = recoverPasswordRepository.checkCodeAlreadyExists(code = code)
        if (checkCodeAlreadyExists > 0) {
            ResponseEntity.noContent()
        } else {
            throw ResourceNotFoundException(message = RECOVER_PASSWORD_NOT_FOUND)
        }
    }

    fun saveNewPassword(passwordVO: NewPasswordVO) {
        val recoverPassword: RecoverPassword? = recoverPasswordRepository.findByEmail(email = passwordVO.email)
        recoverPassword?.let {
            if (it.expiration!!.isAfter(Instant.now())) {
                val userInstanced: User? = userRepository.fetchByEmail(passwordVO.email)
                if (userInstanced != null) {
                    userInstanced.password = passwordEncoder.encode(passwordVO.password)
                    userRepository.save(userInstanced)
                } else {
                    throw ResourceNotFoundException(message = EMAIL_NOT_FOUND)
                }
            } else {
                throw ForbiddenActionRequestException(EXPIRED_CODE)
            }
        } ?: throw ResourceNotFoundException(message = EMAIL_NOT_FOUND)
    }

    fun refreshToken(email: String, refreshToken: String): TokenVO {
        val user: User? = userRepository.fetchByEmail(email)
        val tokenResponse: TokenVO = if (user != null) {
            tokenProvider.refreshToken(refreshToken)
        } else {
            throw UsernameNotFoundException("Username $email not found")
        }
        return (tokenResponse)
    }

    companion object {
        const val INVALID_CREDENTIALS = "invalid credentials"
        const val EMAIL_DUPLICATE_EXCEPTION = "Email already exists!"
        const val EMAIL_NOT_FOUND = "Email not found!"
        const val CODE_NOT_FOUND = "Code not found!"
        const val RECOVER_PASSWORD_NOT_FOUND = "Recover Password Not Found!"
    }
}
