package br.com.spring.sale.controller

import br.com.spring.sale.exceptions.ForbiddenActionRequestException
import br.com.spring.sale.service.AuthService
import br.com.spring.sale.utils.others.ConstantsUtils.EMPTY_FIELDS
import br.com.spring.sale.utils.others.MediaType.APPLICATION_JSON
import br.com.spring.sale.vo.user.EmailVO
import br.com.spring.sale.vo.user.NewPasswordVO
import br.com.spring.sale.vo.user.SignInRequestVO
import br.com.spring.sale.vo.user.SignUpVO
import br.com.spring.sale.vo.user.TokenVO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
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
@Tag(name = "Auth", description = "EndPoint For Manager Authentication")
class AuthController {

    @Autowired
    private lateinit var authService: AuthService

    @PostMapping(
        value = ["/confirm-email-address"],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Confirm Email",
        description = "Confirm Email",
        tags = ["Auth"],
        responses = [
            ApiResponse(
                description = "No Content", responseCode = "204", content = [
                    Content(array = ArraySchema(schema = Schema(implementation = Unit::class)))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun confirmEmailAddress(
        @RequestBody emailVO: EmailVO
    ): ResponseEntity<*> {
        require(value = emailVO.email.isNotEmpty() && emailVO.email.isNotBlank()) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        authService.confirmEmailAddress(emailVO = emailVO)
        return ResponseEntity.noContent().build<Any>()
    }

    @GetMapping(
        value = ["/check-code-verification/{code}"],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Check Code of Verification",
        description = "Check Code Verification",
        tags = ["Auth"],
        responses = [
            ApiResponse(
                description = "No Content", responseCode = "204", content = [
                    Content(array = ArraySchema(schema = Schema(implementation = Unit::class)))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun checkCodeVerification(
        @PathVariable code: String
    ): ResponseEntity<*> {
        authService.checkCodeVerification(code = code)
        return ResponseEntity.noContent().build<Any>()
    }

    @PostMapping(
        value = ["/sign-up"],
        consumes = [APPLICATION_JSON],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Create New User",
        description = "Create New User",
        tags = ["Auth"],
        responses = [
            ApiResponse(
                description = "Created", responseCode = "201", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Conflict", responseCode = "409", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
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
        authService.signUp(data = signUpVO)
        return ResponseEntity.status(HttpStatus.CREATED).build<Any>()
    }

    @PostMapping(
        value = ["/sign-in"],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Authentication",
        description = "Authentication",
        tags = ["Auth"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
                    Content(schema = Schema(implementation = TokenVO::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun signIn(
        @RequestBody signInVO: SignInRequestVO
    ): ResponseEntity<TokenVO> {
        require(
            value = signInVO.email.isNotEmpty() && signInVO.email.isNotBlank() &&
                    signInVO.password.isNotEmpty() && signInVO.password.isNotBlank()
        ) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        return ResponseEntity.ok(authService.signIn(signInVO = signInVO))
    }

    @PostMapping(
        value = ["/recover-password"],
        consumes = [APPLICATION_JSON],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Recover Password",
        description = "Recover Password",
        tags = ["Auth"],
        responses = [
            ApiResponse(
                description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun createRecoverPassword(
        @RequestBody emailVO: EmailVO
    ): ResponseEntity<*> {
        require(value = emailVO.email.isNotEmpty() && emailVO.email.isNotBlank()) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        authService.createRecoverPassword(emailVO)
        return ResponseEntity.noContent().build<Any>()
    }

    @GetMapping(
        value = ["/check-recover-password/{code}"],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Check Recover Password",
        description = "Check Recover Password",
        tags = ["Auth"], responses = [
            ApiResponse(
                description = "No Content", responseCode = "204", content = [
                    Content(array = ArraySchema(schema = Schema(implementation = Unit::class)))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun checkRecoverPassword(
        @PathVariable code: String
    ): ResponseEntity<*> {
        authService.checkRecoverPassword(code = code)
        return ResponseEntity.noContent().build<Any>()
    }

    @PutMapping(
        value = ["/new-password"],
        consumes = [APPLICATION_JSON],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Create New Password",
        description = "Create New Password",
        tags = ["Auth"],
        responses = [
            ApiResponse(
                description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
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

    @PutMapping(
        value = ["/refresh-token/{email}"],
        consumes = [APPLICATION_JSON],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Refresh Token",
        description = "Refresh Token",
        tags = ["Auth"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
                    Content(schema = Schema(implementation = TokenVO::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun refreshToken(
        @PathVariable(value = "email") email: String,
        @RequestHeader(value = "Authorization") refreshToken: String,
    ): ResponseEntity<TokenVO> {
        return ResponseEntity.ok(authService.refreshToken(email = email, refreshToken = refreshToken))
    }
}
