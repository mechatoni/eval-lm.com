package exercises.flight.search;

import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Suite.*;

@RunWith(Suite.class)
@SuiteClasses({
        FlightTicketTest.class,
        SearchConditionsTest.class,
        PricingEngineByDaysToDepartureAndNumPersonsTest.class,
        SearchEngineByOriginAndDestinationTest.class
})
public class AllTests {

}
