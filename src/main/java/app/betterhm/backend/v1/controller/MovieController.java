package app.betterhm.backend.v1.controller;

import app.betterhm.backend.v1.component.MovieCache;
import app.betterhm.backend.v1.models.movies.Movie;
import app.betterhm.backend.v1.services.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/movies")
public class MovieController {
    private final MovieCache movieCache;
    private final MovieService movieService;

    private final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    public MovieController(MovieCache movieCache, MovieService movieService) {
        this.movieCache = movieCache;
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getMovies() {
        var movies = movieCache.getMovies();
        logger.info("Found {} movies", movies.size());
        return movies;
    }

    @GetMapping("/fetch")
    public void fetch() {
        movieService.updateMovies();
    }

    @GetMapping("/test")
    public List<Movie> test() {
        Movie movie = new Movie(
                "Inception", LocalDate.of(2010, 7, 16), "20:00", "FSK 12", "Sci-Fi", 148,
                "A mind-bending thriller", "The content of the movie", "Room A", "coverUrl",
                "coverBlurhash", "trailerUrl", "unifilmUrl"
        );
        return List.of(movie); // Return a list with one movie object for testing
    }
}
