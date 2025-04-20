package app.betterhm.backend.v1.controller;

import app.betterhm.backend.v1.component.MovieCache;
import app.betterhm.backend.v1.models.movies.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/movies")
public class MovieController {
    private final MovieCache movieCache;

    private final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    public MovieController(MovieCache movieCache) {
        this.movieCache = movieCache;
    }

    @GetMapping
    public List<Movie> getMovies() {
        var movies = movieCache.getMovies();
        logger.info("Found {} movies", movies.size());
        return movies;
    }
}
