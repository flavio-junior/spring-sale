package br.com.spring.sale.controller

import br.com.spring.sale.entity.user.User
import br.com.spring.sale.exceptions.ForbiddenActionRequestException
import br.com.spring.sale.service.EmployeeService
import br.com.spring.sale.utils.common.PriceRequestVO
import br.com.spring.sale.utils.others.ConstantsUtils.EMPTY_FIELDS
import br.com.spring.sale.utils.others.MediaType.APPLICATION_JSON
import br.com.spring.sale.vo.employee.EmployeeResponseVO
import br.com.spring.sale.vo.employee.RegisterEmployeeRequestVO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
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
@Tag(name = "Employee", description = "EndPoint For Managing All Employees")
class EmployeeController {

    @Autowired
    private lateinit var employeeService: EmployeeService

    @GetMapping(produces = [APPLICATION_JSON])
    @Operation(
        summary = "List All Employees",
        description = "List All Employees",
        tags = ["Employee"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
                    Content(array = ArraySchema(schema = Schema(implementation = EmployeeResponseVO::class)))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
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

    @GetMapping(
        value = ["/{id}"],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Find Employee By Id",
        description = "Find Employee By Id",
        tags = ["Employee"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
                    Content(array = ArraySchema(schema = Schema(implementation = EmployeeResponseVO::class)))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun findEmployeeById(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") employeeId: Long
    ): EmployeeResponseVO {
        return employeeService.findEmployeeById(user = user, employeeId = employeeId)
    }

    @PostMapping(
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Create New Employee",
        description = "Create New Employee",
        tags = ["Employee"],
        responses = [
            ApiResponse(
                description = "Created", responseCode = "201", content = [
                    Content(schema = Schema(implementation = EmployeeResponseVO::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Conflict", responseCode = "409", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
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
        employeeService.createNewEmployee(user = user, employee = employee)
        return ResponseEntity.status(HttpStatus.CREATED).build<Any>()
    }

    @PatchMapping(
        value = ["/disabled/{id}"],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Update Price of Employee",
        description = "Update Price of Employee",
        tags = ["Employee"],
        responses = [
            ApiResponse(
                description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = PriceRequestVO::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Conflict", responseCode = "409", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun disabledProfileEmployee(
        @PathVariable(value = "id") employeeId: Long
    ): ResponseEntity<*> {
        employeeService.disabledProfileEmployee(employeeId = employeeId)
        return ResponseEntity.noContent().build<Any>()
    }

    @PatchMapping(
        value = ["/enabled/{id}"],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Update Price of Employee",
        description = "Update Price of Employee",
        tags = ["Employee"],
        responses = [
            ApiResponse(
                description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = PriceRequestVO::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Conflict", responseCode = "409", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun enabledProfileEmployee(
        @PathVariable(value = "id") employeeId: Long
    ): ResponseEntity<*> {
        employeeService.enabledProfileEmployee(employeeId = employeeId)
        return ResponseEntity.noContent().build<Any>()
    }

    @DeleteMapping(
        value = ["/{id}"],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Delete Employee",
        description = "Delete Employee",
        tags = ["Employee"],
        responses = [
            ApiResponse(
                description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun deleteEmployee(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") employeeId: Long
    ): ResponseEntity<*> {
        employeeService.deleteEmployee(user = user, employeeId = employeeId)
        return ResponseEntity.noContent().build<Any>()
    }
}
