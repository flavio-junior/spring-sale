package br.com.spring.sale.repository

import br.com.spring.sale.entity.employee.Employee
import br.com.spring.sale.utils.common.StatusEmployee
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {

    @Query(
        value = """
        SELECT e FROM Employee e
            WHERE e.company.id = :companyId
        AND (:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')))
    """
    )
    fun findAllEmployees(
        @Param("companyId") companyId: Long? = null,
        @Param("name") name: String?,
        pageable: Pageable
    ): Page<Employee>?

    @Query(value = "SELECT e FROM Employee e WHERE e.company.id = :companyId AND e.id = :employeeId")
    fun findEmployeeById(
        @Param("companyId") companyId: Long? = null,
        @Param("employeeId") employeeId: Long
    ): Employee?

    @Query(
        value = "SELECT e FROM Employee e WHERE e.company.id = :companyId AND LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))"
    )
    fun findEmployeeByName(
        @Param("companyId") companyId: Long? = null,
        @Param("name") name: String?
    ): List<Employee>

    @Modifying
    @Query("UPDATE Employee e SET e.status =:status WHERE e.id = :employeeId")
    fun changeStatusEmployee(
        @Param("employeeId") employeeId: Long,
        @Param("status") status: StatusEmployee
    )

    @Modifying
    @Query(value = "DELETE FROM Employee e WHERE e.id = :employeeId AND e.company.id = :companyId")
    fun deleteEmployeeById(
        @Param("companyId") companyId: Long? = null,
        @Param("employeeId") employeeId: Long
    ): Int
}
