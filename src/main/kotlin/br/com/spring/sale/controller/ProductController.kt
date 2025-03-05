package br.com.spring.sale.controller

import br.com.spring.sale.entity.user.User
import br.com.spring.sale.exceptions.ForbiddenActionRequestException
import br.com.spring.sale.service.ProductService
import br.com.spring.sale.utils.common.PriceRequestVO
import br.com.spring.sale.utils.others.ConstantsUtils.EMPTY_FIELDS
import br.com.spring.sale.vo.product.ProductRequestVO
import br.com.spring.sale.vo.product.ProductResponseVO
import br.com.spring.sale.vo.product.RestockProductRequestVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
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
@RequestMapping(value = ["/api/spring/sale/products/v1"])
class ProductController {

    @Autowired
    private lateinit var productService: ProductService

    @GetMapping
    fun findAllProducts(
        @AuthenticationPrincipal user: User,
        @RequestParam(required = false) name: String?,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "12") size: Int,
        @RequestParam(value = "sort", defaultValue = "asc") sort: String
    ): ResponseEntity<Page<ProductResponseVO>> {
        val sortDirection: Sort.Direction =
            if ("desc".equals(sort, ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"))
        return ResponseEntity.ok(
            productService.findAllProducts(user = user, name = name, pageable = pageable)
        )
    }

    @GetMapping(value = ["/find/product/by/{name}"])
    fun findProductByName(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "name") name: String,
    ): ResponseEntity<List<ProductResponseVO>> {
        return ResponseEntity.ok(
            productService.findProductByName(user = user, name = name)
        )
    }

    @GetMapping(value = ["/{id}"])
    fun findProductById(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") id: Long
    ): ProductResponseVO {
        return productService.findProductById(user = user, productId = id)
    }

    @PostMapping
    fun createNewProduct(
        @AuthenticationPrincipal user: User,
        @RequestBody product: ProductRequestVO
    ): ResponseEntity<ProductResponseVO> {
        require(
            value = product.name.isNotBlank() && product.name.isNotEmpty()
        ) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        val entity: ProductResponseVO = productService.createNewProduct(user = user, product = product)
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(entity.id).toUri()
        return ResponseEntity.created(uri).body(entity)
    }

    @PutMapping
    fun updateProduct(
        @AuthenticationPrincipal user: User,
        @RequestBody product: ProductResponseVO
    ): ProductResponseVO {
        return productService.updateProduct(user = user, product = product)
    }

    @PatchMapping(value = ["update/price/product/{id}"])
    fun updatePriceProduct(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") id: Long,
        @RequestBody price: PriceRequestVO
    ): ResponseEntity<*> {
        productService.updatePriceProduct(user = user, productId = id, price = price)
        return ResponseEntity.noContent().build<Any>()
    }

    @PatchMapping(value = ["restock/product/{id}"])
    fun restockProduct(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") id: Long,
        @RequestBody restockProduct: RestockProductRequestVO
    ): ResponseEntity<*> {
        productService.restockProduct(user = user, productId = id, restockProduct = restockProduct)
        return ResponseEntity.noContent().build<Any>()
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteProduct(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") id: Long
    ): ResponseEntity<*> {
        productService.deleteProduct(user = user, productId = id)
        return ResponseEntity.noContent().build<Any>()
    }
}
