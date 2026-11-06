package chapter6.exercise7;

import java.util.Collections;
import java.util.Iterator;

/** Exercise 7: all shows expose a common way to iterate their direct components. */
public interface Show extends Iterable<Show> {
    String description();
    int runningTime();
    Show copy();

    /** An atomic show has no children. CompositeShow overrides this method. */
    @Override
    default Iterator<Show> iterator() {
        return Collections.emptyIterator();
    }
}
