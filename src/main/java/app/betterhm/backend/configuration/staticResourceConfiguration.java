package app.betterhm.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.time.Duration;

/**
 * This class is responsible configuring spring to serve static resources
 */
@Configuration
@EnableWebMvc
public class staticResourceConfiguration implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
				.addResourceLocations("file:resources/static/")
				.setCacheControl(CacheControl.maxAge(Duration.ofDays(14)))
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
	}
}