package exercises.flight.search;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import exercises.flight.search.*;
import exercises.flight.search.impl.*;

public class SearchEngineTest {

    // The next code may go to another test class (test from sample file)
    @Test
    public void testSearchAndNotFind() {

        SearchEngine engine = new SearchEngineImpl();
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditionsImpl("", ""));
        assertThat("getFlightTickets must return an empty list", matchingFlightTickets.isEmpty(), is(true));
    }
}
