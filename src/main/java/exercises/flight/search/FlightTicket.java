package exercises.flight.search;

import java.math.*;

public class FlightTicket {

    public String flightName;
    public BigDecimal price;

    public FlightTicket(String flightName, BigDecimal price) {
        this.flightName = flightName;
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }

    public FlightTicket(String flightName, String price) throws NumberFormatException {
        this(flightName, new BigDecimal(price));
    }

    // Just for testing
    @Override
    public boolean equals(Object another) {

        return another instanceof FlightTicket && another != null && another.toString().equals(toString());
    }

    @Override
    public String toString() {

        return flightName + ":" + price;
    }
}
