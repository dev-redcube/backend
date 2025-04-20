package app.betterhm.backend.v1.component;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MovieApiClient {
    private RestTemplate restTemplate = new RestTemplate();

    public String fetchMovies() {
        return restTemplate.getForObject("https://unifilm.eu/kinodateien/api/api_hs_muenchen.php", String.class);
    }
}
