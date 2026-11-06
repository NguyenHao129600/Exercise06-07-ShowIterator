package demo;

/** Entry point that launches both exercises in one run. */
public final class DemoAll {
    private DemoAll() { }

    public static void main(String[] args) {
        Exercise06Demo.main(args);
        System.out.println();
        Exercise07Demo.main(args);
    }
}
