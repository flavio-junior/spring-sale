package br.com.spring.sale.entity.user

import br.com.spring.sale.entity.employee.Employee
import br.com.spring.sale.utils.common.TypeAccount
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant

@Entity
@Table(name = "tb_user")
class User : UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "created_at", nullable = false, unique = true)
    var createdAt: Instant? = null
    var name: String = ""
    var surname: String = ""

    @Column(unique = true)
    var email: String = ""

    @Column(name = "password", nullable = false, unique = true)
    private var password: String? = ""

    @Column(name = "type_account", nullable = false)
    @Enumerated(EnumType.STRING)
    var typeAccount: TypeAccount? = null

    @Column(name = "account_non_expired")
    var accountNonExpired: Boolean = true

    @Column(name = "account_non_locked")
    var accountNonLocked: Boolean = true

    @Column(name = "credentials_non_expired")
    var credentialsNonExpired: Boolean = true

    @Column(name = "enabled")
    var enabled: Boolean = true

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "tb_user_employee",
        joinColumns = [JoinColumn(name = "fk_user", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_employee", referencedColumnName = "id")]
    )
    var employee: Employee? = null

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        if (this.typeAccount == TypeAccount.ADMIN) {
            return arrayListOf(SimpleGrantedAuthority("ROLE_ADMIN"), SimpleGrantedAuthority("ROLE_USER"))
        }
        return arrayListOf(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String {
        return password!!
    }

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return enabled
    }
}
