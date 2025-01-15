package com.rer.ForoHub.SpringDoc;



import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ForoHub Api")
                        .version("1.0.0")
                        .description("App ForoHub Challenger")
                        .contact(new Contact()
                                .name("RRR")
                                .url("http://ForoHubApp.com")
                                .email("ForoHub@Gmail.com")));
    }
    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("GrupoForoHud-api")
                .pathsToMatch("/login","/registrarUsuario","/usuarios/listarUsuarios", "/usuarios/obtenerUsuarioId/{id}",
                              "/usuarios/actualizarUsuarioId/{id}", "/usuarios/eliminarUsuarioId/{id}",
                              "/topicos/listarTopicos","/topicos/listarTopicoPorIdRespuestas/{id}",
                              "/topicos/crearTopico","/topicos/actualizarTopicoId/{id}","/topicos/eliminarTopicoId/{id}",
                              "/respuestas/listarRespuestas","/respuestas/crearRespuesta/{id}",
                              "/respuestas/actualizarRespuestaId/{id}","/respuestas/eliminarRespuestaId/{id}"
                ).build();
    }
}


