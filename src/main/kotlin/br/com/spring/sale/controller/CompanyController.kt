package br.com.spring.sale.controller

import br.com.spring.sale.entity.user.User
import br.com.spring.sale.service.CompanyService
import br.com.spring.sale.vo.company.CompanyResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping(value = ["/api/spring/sale/companies/v1"])
class CompanyController {

    @Autowired
    private lateinit var companyService: CompanyService

    @PostMapping
    fun createNewCompany(
        @AuthenticationPrincipal user: User,
        @RequestParam("name") name: String,
        @RequestParam("main_image") mainImage: MultipartFile
    ): ResponseEntity<CompanyResponseVO> {
        val entity: CompanyResponseVO = companyService.createNewCompany(user = user, mainImage = mainImage, name = name)
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(entity.id).toUri()
        return ResponseEntity.created(uri).body(entity)
    }
}
