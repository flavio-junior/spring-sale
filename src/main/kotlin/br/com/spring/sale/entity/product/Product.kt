package br.com.spring.sale.entity.product

import br.com.spring.sale.entity.category.Category
import br.com.spring.sale.entity.company.Company
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "tb_product")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime? = null,
    var name: String = "",
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "tb_product_category",
        joinColumns = [JoinColumn(name = "fk_product", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_category", referencedColumnName = "id")]
    )
    var categories: MutableList<Category>? = null,
    var price: Double = 0.0,
    @Column(name = "stock_quantity", nullable = false)
    var quantity: Int? = 0,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "tb_company_product",
        joinColumns = [JoinColumn(name = "fk_product", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_company", referencedColumnName = "id")]
    )
    var company: Company? = null
)
