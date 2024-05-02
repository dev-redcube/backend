package app.betterhm.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * This class is responsible configuring spring to serve static resources
 */
@Configuration
@EnableWebMvc
public class staticResourceConfiguration implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
				.addResourceLocations("classpath:/static/")
				.setCachePeriod( 21600000 ) // 6 hours
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
	}
}