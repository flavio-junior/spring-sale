package br.com.spring.sale.service

import br.com.spring.sale.entity.user.User
import br.com.spring.sale.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProfileUserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var categoryService: CategoryService

    @Transactional
    fun deleteMyAccount(
        user: User
    ) {
        productService.deleteAllProduct(user = user)
        categoryService.deleteAllCategories(user = user)
        userRepository.delete(user)
    }
}
