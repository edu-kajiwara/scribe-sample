package jp.ac.tama.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by kajiwarayutaka on 2017/07/04.
 */
@Component
public class CorsConfigurer extends WebMvcConfigurerAdapter {

    private final String clientUrl;

    public CorsConfigurer(@Value("${client.url}") String clientUrl) {
        this.clientUrl = clientUrl;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(clientUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTION")
                .allowedHeaders("Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers","Authorization",
                        "X-Requested-With")
                .maxAge(3600);
    }
}
