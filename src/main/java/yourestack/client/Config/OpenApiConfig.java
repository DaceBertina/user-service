package yourestack.client.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;

@Configuration
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Estack API", version = "1.0", description = "IT Educational courses."))
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        String[] packagesToScan = {"yourestack.client"};
        return GroupedOpenApi.builder()
                .group("MainController")
                .packagesToScan(packagesToScan)
                .pathsToMatch("api/v1/**")
                .build();
    }

//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("ManagerController")
//                .pathsToMatch("/admin/**")
//                .addOpenApiMethodFilter(method -> method.isAnnotationPresent(Annotation.class))
//                .build();
   // }

    @Bean
    public OpenAPI estackOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Spring Boot website yourestack.com")
                        .description("IT educational courses")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring Boot website yourestack.com")
                        .url("https://github.com.DaceBertina/your-estack"));
    }
}
