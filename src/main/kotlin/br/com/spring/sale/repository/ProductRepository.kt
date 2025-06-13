package br.com.spring.sale.repository

import br.com.spring.sale.entity.product.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p WHERE p.user.id = :userId AND p.name = :name")
    fun checkNameProductAlreadyExists(
        @Param(value = "userId") userId: Long? = null,
        @Param(value = "name") name: String
    ): Product?

    @Query(
        value = """
        SELECT p FROM Product p
            WHERE p.user.id = :userId
        AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
    """
    )
    fun findAllProducts(
        @Param(value = "userId") userId: Long? = null,
        @Param(value = "name") name: String?,
        pageable: Pageable
    ): Page<Product>?

    @Query(value = "SELECT p FROM Product p WHERE p.user.id = :userId")
    fun findAllProductsByUserLogged(
        @Param(value = "userId") userId: Long? = null,
    ): List<Product>?

    @Query(value = "SELECT p FROM Product p WHERE p.user.id = :userId AND p.id = :productId")
    fun findProductById(
        @Param(value = "userId") userId: Long? = null,
        @Param(value = "productId") productId: Long
    ): Product?

    @Modifying
    @Query(value = "UPDATE Product p SET p.price =:price WHERE p.user.id = :userId AND p.id =:productId")
    fun updatePriceProduct(
        @Param(value = "userId") userId: Long? = null,
        @Param(value = "productId") productId: Long,
        @Param(value = "price") price: Double
    )

    @Modifying
    @Query(value = "UPDATE Product p SET p.quantity = p.quantity + :quantity WHERE p.user.id = :userId AND p.id = :productId")
    fun restockProduct(
        @Param(value = "userId") userId: Long? = null,
        @Param(value = "productId") productId: Long,
        @Param(value = "quantity") quantity: Int
    )

    @Modifying
    @Query(value = "DELETE FROM Product p WHERE p.id = :productId AND p.user.id = :userId")
    fun deleteProductById(
        @Param(value = "userId") userId: Long? = null,
        @Param(value = "productId") productId: Long
    ): Int
}
