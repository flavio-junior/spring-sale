package br.com.spring.sale.service

import br.com.spring.sale.entity.category.Category
import br.com.spring.sale.entity.user.User
import br.com.spring.sale.exceptions.DatabaseException
import br.com.spring.sale.exceptions.ObjectDuplicateException
import br.com.spring.sale.exceptions.ResourceNotFoundException
import br.com.spring.sale.repository.CategoryRepository
import br.com.spring.sale.utils.others.ConverterUtils.parseObject
import br.com.spring.sale.vo.category.CategoryResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService {

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var userService: UserService

    @Transactional(readOnly = true)
    fun findAllCategories(
        user: User,
        name: String?,
        pageable: Pageable
    ): Page<CategoryResponseVO> {
        val categories: Page<Category> =
            categoryRepository.findAllCategories(userId = user.id, name = name, pageable = pageable)
        return categories.map { category ->
            parseObject(origin = category, destination = CategoryResponseVO::class.java)
        }
    }

    @Transactional(readOnly = true)
    fun findCategoryByName(
        user: User,
        name: String
    ): List<CategoryResponseVO> {
        val products: List<Category> = categoryRepository.findCategoryByName(userId = user.id, name = name)
        if (products.isNotEmpty()) {
            return products.map { product ->
                parseObject(origin = product, destination = CategoryResponseVO::class.java)
            }
        } else {
            throw ResourceNotFoundException(message = CATEGORY_NOT_FOUND)
        }
    }

    @Transactional(readOnly = true)
    fun findCategoryById(
        user: User,
        categoryId: Long
    ): CategoryResponseVO {
        val category = getCategory(categoryId = categoryId, user = user)
        return parseObject(origin = category, destination = CategoryResponseVO::class.java)
    }

    fun getCategory(
        user: User,
        categoryId: Long
    ): Category {
        val categorySaved: Category? =
            categoryRepository.findCategoryById(userId = user.id, categoryId = categoryId)
        if (categorySaved != null) {
            return categorySaved
        } else {
            throw ResourceNotFoundException(message = CATEGORY_NOT_FOUND)
        }
    }

    fun converterCategories(
        user: User,
        categories: MutableList<CategoryResponseVO>? = null
    ): MutableList<Category>? {
        val result = categories?.map { category ->
            getCategory(categoryId = category.id, user = user)
        }?.toMutableList()
        return result
    }

    @Transactional
    fun createNewCategory(
        user: User,
        category: CategoryResponseVO
    ): CategoryResponseVO {
        if (!checkNameCategoryAlreadyExists(userId = user.id, name = category.name)) {
            val categoryResult: Category = parseObject(category, Category::class.java)
            categoryResult.user = userService.findUserById(userId = user.id)
            return parseObject(
                origin = categoryRepository.save(categoryResult),
                destination = CategoryResponseVO::class.java
            )
        } else {
            throw ObjectDuplicateException(message = DUPLICATE_NAME_CATEGORY)
        }
    }

    private fun checkNameCategoryAlreadyExists(
        userId: Long? = null,
        name: String
    ): Boolean {
        val categoryResult = categoryRepository.checkNameCategoryAlreadyExists(userId = userId, name = name)
        return categoryResult != null
    }

    fun updateCategory(
        user: User,
        category: CategoryResponseVO
    ): CategoryResponseVO {
        if (!checkNameCategoryAlreadyExists(userId = user.id, name = category.name)) {
            val categoryResult: Category = getCategory(user = user, categoryId = category.id)
            categoryResult.name = category.name
            return parseObject(
                origin = categoryRepository.save(categoryResult),
                destination = CategoryResponseVO::class.java
            )
        } else {
            throw ObjectDuplicateException(message = DUPLICATE_NAME_CATEGORY)

        }
    }

    @Transactional
    fun deleteAllCategories(
        user: User
    ) {
        val allCategories = categoryRepository.findAllCategoriesByUserLogged(userId = user.id)
        allCategories.forEach { categorySaved ->
            deleteCategory(user = user, categoryId = categorySaved.id)
        }
    }

    @Transactional
    fun deleteCategory(
        user: User,
        categoryId: Long
    ) {
        try {
            val category = getCategory(user = user, categoryId = categoryId)
            categoryRepository.deleteCategoryById(categoryId = category.id, userId = user.id)
        } catch (e: DataIntegrityViolationException) {
            throw DatabaseException()
        }
    }

    companion object {
        const val CATEGORY_NOT_FOUND = "Category not found!"
        const val DUPLICATE_NAME_CATEGORY = "The category already exists"
    }
}
