package exercises.flight.search;
import java.io.*;
import java.util.*;

public interface SearchEngine {

    List<FlightTicket> getFlightTickets(SearchConditions searchConditions);
}