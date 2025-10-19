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
    public Movie(
            String title,
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
        this.title = title;
        this.date = date;
        this.time = time;
        this.fsk = fsk;
        this.genre = genre;
        this.length = length;
        this.info = info;
        this.content = content;
        this.room = room;
        this.coverUrl = sanitizeUrl(coverUrl);
        this.coverBlurhash = coverBlurhash;
        this.trailerUrl = sanitizeUrl(trailerUrl);
        this.unifilmUrl = sanitizeUrl(unifilmUrl);
    }


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

    /**
     * Sanitizes a URL string by:
     * - Trimming whitespace and converting backslashes to slashes
     * - Splitting into components (scheme, authority, path, query, fragment)
     * - Letting URI component constructor percent-encode illegal characters
     * - Normalizing the path (removes dot-segments) and returning ASCII form
     *
     * @param url The input URL string
     * @return A sanitized URL string, or IllegalArgumentException if invalid
     */
    private static String sanitizeUrl(String url)
    {
        if (url == null) return null;

        // 1) Trim and normalize slashes
        String s = url.trim().replace('\\', '/');

        // 2) Split off fragment and query (keep raw, we'll let URI quote as needed)
        String fragment = null;
        int hash = s.indexOf('#');
        if (hash >= 0) {
            fragment = s.substring(hash + 1);
            s = s.substring(0, hash);
        }

        String query = null;
        int q = s.indexOf('?');
        if (q >= 0) {
            query = s.substring(q + 1);
            s = s.substring(0, q);
        }

        // 3) Extract scheme/authority/path (simple but covers common cases)
        String scheme = null;
        String authority = null;
        String path = null;

        int schemeSep = s.indexOf("://");
        if (schemeSep > 0) {
            scheme = s.substring(0, schemeSep);
            int authStart = schemeSep + 3;
            int slash = s.indexOf('/', authStart);
            if (slash >= 0) {
                authority = s.substring(authStart, slash);
                path = s.substring(slash); // includes leading '/'
            } else {
                authority = s.substring(authStart);
            }
        } else if (s.startsWith("//")) {
            // scheme-relative URL
            int authStart = 2;
            int slash = s.indexOf('/', authStart);
            authority = (slash >= 0) ? s.substring(authStart, slash) : s.substring(authStart);
            path = (slash >= 0) ? s.substring(slash) : null;
        } else {
            // No authority—treat remaining as path (relative or absolute)
            path = s.isEmpty() ? null : s;
        }

        // 4) Fix stray '%' so the builder can safely quote everything else
        query = fixStrayPercents(query);
        fragment = fixStrayPercents(fragment);
        path = fixStrayPercents(path);

        // 5) Build and return a normalized ASCII URL
        try {
            java.net.URI uri = new java.net.URI(scheme, authority, path, query, fragment).normalize();
            return uri.toASCIIString(); // encodes spaces as %20, ensures ASCII
        } catch (java.net.URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL", e);
        }
    }

    // --- helpers ---

    private static String fixStrayPercents(String s) {
        if (s == null || s.indexOf('%') < 0) return s;
        StringBuilder out = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '%' && !(i + 2 < s.length() && isHex(s.charAt(i + 1)) && isHex(s.charAt(i + 2)))) {
                out.append("%25"); // encode stray '%'
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }

    private static boolean isHex(char c) {
        return (c >= '0' && c <= '9')
                || (c >= 'a' && c <= 'f')
                || (c >= 'A' && c <= 'F');
    }
}