package nl.demian.trivia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Value("${application.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Value("${application.cors.allowed-methods}")
    private String[] allowedMethods;

    @Value("${application.cors.allowed-headers}")
    private String[] allowedHeaders;

    @Value("${application.cors.exposed-headers}")
    private String[] exposedHeaders;

    @Value("${application.cors.allow-credentials}")
    private Boolean allowCredentials;

    @Value("${application.cors.max-age}")
    private Long maxAge;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(allowedMethods)
                .allowedHeaders(allowedHeaders)
                .exposedHeaders(exposedHeaders)
                .allowCredentials(allowCredentials)
                .maxAge(maxAge);
    }
}
