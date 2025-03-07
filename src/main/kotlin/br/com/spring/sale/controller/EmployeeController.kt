package br.com.spring.sale.controller

import br.com.spring.sale.entity.user.User
import br.com.spring.sale.exceptions.ForbiddenActionRequestException
import br.com.spring.sale.service.EmployeeService
import br.com.spring.sale.utils.others.ConstantsUtils.EMPTY_FIELDS
import br.com.spring.sale.vo.employee.EmployeeResponseVO
import br.com.spring.sale.vo.employee.RegisterEmployeeRequestVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/spring/sale/employees/v1"])
class EmployeeController {

    @Autowired
    private lateinit var employeeService: EmployeeService

    @GetMapping
    fun finAllEmployees(
        @AuthenticationPrincipal user: User,
        @RequestParam(required = false) name: String?,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "12") size: Int,
        @RequestParam(value = "sort", defaultValue = "asc") sort: String
    ): ResponseEntity<Page<EmployeeResponseVO>> {
        val sortDirection: Sort.Direction =
            if ("desc".equals(sort, ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"))
        return ResponseEntity.ok(
            employeeService.finAllEmployees(user = user, name = name, pageable = pageable)
        )
    }

    @GetMapping(value = ["/{id}"])
    fun findEmployeeById(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") employeeId: Long
    ): EmployeeResponseVO {
        return employeeService.findEmployeeById(user = user, employeeId = employeeId)
    }

    @GetMapping(value = ["/find/employee/by/{name}"])
    fun findEmployeeByName(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "name") name: String,
    ): ResponseEntity<List<EmployeeResponseVO>> {
        return ResponseEntity.ok(
            employeeService.findEmployeeByName(user = user, name = name)
        )
    }

    @PostMapping
    fun createNewEmployee(
        @AuthenticationPrincipal user: User,
        @RequestBody employee: RegisterEmployeeRequestVO
    ): ResponseEntity<*> {
        require(
            value =
                employee.name.isNotEmpty() && employee.name.isNotBlank() &&
                        employee.surname.isNotEmpty() && employee.surname.isNotBlank() &&
                        employee.email.isNotEmpty() && employee.email.isNotBlank()
                        && employee.password.isNotEmpty() && employee.password.isNotBlank()
                        && employee.function != null
        ) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        employeeService.createNewEmployee(user, employee)
        return ResponseEntity.status(HttpStatus.CREATED).build<Any>()
    }

    @PatchMapping(value = ["/disabled/{id}"])
    fun disabledProfileEmployee(
        @PathVariable(value = "id") employeeId: Long
    ): ResponseEntity<*> {
        employeeService.disabledProfileEmployee(employeeId = employeeId)
        return ResponseEntity.noContent().build<Any>()
    }

    @PatchMapping(value = ["/enabled/{id}"])
    fun enabledProfileEmployee(
        @PathVariable(value = "id") employeeId: Long
    ): ResponseEntity<*> {
        employeeService.enabledProfileEmployee(employeeId = employeeId)
        return ResponseEntity.noContent().build<Any>()
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteEmployee(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") employeeId: Long
    ): ResponseEntity<*> {
        employeeService.deleteEmployee(user = user, employeeId = employeeId)
        return ResponseEntity.noContent().build<Any>()
    }
}
