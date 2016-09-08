package exercises.flight.search;

/*
 * Modeled as a lightweight bean, not an interface. Security and thread-safe characteristics should be added only if necessary.
 * #CleanCode
 */
public class SearchConditions {

    public String origin;
    public String destination;

    public SearchConditions(String origin, String destination) {
        if (origin == null || destination == null) {
            throw new IllegalArgumentException("Neither origin nor destination can be null");
        }
        this.origin = origin;
        this.destination = destination;
    }
}
