package chapter6.exercise7;

import java.util.Objects;

/** An individual concert; immutable. */
public final class Concert implements Show {
    private final String title;
    private final String performer;
    private final int duration;

    public Concert(String title, String performer, int duration) {
        if (title == null || title.isBlank() || performer == null || performer.isBlank()) {
            throw new IllegalArgumentException("Title and performer are required");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        this.title = title;
        this.performer = performer;
        this.duration = duration;
    }

    @Override
    public String description() {
        return title + " by " + performer;
    }

    @Override
    public int runningTime() {
        return duration;
    }

    @Override
    public Show copy() {
        return new Concert(title, performer, duration);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Concert concert)) return false;
        return duration == concert.duration && title.equals(concert.title)
                && performer.equals(concert.performer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, performer, duration);
    }
}
