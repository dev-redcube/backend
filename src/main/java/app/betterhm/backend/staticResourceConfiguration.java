package app.betterhm.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
@Configuration
@EnableWebMvc
public class staticResourceConfiguration implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/static/**")
				.addResourceLocations("classpath:/static/")
				.setCachePeriod(3600)
				.resourceChain(true)
				.addResolver(new EncodedResourceResolver());
	}
}