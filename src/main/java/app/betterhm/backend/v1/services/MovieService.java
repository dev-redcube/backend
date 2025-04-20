package app.betterhm.backend.v1.services;

import app.betterhm.backend.v1.component.MovieApiClient;
import app.betterhm.backend.v1.component.MovieCache;
import app.betterhm.backend.v1.models.movies.ExternalMovieModel;
import app.betterhm.backend.v1.models.movies.Movie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.trbl.blurhash.BlurHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private final MovieApiClient apiClient;
    private final ObjectMapper objectMapper;
    private final MovieCache movieCache;

    private final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    public MovieService(MovieApiClient apiClient, ObjectMapper objectMapper, MovieCache movieCache) {
        this.apiClient = apiClient;
        this.objectMapper = objectMapper;
        this.movieCache = movieCache;
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void updateMovies() {
        var rawJson = apiClient.fetchMovies();
        try {
            List<ExternalMovieModel> mapped = objectMapper
                    .readValue(rawJson, new TypeReference<List<ExternalMovieModel>>() {
                    });

            List<Movie> mappedToInternal = mapped.stream().map(this::mapToInternalModel).toList();

            List<Movie> complete = new ArrayList<>();
            for (Movie movie : mappedToInternal) {
                var newMovie = movie.withBlurhash(getCoverBlurhash(movie));
                logger.info("movie {} complete: {}", movie.title(), movie);
                complete.add(newMovie);
            }

            movieCache.setMovies(complete);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCoverBlurhash(Movie m) throws IOException {
        logger.info("Fetching Cover for Movie {}", m.title());
        BufferedImage img = ImageIO.read(new URL(m.coverUrl()).openStream());

        logger.info("Fetched Cover for Movie {}", m.title());

        return BlurHash.encode(img);
    }

    private Movie mapToInternalModel(ExternalMovieModel external) {
        var room = external.Abw_Raum();
        if (room == null)
            room = external.Allg_Raum();

        return new Movie(
                external.Termin_Titel(),
                LocalDate.parse(external.Termin_Datum()),
                external.Termin_Uhrzeit(),
                external.FSK(),
                external.Genre(),
                Integer.parseInt(external.Laufzeit()),
                external.Filminfo(),
                external.Filminhalt(),
                room,
                external.Img_Filmplakat(),
                null,
                external.URL_Trailer(),
                external.MD5_Rand()
        );
    }
}
