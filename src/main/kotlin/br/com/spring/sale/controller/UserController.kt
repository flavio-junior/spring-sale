package br.com.spring.sale.controller

import br.com.spring.sale.entity.user.User
import br.com.spring.sale.vo.user.UserAuthenticatedVO
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/spring/sale/use/settings/v1"])
class UserController {

    @GetMapping
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
