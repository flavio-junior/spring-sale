package br.com.spring.sale.service

import br.com.spring.sale.entity.category.Category
import br.com.spring.sale.entity.user.User
import br.com.spring.sale.exceptions.ObjectDuplicateException
import br.com.spring.sale.exceptions.ResourceNotFoundException
import br.com.spring.sale.repository.CategoryRepository
import br.com.spring.sale.utils.others.ConverterUtils.parseObject
import br.com.spring.sale.vo.category.CategoryResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService {

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var companyService: CompanyService

    @Transactional(readOnly = true)
    fun findAllCategories(
        user: User,
        name: String?,
        pageable: Pageable
    ): Page<CategoryResponseVO> {
        val companySaved = companyService.getCompanyByUserLogged(user = user)
        val categories: Page<Category> =
            categoryRepository.findAllCategories(companyId = companySaved.id, name = name, pageable = pageable)
        return categories.map { category -> parseObject(category, CategoryResponseVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findCategoryByName(
        user: User,
        name: String
    ): List<CategoryResponseVO> {
        val companySaved = companyService.getCompanyByUserLogged(user = user)
        val products: List<Category> = categoryRepository.findCategoryByName(companyId = companySaved.id, name = name)
        return products.map { product -> parseObject(product, CategoryResponseVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findCategoryById(
        user: User,
        categoryId: Long
    ): CategoryResponseVO {
        val category = getCategory(categoryId = categoryId, user = user)
        return parseObject(category, CategoryResponseVO::class.java)
    }

    fun getCategory(
        user: User,
        categoryId: Long
    ): Category {
        val companySaved = companyService.getCompanyByUserLogged(user = user)
        val categorySaved: Category? =
            categoryRepository.findCategoryById(companyId = companySaved.id, categoryId = categoryId)
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
        val companySaved = companyService.getCompanyByUserLogged(user = user)
        if (!checkNameCategoryAlreadyExists(companyId = companySaved.id, name = category.name)) {
            val categoryResult: Category = parseObject(category, Category::class.java)
            categoryResult.company = companySaved
            return parseObject(categoryRepository.save(categoryResult), CategoryResponseVO::class.java)
        } else {
            throw ObjectDuplicateException(message = DUPLICATE_NAME_CATEGORY)
        }
    }

    private fun checkNameCategoryAlreadyExists(
        companyId: Long? = null,
        name: String
    ): Boolean {
        val categoryResult = categoryRepository.checkNameCategoryAlreadyExists(companyId = companyId, name = name)
        return categoryResult != null
    }

    fun updateCategory(
        user: User,
        category: CategoryResponseVO
    ): CategoryResponseVO {
        val companySaved = companyService.getCompanyByUserLogged(user = user)
        if (!checkNameCategoryAlreadyExists(companyId = companySaved.id, name = category.name)) {
            val categoryResult: Category = getCategory(user = user, categoryId = category.id)
            categoryResult.name = category.name
            return parseObject(categoryRepository.save(categoryResult), CategoryResponseVO::class.java)
        } else {
            throw ObjectDuplicateException(message = DUPLICATE_NAME_CATEGORY)

        }
    }

    @Transactional
    fun deleteCategory(
        user: User,
        categoryId: Long
    ) {
        val companySaved = companyService.getCompanyByUserLogged(user = user)
        val category = getCategory(user = user, categoryId = categoryId)
        categoryRepository.deleteCategoryById(categoryId = category.id, companyId = companySaved.id)
    }

    companion object {
        const val CATEGORY_NOT_FOUND = "Category not found!"
        const val DUPLICATE_NAME_CATEGORY = "The category already exists"
    }
}
