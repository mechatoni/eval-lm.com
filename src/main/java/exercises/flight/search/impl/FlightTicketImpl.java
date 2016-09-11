package exercises.flight.search.impl;

import java.math.*;

import exercises.flight.search.*;

public class FlightTicketImpl implements FlightTicket {

    String flightName;
    BigDecimal price;

    public FlightTicketImpl(String flightName, BigDecimal price) {
        this.flightName = flightName;
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }

    public FlightTicketImpl(String flightName, String price) throws NumberFormatException {
        this(flightName, new BigDecimal(price));
    }

    @Override
    public String getFlightName() {

        return flightName;
    }

    @Override
    public BigDecimal getPrice() {

        return price;
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
