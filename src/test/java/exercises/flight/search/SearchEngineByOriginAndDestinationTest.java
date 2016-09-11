package exercises.flight.search;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.*;

import exercises.flight.search.impl.*;
import exercises.flight.search.util.*;

public class SearchEngineByOriginAndDestinationTest {

    String SAMPLES_CSV_FILEPATH = "src/test/resources";
    String SAMPLES_CSV_FILENAME = "sample.csv";
    String SAMPLES_CSV_FILENAME_OFFAKE = "fake.csv";
    
    @Test
    public void testNonexistentInputFile() {

        SearchEngine engine = new SearchEngineByOriginAndDestinationInCSVStream(getEmptyReader());
        
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("", ""));
        assertThat(matchingFlightTickets.isEmpty(), is(true));
    }

    @Test
    public void testSearchAndNotFind() {

        SearchEngine engine = new SearchEngineByOriginAndDestinationInCSVStream(getValidReader());
        
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("", ""));
        assertThat(matchingFlightTickets.isEmpty(), is(true));
    }

    @Test
    public void testSearchAndNotFindWhenEmptyOrigin() {

        SearchEngine engine = new SearchEngineByOriginAndDestinationInCSVStream(getValidReader());
        
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("", "MAD"));
        assertThat(matchingFlightTickets.isEmpty(), is(true));
    }

    @Test
    public void testSearchAndNotFindWhenEmptyDestination() {

        SearchEngine engine = new SearchEngineByOriginAndDestinationInCSVStream(getValidReader());
        
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("CDG", ""));
        assertThat(matchingFlightTickets.isEmpty(), is(true));
    }

    @Test
    public void testSearchAndNotFindWhenOriginNonexistent() {

        SearchEngine engine = new SearchEngineByOriginAndDestinationInCSVStream(getValidReader());
        
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("XXX", "MAD"));
        assertThat(matchingFlightTickets.isEmpty(), is(true));
    }

    @Test
    public void testSearchAndNotFindWhenDestinationNonexistent() {

        SearchEngine engine = new SearchEngineByOriginAndDestinationInCSVStream(getValidReader());
        
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("CDG", "XXX"));
        assertThat(matchingFlightTickets.isEmpty(), is(true));
    }

    @Test
    public void testSearchAndFindOne() {

        SearchEngine engine = new SearchEngineByOriginAndDestinationInCSVStream(getValidReader());
        
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("CDG", "MAD"));
        assertThat(matchingFlightTickets.size(), is(1));
        assertThat(matchingFlightTickets, hasItem(new FlightTicket("IB8482", "295")));
    }

    @Test
    public void testSearchAndFindTwo() {

        SearchEngine engine = new SearchEngineByOriginAndDestinationInCSVStream(getValidReader());
        
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions("CPH", "FCO"));
        assertThat(matchingFlightTickets.size(), is(2));
        assertThat(matchingFlightTickets, hasItem(new FlightTicket("TK4667", "137")));
        assertThat(matchingFlightTickets, hasItem(new FlightTicket("U24631", "268")));
    }

    private Reader getValidReader() {

        try {
            return CSVReaderUtil.getFileReader(SAMPLES_CSV_FILEPATH, SAMPLES_CSV_FILENAME);
        } catch (IOException ex) {
            return new StringReader("");
        }
    }

    private Reader getEmptyReader() {

        try {
            return CSVReaderUtil.getFileReader(SAMPLES_CSV_FILEPATH, SAMPLES_CSV_FILENAME_OFFAKE);
        } catch (IOException ex) {
            return new StringReader("");
        }
    }
}
