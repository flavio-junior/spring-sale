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

    @Query(value = "SELECT c FROM Category c WHERE c.company.id = :companyId AND c.name = :name")
    fun checkNameCategoryAlreadyExists(
        @Param("companyId") companyId: Long? = null,
        @Param("name") name: String
    ): Category?

    @Query(
        value = """
        SELECT c FROM Category c
            WHERE c.company.id = :companyId
        AND (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
    """
    )
    fun findAllCategories(
        @Param("companyId") companyId: Long? = null,
        @Param("name") name: String?,
        pageable: Pageable
    ): Page<Category>

    @Query(value = "SELECT c FROM Category c WHERE c.company.id = :companyId AND c.id = :idCategory")
    fun findCategoryById(
        @Param("companyId") companyId: Long? = null,
        @Param("idCategory") categoryId: Long
    ): Category?

    @Query(
        value = "SELECT c FROM Category c WHERE c.company.id = :companyId AND LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))"
    )
    fun findCategoryByName(
        @Param("companyId") companyId: Long? = null,
        @Param("name") name: String?
    ): List<Category>

    @Modifying
    @Query(value = "DELETE FROM Category c WHERE c.id = :categoryId AND c.company.id = :companyId")
    fun deleteCategoryById(
        @Param("companyId") companyId: Long? = null,
        @Param("categoryId") categoryId: Long
    ): Int
}
