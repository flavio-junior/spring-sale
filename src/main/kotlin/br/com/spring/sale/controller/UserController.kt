package br.com.spring.sale.controller

import br.com.spring.sale.entity.user.User
import br.com.spring.sale.utils.others.MediaType.APPLICATION_JSON
import br.com.spring.sale.vo.user.UserAuthenticatedVO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/spring/sale/use/settings/v1"])
@Tag(name = "User", description = "EndPoint For Manager Settings of User")
class UserController {

    @GetMapping(produces = [APPLICATION_JSON])
    @Operation(
        summary = "Find User Authenticated",
        description = "Find User Authenticated",
        tags = ["User"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
                    Content(array = ArraySchema(schema = Schema(implementation = UserAuthenticatedVO::class)))
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
    fun findUserAuthenticated(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<UserAuthenticatedVO> {
        val userInstance = UserAuthenticatedVO(
            id = user.id,
            name = user.name,
            surname = user.surname,
            email = user.email,
            type = user.typeAccount
        )
        return ResponseEntity.ok().body(userInstance)
    }
}
