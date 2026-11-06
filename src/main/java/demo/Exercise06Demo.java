package demo;

    import chapter6.exercise6.CompositeShow;
    import chapter6.exercise6.Concert;
    import chapter6.exercise6.IntroducedShow;
    import chapter6.exercise6.Movie;
    import chapter6.exercise6.Show;
    import java.util.Iterator;

    public final class Exercise06Demo {
        private Exercise06Demo() { }

        public static void main(String[] args) {
            System.out.println("========== EXERCISE 6 ==========");

            Show concert = new Concert("Autumn Live", "The Band", 60);
            Show firstMovie = new Movie("The First Movie", 2023, 90);
            Show secondMovie = new IntroducedShow(
                    new Movie("The Second Movie", 2024, 105), "Speaker B", 5);
            CompositeShow movies = new CompositeShow(firstMovie, secondMovie);
            CompositeShow program = new CompositeShow(concert, movies);
            Show introducedProgram = new IntroducedShow(program, "Speaker A", 10);

            System.out.println("Program: " + introducedProgram.description());
            System.out.println("Total running time: " + introducedProgram.runningTime() + " min");
            System.out.println("\nDirect components of outer composite:");
            Iterator<Show> iterator = program.iterator();
            int position = 1;
            while (iterator.hasNext()) {
                Show child = iterator.next();
                System.out.println(" " + position++ + ". " + child.description());
            }
            System.out.println("\nUnpack nested components (depth-first):");
            unpack(program, 0);
            System.out.println("Show interface modified? NO");
        }

        private static void unpack(Show show, int depth) {
            if (show instanceof CompositeShow composite) {
                for (Show child : composite) {
                    unpack(child, depth + 1);
                }
            } else {
                System.out.println("  ".repeat(depth) + "- " + show.description());
            }
        }
    }
