package br.com.spring.sale.controller

import br.com.spring.sale.exceptions.ForbiddenActionRequestException
import br.com.spring.sale.service.AuthService
import br.com.spring.sale.utils.others.ConstantsUtils.EMPTY_FIELDS
import br.com.spring.sale.vo.user.EmailVO
import br.com.spring.sale.vo.user.NewPasswordVO
import br.com.spring.sale.vo.user.SignInRequestVO
import br.com.spring.sale.vo.user.SignUpVO
import br.com.spring.sale.vo.user.TokenVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/auth/v1"])
class AuthController {

    @Autowired
    private lateinit var authService: AuthService

    @PostMapping(value = ["/confirm-email-address"])
    fun confirmEmailAddress(
        @RequestBody emailVO: EmailVO
    ): ResponseEntity<*> {
        require(value = emailVO.email.isNotEmpty() && emailVO.email.isNotBlank()) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        authService.confirmEmailAddress(emailVO)
        return ResponseEntity.noContent().build<Any>()
    }

    @GetMapping(value = ["/check-code-existent/{code}"])
    fun checkCodeSendToConfirmEmail(
        @PathVariable code: String
    ): ResponseEntity<*> {
        authService.checkCodeSendToConfirmEmail(code = code)
        return ResponseEntity.noContent().build<Any>()
    }

    @PostMapping(value = ["/signUp"])
    fun signUp(
        @RequestBody signUpVO: SignUpVO
    ): ResponseEntity<*> {
        require(
            value = signUpVO.email.isNotEmpty() && signUpVO.email.isNotBlank()
                    && signUpVO.password.isNotEmpty() && signUpVO.password.isNotBlank()
                    && signUpVO.type != null
        ) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        authService.signUp(signUpVO)
        return ResponseEntity.status(HttpStatus.CREATED).build<Any>()
    }

    @PostMapping(value = ["/signIn"])
    fun signIn(@RequestBody signInVO: SignInRequestVO): ResponseEntity<TokenVO> {
        require(
            value = signInVO.email.isNotEmpty() && signInVO.email.isNotBlank() &&
                    signInVO.password.isNotEmpty() && signInVO.password.isNotBlank()
        ) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        return ResponseEntity.ok(authService.signIn(signInVO))
    }

    @PostMapping(value = ["/recover-password"])
    fun createRecoverPassword(
        @RequestBody emailVO: EmailVO
    ): ResponseEntity<*> {
        require(value = emailVO.email.isNotEmpty() && emailVO.email.isNotBlank()) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        authService.createRecoverPassword(emailVO)
        return ResponseEntity.noContent().build<Any>()
    }

    @GetMapping(value = ["/check-recover-password/{code}"])
    fun checkRecoverPassword(
        @PathVariable code: String
    ): ResponseEntity<*> {
        authService.checkRecoverPassword(code = code)
        return ResponseEntity.noContent().build<Any>()
    }

    @PutMapping(value = ["/new-password"])
    fun saveNewPassword(
        @RequestBody passwordVO: NewPasswordVO
    ): ResponseEntity<*> {
        require(
            value = passwordVO.email.isNotEmpty()
                    && passwordVO.email.isNotBlank()
                    && passwordVO.password.isNotEmpty()
                    && passwordVO.password.isNotBlank()
        ) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        authService.saveNewPassword(passwordVO)
        return ResponseEntity.noContent().build<Any>()
    }

    @PutMapping(value = ["/refresh/{email}"])
    fun refreshToken(
        @PathVariable("email") email: String,
        @RequestHeader("Authorization") refreshToken: String,
    ): ResponseEntity<TokenVO> {
        return ResponseEntity.ok(authService.refreshToken(email, refreshToken))
    }
}
