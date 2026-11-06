package chapter6.exercise7;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class Exercise07Test {
    private final Show movie1 = new Movie("A", 2023, 100);
    private final Show movie2 = new Movie("B", 2024, 95);
    private final Show concert = new Concert("Live", "Band", 60);

    @Test
    void showInterfaceNowExtendsIterable() {
        assertTrue(Iterable.class.isAssignableFrom(Show.class));
    }

    @Test
    void atomicShowHasNoComponents() {
        assertFalse(movie1.iterator().hasNext());
        assertFalse(concert.iterator().hasNext());
    }

    @Test
    void compositeCanBeUnpackedFromShowReference() {
        Show program = new CompositeShow(concert, new CompositeShow(movie1, movie2));
        List<Show> immediate = new ArrayList<>();
        for (Show item : program) immediate.add(item);
        assertEquals(2, immediate.size());
        assertSame(concert, immediate.get(0));
        assertTrue(immediate.get(1) instanceof CompositeShow);
    }

    @Test
    void decoratorDelegatesIteratorToWrappedComposite() {
        Show program = new IntroducedShow(new CompositeShow(concert, movie1), "Host", 10);
        var iterator = program.iterator();
        assertSame(concert, iterator.next());
        assertSame(movie1, iterator.next());
        assertFalse(iterator.hasNext());
        assertEquals(170, program.runningTime());
    }

    @Test
    void introducedAtomicShowStillHasNoChildren() {
        Show introduced = new IntroducedShow(movie1, "Host", 10);
        assertFalse(introduced.iterator().hasNext());
        assertEquals(110, introduced.runningTime());
    }

    @Test
    void compositeRejectsOutsideRangeAndNull() {
        assertThrows(IllegalArgumentException.class, () -> new CompositeShow(movie1));
        assertThrows(IllegalArgumentException.class,
                () -> new CompositeShow(movie1, movie1, movie1, movie1, movie1, movie1));
        assertThrows(NullPointerException.class, () -> new CompositeShow(movie1, null));
    }

    @Test
    void compositeDeepCopyPreservesStructureWithoutSharingObjects() {
        Show root = new IntroducedShow(new CompositeShow(movie1, movie2), "Host", 10);
        Show copy = root.copy();
        assertEquals(root, copy);
        assertNotSame(root, copy);
        assertNotSame(root.iterator().next(), copy.iterator().next());
    }
}
