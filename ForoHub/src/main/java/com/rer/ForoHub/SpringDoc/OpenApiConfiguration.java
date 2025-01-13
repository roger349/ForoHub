package com.rer.ForoHub.SpringDoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfiguration {

    @Bean
   public OpenAPI customOpenAPI() {
       return new OpenAPI()
               .info(new Info().title("ForoHub"))
               .addSecurityItem(new SecurityRequirement().addList("ForoHubSecurityScheme"))
               .components(new Components().addSecuritySchemes("ForoHubSecurityScheme", new SecurityScheme()
                       .name("ForoHubSecurityScheme").type(SecurityScheme.Type.HTTP)
                       .scheme("bearer").bearerFormat("JWT")));

   }
}


