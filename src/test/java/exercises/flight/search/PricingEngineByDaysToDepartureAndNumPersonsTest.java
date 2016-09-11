package exercises.flight.search;
import org.junit.Test;

import exercises.flight.search.impl.*;
import exercises.flight.search.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import java.io.*;
import java.util.*;

public class PricingEngineByDaysToDepartureAndNumPersonsTest  {

    String SAMPLES_CSV_FILEPATH = "src/test/resources";
    String SAMPLES_CSV_FILENAME = "sample.csv";
    
    @Test
    public void testCalculateTicketsEdgeCaseNoPersons() {

        //        * no persons, 30 days to the departure date, flying AMS -> FRA
        //        should find flights, but with 0.0 price:
        //          * TK2372, 0.0 €
        //          * TK2659, 0.0 €
        //          * LH5909, 0.0 €
        PricingModifiers pricingModifiers = new PricingModifiers();
        pricingModifiers.daysToDeparture = 30;
        pricingModifiers.numAdults = 0;

        PricingEngine pricingEngine = new PricingEngineByDaysToDepartureAndNumPersons();

        List<FlightTicket> testTotalsTickets = pricingEngine.calculateTotals(getBaseFlightTickets("AMS", "FRA"), pricingModifiers);

        assertThat(testTotalsTickets.size(), is(3));
        assertThat(testTotalsTickets, hasItem(new FlightTicket("TK2372", "0")));
        assertThat(testTotalsTickets, hasItem(new FlightTicket("TK2659", "0")));
        assertThat(testTotalsTickets, hasItem(new FlightTicket("LH5909", "0")));
    }
    @Test
    public void testCalculateTicketsEdgeCaseNegativeDays() {
        
        // Cannot get back in time, at any price
        PricingModifiers pricingModifiers = new PricingModifiers();
        pricingModifiers.daysToDeparture = -1;
        pricingModifiers.numAdults = 1;
        
        PricingEngine pricingEngine = new PricingEngineByDaysToDepartureAndNumPersons();

        List<FlightTicket> testTotalsTickets = pricingEngine.calculateTotals(getBaseFlightTickets("AMS", "FRA"), pricingModifiers);
        assertThat(testTotalsTickets.isEmpty(), is(true));
    }
    
    @Test
    public void testCalculateTicketsFirstExample() {

        //        * 1 adult, 30 days to the departure date, flying AMS -> FRA
        //        flights:
        //          * TK2372, 157.6 €
        //          * TK2659, 198.4 €
        //          * LH5909, 90.4 €
        PricingModifiers pricingModifiers = new PricingModifiers();
        // pricingModifiers.daysToDeparture = 30;
        // TODO: Ask why examples are 30/31 divergent with rules. Deal as if rules are right and examples wrong.
        pricingModifiers.daysToDeparture = 31;
        pricingModifiers.numAdults = 1;

        PricingEngine pricingEngine = new PricingEngineByDaysToDepartureAndNumPersons();

        List<FlightTicket> testTotalsTickets = pricingEngine.calculateTotals(getBaseFlightTickets("AMS", "FRA"), pricingModifiers);
        assertThat(testTotalsTickets.size(), is(3));
        assertThat(testTotalsTickets, hasItem(new FlightTicket("TK2372", "157.60")));
        assertThat(testTotalsTickets, hasItem(new FlightTicket("TK2659", "198.40")));
        assertThat(testTotalsTickets, hasItem(new FlightTicket("LH5909", "90.40")));
    }

    @Test
    public void testCalculateTicketsSecondExample() {

        //      * 2 adults, 1 child, 1 infant, 15 days to the departure date, flying LHR -> IST
        //      flights:
        //        * TK8891, 806 € (2 * (120% of 250) + 67% of (120% of 250) + 5)
        //        * LH1085, 481.19 € (2 * (120% of 148) + 67% of (120% of 148) + 7)
        PricingModifiers pricingModifiers = new PricingModifiers();
        pricingModifiers.daysToDeparture = 15;
        pricingModifiers.numAdults = 2;
        pricingModifiers.numChildren = 1;
        pricingModifiers.numInfants = 1;
        
        PricingEngine pricingEngine = new PricingEngineByDaysToDepartureAndNumPersons();
        
        List<FlightTicket> testTotalsTickets = pricingEngine.calculateTotals(getBaseFlightTickets("LHR", "IST"), pricingModifiers);
        assertThat(testTotalsTickets.size(), is(2));
        assertThat(testTotalsTickets, hasItem(new FlightTicket("TK8891", "806.00")));
        assertThat(testTotalsTickets, hasItem(new FlightTicket("LH1085", "481.19")));
    }

    @Test
    public void testCalculateTicketsThirdExample() {

        //      * 1 adult, 2 children, 2 days to the departure date, flying BCN -> MAD
        //      flights:
        //        * IB2171, 909.09 € (150% of 259 + 2 * 67% of (150% of 259))
        //        * LH5496, 1028.43 € (150% of 293 + 2 * 67% of (150% of 293))
        PricingModifiers pricingModifiers = new PricingModifiers();
        pricingModifiers.daysToDeparture = 2;
        pricingModifiers.numAdults = 1;
        pricingModifiers.numChildren = 2;
        
        PricingEngine pricingEngine = new PricingEngineByDaysToDepartureAndNumPersons();
        
        List<FlightTicket> testTotalsTickets = pricingEngine.calculateTotals(getBaseFlightTickets("BCN", "MAD"), pricingModifiers);
        assertThat(testTotalsTickets.size(), is(2));
        assertThat(testTotalsTickets, hasItem(new FlightTicket("IB2171", "909.09")));
        assertThat(testTotalsTickets, hasItem(new FlightTicket("LH5496", "1028.43")));
    }

    @Test
    public void testCalculateTicketsFourthExample() {

        // * CDG -> FRA
        // no flights available
        PricingModifiers pricingModifiers = new PricingModifiers();

        PricingEngine pricingEngine = new PricingEngineByDaysToDepartureAndNumPersons();
        
        List<FlightTicket> testTotalsTickets = pricingEngine.calculateTotals(getBaseFlightTickets("CDG", "FRA"), pricingModifiers);
        assertThat(testTotalsTickets.isEmpty(), is(true));
    }

    private List<FlightTicket> getBaseFlightTickets(String origin, String destination) {
        
        SearchEngine searchEngine = new SearchEngineByOriginAndDestinationInCSVStream(getReader());
        List<FlightTicket> matchingFlightTickets = searchEngine.getFlightTickets(new SearchConditions(origin, destination));
        return matchingFlightTickets;
    }

    private Reader getReader() {

        try {
            return CSVReaderUtil.getFileReader(SAMPLES_CSV_FILEPATH, SAMPLES_CSV_FILENAME);
        } catch (IOException ex) {
            // For tests an empty reader will do. Production ready code should deal with exceptions
            return new StringReader("");
        }
    }
}
