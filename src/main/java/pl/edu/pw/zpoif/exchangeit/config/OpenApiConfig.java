package pl.edu.pw.zpoif.exchangeit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI(Environment env) {
        return new OpenAPI().info(new Info().title("ExchangeIt API").version(env.getProperty("application.version"))
                                            .description(
                                                    "API to serve as backend service for ExchangeIt web application")
                                            .license(new License().name("Apache 2.0")
                                                                  .url("http://www.apache.org/licenses/LICENSE-2.0")));
    }
}