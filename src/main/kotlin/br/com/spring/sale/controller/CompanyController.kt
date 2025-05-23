package br.com.spring.sale.controller

import br.com.spring.sale.entity.user.User
import br.com.spring.sale.service.CompanyService
import br.com.spring.sale.utils.others.MediaType.APPLICATION_JSON
import br.com.spring.sale.utils.others.MediaType.APPLICATION_MULTI_PART
import br.com.spring.sale.vo.company.CompanyResponseVO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping(value = ["/api/spring/sale/company/v1"])
class CompanyController {

    @Autowired
    private lateinit var companyService: CompanyService

    @PostMapping(
        consumes = [APPLICATION_MULTI_PART],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Create New Company",
        description = "Create New Company",
        tags = ["Company"],
        responses = [
            ApiResponse(
                description = "Created", responseCode = "201", content = [
                    Content(schema = Schema(implementation = CompanyResponseVO::class))
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
    fun createNewCompany(
        @AuthenticationPrincipal user: User,
        @RequestParam(value = "name") name: String,
        @RequestParam(value = "main_image") mainImage: MultipartFile
    ): ResponseEntity<CompanyResponseVO> {
        val entity: CompanyResponseVO = companyService.createNewCompany(user = user, mainImage = mainImage, name = name)
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(entity.id).toUri()
        return ResponseEntity.created(uri).body(entity)
    }

    @GetMapping(produces = [APPLICATION_JSON])
    @Operation(
        summary = "Find Company By User Logged",
        description = "Find Company By User Logged",
        tags = ["Company"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
                    Content(array = ArraySchema(schema = Schema(implementation = CompanyResponseVO::class)))
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
    fun getCompanyByUserLogged(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<CompanyResponseVO> {
        return ResponseEntity.ok(companyService.findCompanyByUserLogged(user = user))
    }
}
