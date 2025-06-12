package br.com.spring.sale.service

import br.com.spring.sale.entity.product.Product
import br.com.spring.sale.entity.user.User
import br.com.spring.sale.exceptions.ObjectDuplicateException
import br.com.spring.sale.exceptions.ResourceNotFoundException
import br.com.spring.sale.repository.ProductRepository
import br.com.spring.sale.utils.common.PriceRequestVO
import br.com.spring.sale.utils.others.ConverterUtils.parseObject
import br.com.spring.sale.vo.product.ProductRequestVO
import br.com.spring.sale.vo.product.ProductResponseVO
import br.com.spring.sale.vo.product.RestockProductRequestVO
import br.com.spring.sale.vo.product.UpdateProductRequestVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class ProductService {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var categoryService: CategoryService

    @Autowired
    private lateinit var userService: UserService

    @Transactional(readOnly = true)
    fun findAllProducts(
        user: User,
        name: String?,
        pageable: Pageable
    ): Page<ProductResponseVO> {
        val products: Page<Product>? =
            productRepository.findAllProducts(userId = user.id, name = name, pageable = pageable)
        return products?.map { product -> parseObject(origin = product, destination = ProductResponseVO::class.java) }
            ?: throw ResourceNotFoundException(message = PRODUCT_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    fun findProductById(
        user: User,
        productId: Long
    ): ProductResponseVO {
        val product = getProduct(user = user, productId = productId)
        return parseObject(origin = product, destination = ProductResponseVO::class.java)
    }

    fun getProduct(
        user: User,
        productId: Long
    ): Product {
        val productSaved: Product? =
            productRepository.findProductById(userId = user.id, productId = productId)
        if (productSaved != null) {
            return productSaved
        } else {
            throw ResourceNotFoundException(message = PRODUCT_NOT_FOUND)
        }
    }

    @Transactional
    fun createNewProduct(
        user: User,
        product: ProductRequestVO
    ): ProductResponseVO {
        if (!checkNameProductAlreadyExists(user = user, name = product.name)) {
            val productResult: Product = parseObject(product, Product::class.java)
            productResult.categories =
                categoryService.converterCategories(user = user, categories = product.categories)
            productResult.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
            productResult.user = userService.findUserById(userId = user.id)
            return parseObject(
                origin = productRepository.save(productResult),
                destination = ProductResponseVO::class.java
            )
        } else {
            throw ObjectDuplicateException(message = DUPLICATE_NAME_PRODUCT)
        }
    }

    private fun checkNameProductAlreadyExists(
        user: User,
        name: String
    ): Boolean {
        val productResult = productRepository.checkNameProductAlreadyExists(userId = user.id, name = name)
        return productResult != null
    }

    @Transactional
    fun updateProduct(
        user: User,
        product: UpdateProductRequestVO
    ): ProductResponseVO {
        if (!checkNameProductAlreadyExists(user = user, name = product.name)) {
            val productSaved: Product = getProduct(user = user, productId = product.id)
            productSaved.name = product.name
            productSaved.categories?.clear()
            productSaved.categories =
                categoryService.converterCategories(user = user, categories = product.categories)
            productSaved.price = product.price
            productSaved.quantity = product.quantity
            return parseObject(
                origin = productRepository.save(productSaved),
                destination = ProductResponseVO::class.java
            )
        } else {
            throw ObjectDuplicateException(message = DUPLICATE_NAME_PRODUCT)
        }
    }

    @Transactional
    fun updatePriceProduct(
        user: User,
        productId: Long,
        price: PriceRequestVO
    ) {
        val productSaved = getProduct(user = user, productId = productId)
        productRepository.updatePriceProduct(
            userId = user.id,
            productId = productSaved.id,
            price = price.price
        )
    }

    @Transactional
    fun restockProduct(
        user: User,
        productId: Long,
        restockProduct: RestockProductRequestVO
    ) {
        val productSaved = getProduct(user = user, productId = productId)
        productRepository.restockProduct(
            userId = user.id,
            productId = productSaved.id,
            quantity = restockProduct.quantity
        )
    }

    @Transactional
    fun deleteProduct(
        user: User,
        productId: Long
    ) {
        val productSaved = getProduct(user = user, productId = productId)
        productSaved.categories = null
        productRepository.deleteProductById(userId = user.id, productId = productId)
    }

    companion object {
        const val PRODUCT_NOT_FOUND = "Product not found!"
        const val DUPLICATE_NAME_PRODUCT = "The product already exists"
    }
}
