package exercises.flight.search.impl;

public class SearchConditionsImpl {

    String origin;
    String destination;

    public SearchConditionsImpl(String origin, String destination) {
        if (origin == null || destination == null)
            throw new IllegalArgumentException("Neither origin nor destination can be null");
        this.origin = origin;
        this.destination = destination;
    }
}
