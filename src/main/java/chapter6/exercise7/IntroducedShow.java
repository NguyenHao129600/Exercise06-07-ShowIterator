package chapter6.exercise7;

import java.util.Objects;

/** Decorator Pattern: adds an introduction without changing the wrapped show. */
public final class IntroducedShow implements Show {
    private final Show wrapped;
    private final String speaker;
    private final int introductionMinutes;

    public IntroducedShow(Show wrapped, String speaker, int introductionMinutes) {
        this.wrapped = Objects.requireNonNull(wrapped, "wrapped show");
        if (speaker == null || speaker.isBlank()) {
            throw new IllegalArgumentException("Speaker is required");
        }
        if (introductionMinutes <= 0) {
            throw new IllegalArgumentException("Introduction must have positive duration");
        }
        this.speaker = speaker;
        this.introductionMinutes = introductionMinutes;
    }

    /** Read-only access to the wrapped show, useful for inspecting the design. */
    public Show wrappedShow() {
        return wrapped;
    }

    @Override
    public String description() {
        return "Intro by " + speaker + " (" + introductionMinutes + " min): "
                + wrapped.description();
    }

    @Override
    public int runningTime() {
        return introductionMinutes + wrapped.runningTime();
    }

    @Override
    public Show copy() {
        return new IntroducedShow(wrapped.copy(), speaker, introductionMinutes);
    }

    /** A decorated composite stays iterable through the shared Show interface. */
    @Override
    public java.util.Iterator<Show> iterator() {
        return wrapped.iterator();
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof IntroducedShow introduced)) return false;
        return introductionMinutes == introduced.introductionMinutes
                && speaker.equals(introduced.speaker)
                && wrapped.equals(introduced.wrapped);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wrapped, speaker, introductionMinutes);
    }
}
