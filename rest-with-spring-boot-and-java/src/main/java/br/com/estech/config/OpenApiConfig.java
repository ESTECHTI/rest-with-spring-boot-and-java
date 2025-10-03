package br.com.estech.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI custoomOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                    .title("REST AOI's RESTfull from 0 with Java, Spring Boot, Kubernetes and Docker")
                    .description("REST API's RESTful from 0 with Java, Spring Boot, Kubernetes and Docker")
                    .termsOfService("https://pub.erudio.com.br/meus-cursos")
                    .license(new License()
                        .name("APACHE 2.0")
                        .url("https://pub.erudio.com.br/meus-cursos"))
            );
    }
}
