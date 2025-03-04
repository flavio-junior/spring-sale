package br.com.spring.sale.repository

import br.com.spring.sale.entity.company.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepository : JpaRepository<Company, Long> {

    @Query(value = "SELECT c FROM Company c WHERE c.user.id = :userLoggedId")
    fun getCompanyByUserLogged(
        @Param("userLoggedId") userLoggedId: Long? = null
    ): Company?

    @Query(value = "SELECT c FROM Company c WHERE c.employee.id = :employeeLoggedId")
    fun getCompanyByEmployeeLogged(
        @Param("employeeLoggedId") employeeLoggedId: Long?
    ): Company?
}
