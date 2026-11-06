package chapter6.exercise6;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/** Composite Pattern: exactly 2 to 5 direct components, in order. */
public final class CompositeShow implements Show, Iterable<Show> {
    private final List<Show> shows;

    public CompositeShow(Show... shows) {
        Objects.requireNonNull(shows, "shows");
        if (shows.length < 2 || shows.length > 5) {
            throw new IllegalArgumentException("A composite must contain 2 to 5 shows");
        }
        // List.copyOf also rejects null elements and prevents client-side mutation.
        this.shows = List.copyOf(Arrays.asList(shows.clone()));
    }

    @Override
    public String description() {
        return shows.stream().map(Show::description)
                .collect(Collectors.joining(" -> ", "Composite[", "]"));
    }

    @Override
    public int runningTime() {
        return shows.stream().mapToInt(Show::runningTime).sum();
    }

    @Override
    public Show copy() {
        Show[] copiedChildren = shows.stream().map(Show::copy).toArray(Show[]::new);
        return new CompositeShow(copiedChildren);
    }

    /** Iterator returns DIRECT children; nested composites can be unpacked recursively. */
    @Override
    public Iterator<Show> iterator() {
        return shows.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof CompositeShow composite)) return false;
        return shows.equals(composite.shows);
    }

    @Override
    public int hashCode() {
        return shows.hashCode();
    }
}
