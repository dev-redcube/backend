package app.betterhm.backend.v1.models.movies;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record Movie(
        String title,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        String time,
        String fsk,
        String genre,
        int length,
        String info,
        String content,
        String room,
        String coverUrl,
        String coverBlurhash,
        String trailerUrl,
        String unifilmUrl
) {
    public Movie withBlurhash(String blurhash) {
        return new Movie(
                title,
                date,
                time,
                fsk,
                genre,
                length,
                info,
                content,
                room,
                coverUrl,
                blurhash,
                trailerUrl,
                unifilmUrl
        );
    }
}
