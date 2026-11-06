package chapter6.exercise6;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class Exercise06Test {
    private final Show movie1 = new Movie("A", 2023, 100);
    private final Show movie2 = new Movie("B", 2024, 95);
    private final Show concert = new Concert("Live", "Band", 60);

    @Test
    void originalShowInterfaceIsNotModified() {
        assertFalse(Iterable.class.isAssignableFrom(Show.class));
        Set<String> methods = new HashSet<>();
        for (var method : Show.class.getDeclaredMethods()) methods.add(method.getName());
        assertEquals(Set.of("description", "runningTime", "copy"), methods);
    }

    @Test
    void iteratorGivesDirectComponentsInOrder() {
        CompositeShow movies = new CompositeShow(movie1, movie2);
        CompositeShow root = new CompositeShow(concert, movies);
        var iterator = root.iterator();
        assertSame(concert, iterator.next());
        assertSame(movies, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void nestedCompositesCanBeUnpacked() {
        CompositeShow movies = new CompositeShow(movie1, movie2);
        CompositeShow root = new CompositeShow(concert, movies);
        var iterator = root.iterator();
        assertSame(concert, iterator.next());
        var moviesIterator = ((CompositeShow) iterator.next()).iterator();
        assertSame(movie1, moviesIterator.next());
        assertSame(movie2, moviesIterator.next());
        assertFalse(moviesIterator.hasNext());
    }

    @Test
    void acceptsTwoThroughFiveAndRejectsOtherCounts() {
        assertThrows(IllegalArgumentException.class, () -> new CompositeShow(movie1));
        assertThrows(IllegalArgumentException.class,
                () -> new CompositeShow(movie1, movie1, movie1, movie1, movie1, movie1));
        assertDoesNotThrow(() -> new CompositeShow(movie1, movie1));
        assertDoesNotThrow(() -> new CompositeShow(movie1, movie1, movie1, movie1, movie1));
        assertThrows(NullPointerException.class, () -> new CompositeShow(movie1, null));
    }

    @Test
    void iteratorCannotMutateComposite() {
        var iterator = new CompositeShow(movie1, movie2).iterator();
        iterator.next();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    @Test
    void decoratorAddsRunningTime() {
        Show decorated = new IntroducedShow(movie2, "Speaker", 10);
        assertEquals(105, decorated.runningTime());
        assertTrue(decorated.description().contains("Speaker"));
    }

    @Test
    void compositeDeepCopyDoesNotReuseChildren() {
        CompositeShow original = new CompositeShow(movie1, movie2);
        CompositeShow copy = (CompositeShow) original.copy();
        assertNotSame(original, copy);
        assertEquals(original, copy);
        assertNotSame(original.iterator().next(), copy.iterator().next());
    }

    @Test
    void runningTimeIncludesIntroductionsAndNestedShows() {
        CompositeShow movies = new CompositeShow(movie1,
                new IntroducedShow(movie2, "Second speaker", 5));
        Show root = new IntroducedShow(new CompositeShow(concert, movies), "First speaker", 10);
        assertEquals(270, root.runningTime());
    }
}
