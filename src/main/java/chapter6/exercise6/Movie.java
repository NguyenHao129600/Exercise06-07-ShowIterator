package chapter6.exercise6;

import java.util.Objects;

/** An individual movie; immutable. */
public final class Movie implements Show {
    private final String title;
    private final int year;
    private final int duration;

    public Movie(String title, int year, int duration) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Movie title must not be blank");
        }
        if (year <= 0 || duration <= 0) {
            throw new IllegalArgumentException("Year and duration must be positive");
        }
        this.title = title;
        this.year = year;
        this.duration = duration;
    }

    @Override
    public String description() {
        return title + " (" + year + ")";
    }

    @Override
    public int runningTime() {
        return duration;
    }

    @Override
    public Show copy() {
        return new Movie(title, year, duration);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Movie movie)) return false;
        return year == movie.year && duration == movie.duration
                && title.equals(movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, duration);
    }
}
