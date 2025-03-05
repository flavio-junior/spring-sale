package br.com.spring.sale.controller

import br.com.spring.sale.entity.user.User
import br.com.spring.sale.exceptions.ForbiddenActionRequestException
import br.com.spring.sale.service.CategoryService
import br.com.spring.sale.utils.others.ConstantsUtils.EMPTY_FIELDS
import br.com.spring.sale.vo.category.CategoryResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping(value = ["/api/spring/sale/categories/v1"])
class CategoryController {

    @Autowired
    private lateinit var categoryService: CategoryService

    @GetMapping
    fun findAllCategories(
        @AuthenticationPrincipal user: User,
        @RequestParam(required = false) name: String?,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "12") size: Int,
        @RequestParam(value = "sort", defaultValue = "asc") sort: String
    ): ResponseEntity<Page<CategoryResponseVO>> {
        val sortDirection: Sort.Direction =
            if ("desc".equals(sort, ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"))
        return ResponseEntity.ok(
            categoryService.findAllCategories(user = user, name = name, pageable = pageable)
        )
    }

    @GetMapping(value = ["/find/category/by/{name}"])
    fun findCategoryByName(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "name") name: String,
    ): ResponseEntity<List<CategoryResponseVO>> {
        return ResponseEntity.ok(
            categoryService.findCategoryByName(user = user, name = name)
        )
    }

    @GetMapping(value = ["/{id}"])
    fun findById(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") categoryId: Long
    ): CategoryResponseVO {
        return categoryService.findCategoryById(user = user, categoryId = categoryId)
    }

    @PostMapping
    fun createNewCategory(
        @AuthenticationPrincipal user: User,
        @RequestBody categoryResponseVO: CategoryResponseVO
    ): ResponseEntity<CategoryResponseVO> {
        require(
            value = categoryResponseVO.name.isNotBlank() && categoryResponseVO.name.isNotEmpty()
        ) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        val entity: CategoryResponseVO = categoryService.createNewCategory(user = user, category = categoryResponseVO)
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(entity.id).toUri()
        return ResponseEntity.created(uri).body(entity)
    }

    @PutMapping
    fun updateCategory(
        @AuthenticationPrincipal user: User,
        @RequestBody category: CategoryResponseVO
    ): CategoryResponseVO {
        return categoryService.updateCategory(user = user, category = category)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteCategory(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") categoryId: Long
    ): ResponseEntity<*> {
        categoryService.deleteCategory(user = user, categoryId = categoryId)
        return ResponseEntity.noContent().build<Any>()
    }
}
