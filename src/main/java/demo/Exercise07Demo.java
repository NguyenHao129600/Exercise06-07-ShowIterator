package demo;

    import chapter6.exercise7.CompositeShow;
    import chapter6.exercise7.Concert;
    import chapter6.exercise7.IntroducedShow;
    import chapter6.exercise7.Movie;
    import chapter6.exercise7.Show;

    public final class Exercise07Demo {
        private Exercise07Demo() { }

        public static void main(String[] args) {
            System.out.println("========== EXERCISE 7 ==========");

            Show concert = new Concert("Autumn Live", "The Band", 60);
            Show firstMovie = new Movie("The First Movie", 2023, 90);
            Show secondMovie = new IntroducedShow(
                    new Movie("The Second Movie", 2024, 105), "Speaker B", 5);
            Show movies = new CompositeShow(firstMovie, secondMovie);
            Show program = new CompositeShow(concert, movies);
            Show introducedProgram = new IntroducedShow(program, "Speaker A", 10);

            System.out.println("Program: " + introducedProgram.description());
            System.out.println("Total running time: " + introducedProgram.runningTime() + " min");
            System.out.println("\nComponents accessed through a Show variable:");
            for (Show child : introducedProgram) {
                System.out.println(" - " + child.description());
            }
            System.out.println("\nUnpack nested components (depth-first):");
            unpack(introducedProgram, 0);
            System.out.println("Show interface modified? YES (extends Iterable<Show>)");
        }

        private static void unpack(Show show, int depth) {
            boolean hasChildren = false;
            for (Show child : show) {
                hasChildren = true;
                unpack(child, depth + 1);
            }
            if (!hasChildren && show instanceof IntroducedShow decorated) {
                // A decorated atomic show iterates over no children; print the decorator.
                System.out.println("  ".repeat(depth) + "- " + decorated.description());
            } else if (!hasChildren && !(show instanceof CompositeShow)) {
                System.out.println("  ".repeat(depth) + "- " + show.description());
            }
        }
    }
