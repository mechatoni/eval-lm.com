package exercises.flight.search.impl;

import exercises.flight.search.*;

public class FlightTicketImpl implements FlightTicket {

    String flightName;
    Float price;

    public FlightTicketImpl(String flightName, Float price) {
        this.flightName = flightName;
        this.price = price;
    }

    public FlightTicketImpl(String flightName, String price) throws NumberFormatException {
        this(flightName, Float.parseFloat(price));
    }

    @Override
    public String getFlightName() {

        return flightName;
    }

    @Override
    public Float getPrice() {

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
