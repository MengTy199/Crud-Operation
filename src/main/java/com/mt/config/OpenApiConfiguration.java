package com.mt.config;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public GroupedOpenApi backEndGorupApi() {
        return GroupedOpenApi.builder().group("BackEnd-api").addOpenApiCustomizer(openApi ->
                openApi.info(getBackendApiInfo())).packagesToScan("com.mt.controller.BackEnd").build();
    }

    @Bean
    public GroupedOpenApi frontEndGorupApi() {
        return GroupedOpenApi.builder().group("FrontEnd-api").addOpenApiCustomizer(openApi ->
                openApi.info(getBackendApiInfo())).packagesToScan("com.mt.controller.FrontEnd").build();
    }

    //
    private Info getBackendApiInfo() {
        Contact contact = new Contact();
        contact.setName("Mengty");
        contact.setEmail("Mengty@gamil.com");

        return new Info().title("Spring Ecommerce Backed API").description("Backend Ecommerce API").contact(contact).version("1.0.1");
    }
}


