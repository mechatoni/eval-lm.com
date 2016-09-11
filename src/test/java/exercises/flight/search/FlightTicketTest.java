package exercises.flight.search;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.*;

import org.junit.*;

public class FlightTicketTest {

    @Test(expected = IllegalArgumentException.class)
    public void testFlightTicketStringString() {
        assertThat(new FlightTicket("AnyName", "NotANumber"), is(not(equalTo(new FlightTicket("AnyName", BigDecimal.ZERO)))));
    }

    @Test
    public void testEqualsString() {
        assertThat(new FlightTicket("AnyName", "0"), is(equalTo(new FlightTicket("AnyName", BigDecimal.ZERO))));
    }

    @Test
    public void testEqualsObject() {
        assertThat(new FlightTicket("AnyName", "1.000"), is(equalTo(new FlightTicket("AnyName", BigDecimal.ONE))));
    }

    @Test
    public void testToStringWithDecimals() {
        assertThat(new FlightTicket("AnyName", "0").toString(), is(equalTo("AnyName:0.00")));
    }

    @Test
    public void testEquals() {
        assertTrue(new FlightTicket("AnyName", "1.000").equals(new FlightTicket("AnyName", BigDecimal.ONE)));
    }
}
