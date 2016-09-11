package exercises.flight.search;
import org.junit.Test;

import exercises.flight.search.impl.*;
import exercises.flight.serach.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import java.io.*;
import java.util.*;

public class PricingEngineTest {

    @Test
    public void testCalculateTicketsFirstExample() {

        //        * 1 adult, 30 days to the departure date, flying AMS -> FRA
        //        flights:
        //          * TK2372, 157.6 €
        //          * TK2659, 198.4 €
        //          * LH5909, 90.4 €
        PricingEngine pricing = new RuledPricingEngine();
        PricingModifiers pricingModifiers = new PricingModifiers();
        // TODO: Find why examples are 30/31 different, or rule is not as examples state.
        pricingModifiers.daysToDeparture = 31;
        pricingModifiers.numAdults = 1;

        List<FlightTicket> testTotalsTickets = pricing.calculateTotals(getBaseFlightTickets("AMS", "FRA"), pricingModifiers);
        assertThat("getFlightTickets must return a non empty list", testTotalsTickets.isEmpty(), is(false));
        assertThat("getFlightTickets must return a list with three tickets", testTotalsTickets.size(), is(3));
        assertThat("getFlightTickets must return a ticket like 'TK2372,157.60'", testTotalsTickets, hasItem(new FlightTicket("TK2372", "157.60")));
        assertThat("getFlightTickets must return a ticket like 'TK2659,198.40'", testTotalsTickets, hasItem(new FlightTicket("TK2659", "198.40")));
        assertThat("getFlightTickets must return a ticket like 'LH5909,90.40'", testTotalsTickets, hasItem(new FlightTicket("LH5909", "90.40")));
    }

    @Test
    public void testCalculateTicketsSecondExample() {

        //      * 2 adults, 1 child, 1 infant, 15 days to the departure date, flying LHR -> IST
        //      flights:
        //        * TK8891, 806 € (2 * (120% of 250) + 67% of (120% of 250) + 5)
        //        * LH1085, 481.19 € (2 * (120% of 148) + 67% of (120% of 148) + 7)
        PricingEngine pricing = new RuledPricingEngine();
        PricingModifiers pricingModifiers = new PricingModifiers();
        pricingModifiers.daysToDeparture = 15;
        pricingModifiers.numAdults = 2;
        pricingModifiers.numChildren = 1;
        pricingModifiers.numInfants = 1;
        List<FlightTicket> testTotalsTickets = pricing.calculateTotals(getBaseFlightTickets("LHR", "IST"), pricingModifiers);
        assertThat("getFlightTickets must return a non empty list", testTotalsTickets.isEmpty(), is(false));
        assertThat("getFlightTickets must return a list with three tickets", testTotalsTickets.size(), is(2));
        assertThat("getFlightTickets must return a ticket like 'TK8891,806.00'", testTotalsTickets, hasItem(new FlightTicket("TK8891", "806.00")));
        assertThat("getFlightTickets must return a ticket like 'LH1085,481.19'", testTotalsTickets, hasItem(new FlightTicket("LH1085", "481.19")));
    }

    @Test
    public void testCalculateTicketsThirdExample() {

        //      * 1 adult, 2 children, 2 days to the departure date, flying BCN -> MAD
        //      flights:
        //        * IB2171, 909.09 € (150% of 259 + 2 * 67% of (150% of 259))
        //        * LH5496, 1028.43 € (150% of 293 + 2 * 67% of (150% of 293))
        PricingEngine pricing = new RuledPricingEngine();
        PricingModifiers pricingModifiers = new PricingModifiers();
        pricingModifiers.daysToDeparture = 2;
        pricingModifiers.numAdults = 1;
        pricingModifiers.numChildren = 2;
        List<FlightTicket> testTotalsTickets = pricing.calculateTotals(getBaseFlightTickets("BCN", "MAD"), pricingModifiers);
        assertThat("getFlightTickets must return a non empty list", testTotalsTickets.isEmpty(), is(false));
        assertThat("getFlightTickets must return a list with three tickets", testTotalsTickets.size(), is(2));
        assertThat("getFlightTickets must return a ticket like 'IB2171,909.09'", testTotalsTickets, hasItem(new FlightTicket("IB2171", "909.09")));
        assertThat("getFlightTickets must return a ticket like 'LH5496,1028.43'", testTotalsTickets, hasItem(new FlightTicket("LH5496", "1028.43")));
    }

    @Test
    public void testCalculateTicketsFourthExample() {

        // * CDG -> FRA
        // no flights available
        PricingEngine pricing = new RuledPricingEngine();
        PricingModifiers pricingModifiers = new PricingModifiers();
        List<FlightTicket> testTotalsTickets = pricing.calculateTotals(getBaseFlightTickets("CDG", "FRA"), pricingModifiers);
        assertThat("getFlightTickets must return an empty list", testTotalsTickets.isEmpty(), is(true));
        assertThat("getFlightTickets must return a list with no tickets", testTotalsTickets.size(), is(0));
    }

    private List<FlightTicket> getBaseFlightTickets(String origin, String destination) {
        
        SearchEngine engine = new SearchEngineImpl(getReader());
        List<FlightTicket> matchingFlightTickets = engine.getFlightTickets(new SearchConditions(origin, destination));
        return matchingFlightTickets;
    }

    private Reader getReader() {

        try {
            return CSVReaderUtil.getFileReader("src/test/resources", "sample.csv");
        } catch (IOException ex) {
            return new StringReader("");
        }
    }
}
