package br.com.spring.sale.repository

import br.com.spring.sale.entity.category.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long?> {

    @Query(value = "SELECT c FROM Category c WHERE c.user.id = :userId AND c.name = :name")
    fun checkNameCategoryAlreadyExists(
        @Param(value = "userId") userId: Long? = null,
        @Param(value = "name") name: String
    ): Category?

    @Query(
        value = """
        SELECT c FROM Category c
            WHERE c.user.id = :userId
        AND (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
    """
    )
    fun findAllCategories(
        @Param(value = "userId") userId: Long? = null,
        @Param(value = "name") name: String?,
        pageable: Pageable
    ): Page<Category>

    @Query(
        value = "SELECT c FROM Category c WHERE c.user.id = :userId AND LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))"
    )
    fun findCategoryByName(
        @Param(value = "userId") userId: Long? = null,
        @Param(value = "name") name: String?
    ): List<Category>

    @Query(value = "SELECT c FROM Category c WHERE c.user.id = :userId AND c.id = :idCategory")
    fun findCategoryById(
        @Param(value = "userId") userId: Long? = null,
        @Param(value = "idCategory") categoryId: Long
    ): Category?

    @Modifying
    @Query(value = "DELETE FROM Category c WHERE c.id = :categoryId AND c.user.id = :userId")
    fun deleteCategoryById(
        @Param(value = "userId") userId: Long? = null,
        @Param(value = "categoryId") categoryId: Long
    ): Int
}
