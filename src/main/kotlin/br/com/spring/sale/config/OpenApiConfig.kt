package br.com.spring.sale.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI().info(
            Info().title("API RESTFul with Kotlin 1.8.22 and Spring Boot 3.1.4")
                .version("V1")
                .contact(
                    Contact()
                        .name("Flávio Júnior")
                        .email("flaviojunior.work@gmail.com")
                        .url("https://github.com/flavio-junior")
                )
                .description("Some description about your API.")
                .termsOfService("https://github.com/flavio-junior/spring-sale/blob/end/LICENSE")
                .license(License().name("Apache 2.0").url("https://github.com/flavio-junior/spring-sale/blob/end/LICENSE"))
        )
    }

}