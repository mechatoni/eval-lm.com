package exercises.flight.search;

import java.util.*;

public interface SearchEngine {

    List<FlightTicket> getFlightTickets(SearchConditions searchConditions);
}