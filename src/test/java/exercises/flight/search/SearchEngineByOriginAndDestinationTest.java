package exercises.flight.search;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.*;

import exercises.flight.search.impl.*;
import exercises.flight.search.util.*;

public class SearchEngineByOriginAndDestinationTest {

    @Test
    public void testNonexistentInputFile() {

        SearchEngine engine = new SearchEngineByOriginAndDestination(getEmptyReader());
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("", ""));
        assertThat("getFlightTickets must return an empty list", matchingFlightTickets.isEmpty(), is(true));
    }

    @Test
    public void testSearchAndNotFind() {

        SearchEngine engine = new SearchEngineByOriginAndDestination(getValidReader());
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("", ""));
        assertThat("getFlightTickets must return an empty list", matchingFlightTickets.isEmpty(), is(true));
    }

    @Test
    public void testSearchAndNotFindWhenEmptyOrigin() {

        SearchEngine engine = new SearchEngineByOriginAndDestination(getValidReader());
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("", "MAD"));
        assertThat("getFlightTickets must return an empty list", matchingFlightTickets.isEmpty(), is(true));
    }

    @Test
    public void testSearchAndNotFindWhenEmptyDestination() {

        SearchEngine engine = new SearchEngineByOriginAndDestination(getValidReader());
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("CDG", ""));
        assertThat("getFlightTickets must return an empty list", matchingFlightTickets.isEmpty(), is(true));
    }

    @Test
    public void testSearchAndNotFindWhenOriginNonexistent() {

        SearchEngine engine = new SearchEngineByOriginAndDestination(getValidReader());
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("XXX", "MAD"));
        assertThat("getFlightTickets must return an empty list", matchingFlightTickets.isEmpty(), is(true));
    }

    @Test
    public void testSearchAndNotFindWhenDestinationNonexistent() {

        SearchEngine engine = new SearchEngineByOriginAndDestination(getValidReader());
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("CDG", "XXX"));
        assertThat("getFlightTickets must return an empty list", matchingFlightTickets.isEmpty(), is(true));
    }

    @Test
    public void testSearchAndFindOne() {

        SearchEngine engine = new SearchEngineByOriginAndDestination(getValidReader());
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("CDG", "MAD"));
        assertThat("getFlightTickets must return a non empty list", matchingFlightTickets.isEmpty(), is(false));
        assertThat("getFlightTickets must return a list with only one ticket", matchingFlightTickets.size(), is(1));
        assertThat("getFlightTickets must return a ticket like 'IB8482,295'", matchingFlightTickets, hasItem(new FlightTicket("IB8482", "295")));
    }

    @Test
    public void testSearchAndFindTwo() {

        SearchEngine engine = new SearchEngineByOriginAndDestination(getValidReader());
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("CPH", "FCO"));
        assertThat("getFlightTickets must return a non empty list", matchingFlightTickets.isEmpty(), is(false));
        assertThat("getFlightTickets must return a list with two tickets", matchingFlightTickets.size(), is(2));
        assertThat("getFlightTickets must return a ticket like 'TK4667,137'", matchingFlightTickets, hasItem(new FlightTicket("TK4667", "137")));
        assertThat("getFlightTickets must return a ticket like 'U24631,268'", matchingFlightTickets, hasItem(new FlightTicket("U24631", "268")));
    }

    private Reader getValidReader() {

        try {
            return CSVReaderUtil.getFileReader("src/test/resources", "sample.csv");
        } catch (IOException ex) {
            return new StringReader("");
        }
    }

    private Reader getEmptyReader() {

        try {
            return CSVReaderUtil.getFileReader("src/test/resources", "fake.csv");
        } catch (IOException ex) {
            return new StringReader("");
        }
    }
}
