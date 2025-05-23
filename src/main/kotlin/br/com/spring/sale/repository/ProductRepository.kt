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

    @Query("SELECT p FROM Product p WHERE p.company.id = :companyId AND p.name = :name")
    fun checkNameProductAlreadyExists(
        @Param(value = "companyId") companyId: Long? = null,
        @Param(value = "name") name: String
    ): Product?

    @Query(
        value = """
        SELECT p FROM Product p
            WHERE p.company.id = :companyId
        AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
    """
    )
    fun findAllProducts(
        @Param(value = "companyId") companyId: Long? = null,
        @Param(value = "name") name: String?,
        pageable: Pageable
    ): Page<Product>?

    @Query(value = "SELECT p FROM Product p WHERE p.company.id = :companyId AND p.id = :productId")
    fun findProductById(
        @Param(value = "companyId") companyId: Long? = null,
        @Param(value = "productId") productId: Long
    ): Product?

    @Query(
        value = "SELECT p FROM Product p WHERE p.company.id = :companyId AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND p.quantity > 0"
    )
    fun findProductByName(
        @Param(value = "companyId") companyId: Long? = null,
        @Param(value = "name") name: String?
    ): List<Product>

    @Modifying
    @Query(value = "UPDATE Product p SET p.price =:price WHERE p.company.id = :companyId AND p.id =:id")
    fun updatePriceProduct(
        @Param(value = "companyId") companyId: Long? = null,
        @Param(value = "id") idProduct: Long,
        @Param(value = "price") price: Double
    )

    @Modifying
    @Query(value = "UPDATE Product p SET p.quantity = p.quantity + :quantity WHERE p.company.id = :companyId AND p.id = :id")
    fun restockProduct(
        @Param(value = "companyId") companyId: Long? = null,
        @Param(value = "id") idProduct: Long,
        @Param(value = "quantity") quantity: Int
    )

    @Modifying
    @Query(value = "DELETE FROM Product p WHERE p.id = :productId AND p.company.id = :companyId")
    fun deleteProductById(
        @Param(value = "companyId") companyId: Long? = null,
        @Param(value = "productId") productId: Long
    ): Int
}
