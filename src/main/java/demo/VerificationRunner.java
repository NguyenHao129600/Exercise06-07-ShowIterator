package demo;

    import java.lang.reflect.Method;
    import java.util.List;

    /** A dependency-free verification suite. Run this even without Maven/JUnit. */
    public final class VerificationRunner {
        private static int passed = 0;
        private static int failed = 0;

        private VerificationRunner() { }

        public static void main(String[] args) {
            verify("Ex6: Show remains the original three-method interface", () -> {
                Class<?> showType = chapter6.exercise6.Show.class;
                check(!Iterable.class.isAssignableFrom(showType));
                check(showType.getDeclaredMethods().length == 3);
                check(List.of(showType.getDeclaredMethods()).stream()
                        .map(Method::getName)
                        .collect(java.util.stream.Collectors.toSet())
                        .equals(java.util.Set.of("description", "runningTime", "copy")));
            });
            verify("Ex6: nested iterator retains direct-child order", () -> {
                chapter6.exercise6.Show a = new chapter6.exercise6.Movie("A", 2020, 90);
                chapter6.exercise6.Show b = new chapter6.exercise6.Movie("B", 2021, 95);
                chapter6.exercise6.Show c = new chapter6.exercise6.Concert("C", "Band", 40);
                chapter6.exercise6.CompositeShow nested = new chapter6.exercise6.CompositeShow(a, b);
                chapter6.exercise6.CompositeShow root = new chapter6.exercise6.CompositeShow(c, nested);
                var it = root.iterator();
                check(it.hasNext() && it.next() == c);
                check(it.hasNext() && it.next() == nested);
                check(!it.hasNext());
                check(root.runningTime() == 225);
            });
            verify("Ex6: 2-5 child constraint", () -> {
                chapter6.exercise6.Show m = new chapter6.exercise6.Movie("A", 2020, 90);
                expectIllegal(() -> new chapter6.exercise6.CompositeShow(m));
                expectIllegal(() -> new chapter6.exercise6.CompositeShow(m, m, m, m, m, m));
                check(new chapter6.exercise6.CompositeShow(m, m, m, m, m).runningTime() == 450);
            });
            verify("Ex6: immutable iterator and independent copy", () -> {
                chapter6.exercise6.Show m = new chapter6.exercise6.Movie("A", 2020, 90);
                chapter6.exercise6.CompositeShow root = new chapter6.exercise6.CompositeShow(m, m);
                var it = root.iterator();
                it.next();
                try {
                    it.remove();
                    throw new AssertionError("remove() must be unsupported");
                } catch (UnsupportedOperationException expected) { }
                chapter6.exercise6.CompositeShow duplicate =
                        (chapter6.exercise6.CompositeShow) root.copy();
                check(duplicate != root && duplicate.equals(root));
                check(duplicate.iterator().next() != root.iterator().next());
            });
            verify("Ex7: Show interface is Iterable<Show>", () ->
                    check(Iterable.class.isAssignableFrom(chapter6.exercise7.Show.class)));
            verify("Ex7: leaf is empty; composite yields components", () -> {
                chapter6.exercise7.Show m1 = new chapter6.exercise7.Movie("A", 2020, 80);
                chapter6.exercise7.Show m2 = new chapter6.exercise7.Movie("B", 2021, 100);
                check(!m1.iterator().hasNext());
                chapter6.exercise7.Show composite = new chapter6.exercise7.CompositeShow(m1, m2);
                var it = composite.iterator();
                check(it.next() == m1 && it.next() == m2 && !it.hasNext());
            });
            verify("Ex7: decorator preserves iterable behavior", () -> {
                chapter6.exercise7.Show first = new chapter6.exercise7.Movie("A", 2020, 80);
                chapter6.exercise7.Show second = new chapter6.exercise7.Movie("B", 2021, 100);
                chapter6.exercise7.Show decorated = new chapter6.exercise7.IntroducedShow(
                        new chapter6.exercise7.CompositeShow(first, second), "Speaker", 10);
                check(decorated.runningTime() == 190);
                var it = decorated.iterator();
                check(it.next() == first && it.next() == second && !it.hasNext());
                check(decorated.copy() != decorated);
            });
            System.out.println("\nRESULT: " + passed + " PASSED, " + failed + " FAILED");
            if (failed != 0) System.exit(1);
        }

        private static void check(boolean condition) {
            if (!condition) throw new AssertionError("Unexpected result");
        }

        private static void expectIllegal(Runnable action) {
            try {
                action.run();
                throw new AssertionError("Expected IllegalArgumentException");
            } catch (IllegalArgumentException expected) { }
        }

        private static void verify(String name, Runnable test) {
            try {
                test.run();
                passed++;
                System.out.println("[PASS] " + name);
            } catch (Throwable ex) {
                failed++;
                System.out.println("[FAIL] " + name + " : " + ex);
            }
        }
    }
