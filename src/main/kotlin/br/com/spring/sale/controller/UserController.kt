package br.com.spring.sale.controller

import br.com.spring.sale.entity.user.User
import br.com.spring.sale.exceptions.ForbiddenActionRequestException
import br.com.spring.sale.service.ProfileUserService
import br.com.spring.sale.service.UserService
import br.com.spring.sale.utils.others.ConstantsUtils.EMPTY_FIELDS
import br.com.spring.sale.utils.others.MediaType.APPLICATION_JSON
import br.com.spring.sale.vo.user.ChangeInfoUserRequestVO
import br.com.spring.sale.vo.user.UserAuthenticatedVO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/spring/sale/use/settings/v1"])
@Tag(name = "User", description = "EndPoint For Manager Settings of User")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var profileUserService: ProfileUserService

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
        return ResponseEntity.ok().body(userService.findUserAuthenticated(user = user))
    }

    @PatchMapping(
        consumes = [APPLICATION_JSON],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Change Info of User",
        description = "Change Info Of User",
        tags = ["User"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
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
    fun changeInfoUserLogged(
        @AuthenticationPrincipal user: User,
        @RequestBody info: ChangeInfoUserRequestVO
    ) {
        require(
            value = info.name.isNotBlank() && info.name.isNotEmpty() &&
                    info.surname.isNotBlank() && info.surname.isNotEmpty() &&
                    info.username.isNotBlank() && info.username.isNotEmpty()
        ) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        ResponseEntity.ok(userService.changeInfoUserLogged(user = user, info = info))
    }

    @DeleteMapping(produces = [APPLICATION_JSON])
    @Operation(
        summary = "Delete My Account",
        description = "Delete My Account",
        tags = ["User"],
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
    fun deleteMyAccount(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<*> {
        profileUserService.deleteMyAccount(user = user)
        return ResponseEntity.noContent().build<Any>()
    }
}
