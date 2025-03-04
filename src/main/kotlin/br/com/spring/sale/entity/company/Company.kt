package br.com.spring.sale.entity.company

import br.com.spring.sale.entity.employee.Employee
import br.com.spring.sale.entity.user.User
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "tb_company")
data class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    var identifier: Long? = 0,
    var date: LocalDate? = null,
    var hour: LocalTime? = null,
    @Column(length = 40, nullable = false, unique = true)
    var name: String = "",
    @Column(name = "main_image", nullable = false)
    var mainImage: String? = "",
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "tb_user_company",
        joinColumns = [JoinColumn(name = "fk_company", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_user", referencedColumnName = "id")]
    )
    var user: User? = null,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "tb_company_employee",
        joinColumns = [JoinColumn(name = "fk_company", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_employee", referencedColumnName = "id")]
    )
    var employee: Employee? = null
)
