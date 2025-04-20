package app.betterhm.backend.v1.models.movies;

public record ExternalMovieModel(
        String MD5_Rand,
        String Termin_Titel,
        String Termin_Datum,
        String Termin_Uhrzeit,
        String Termin_Sortierung,
        String Allg_Raum,
        String Abw_Raum,
        String Allg_Einlass,
        String Abw_Einlass,
        String Sprachinfo,
        String Zusatzinfo,
        String Filmtitel_Kachel_Gross,
        String FSK,
        String Genre,
        String Laufzeit,
        String Filminfo,
        String Filminhalt,
        String Img_Filmplakat,
        String Img_Filmausschnitt,
        String URL_Trailer,
        String Ausfall_Ja_Nein
) {}
