package br.com.spring.sale.entity.employee

import br.com.spring.sale.entity.company.Company
import br.com.spring.sale.utils.common.Function
import br.com.spring.sale.utils.common.StatusEmployee
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tb_employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime? = null,
    @Column(name = "name", nullable = false)
    var name: String = "",
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varying")
    var function: Function? = null,
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varying")
    var status: StatusEmployee? = null,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "tb_company_employee",
        joinColumns = [JoinColumn(name = "fk_employee", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_company", referencedColumnName = "id")]
    )
    var company: Company? = null
)
