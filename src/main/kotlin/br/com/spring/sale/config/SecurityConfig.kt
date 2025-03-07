package br.com.spring.sale.config

import br.com.spring.sale.security.JwtTokenFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Autowired
    var securityFilter: JwtTokenFilter? = null

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun securityFilterChain(
        httpSecurity: HttpSecurity
    ): SecurityFilterChain {
        return httpSecurity
            .httpBasic { basic: HttpBasicConfigurer<HttpSecurity> -> basic.disable() }
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(
                        "/api/auth/v1/confirm-email-address",
                        "/api/auth/v1/check-code-existent/{code}",
                        "/api/auth/v1/update-code-verification-email/{email}",
                        "/api/auth/v1/signUp",
                        "/api/auth/v1/signIn",
                        "/api/auth/v1/recover-password",
                        "/api/auth/v1/check-recover-password/{code}",
                        "/api/auth/v1/new-password",
                        "/api/auth/v1/refresh/{email}",
                        "/v3/api-docs/**",
                        "/swagger-ui/**"
                    ).permitAll()
                    .requestMatchers("/api/spring/sale/categories/v1/**").hasRole("ADMIN")
                    .requestMatchers("/api/spring/sale/products/v1/**").hasRole("ADMIN")
                    .requestMatchers("/api/spring/sale/companies/v1/**").hasRole("ADMIN")
                    .requestMatchers("/api/spring/sale/employees/v1/**").hasRole("ADMIN")
                    .requestMatchers("/api/spring/sale/use/settings/v1/**").authenticated()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .cors { _: CorsConfigurer<HttpSecurity?>? -> }
            .build()
    }
}
