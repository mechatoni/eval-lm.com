package exercises.flight.search.impl;

import java.util.*;
import java.util.stream.*;

import exercises.flight.search.*;

public class RuledPricingEngine implements PricingEngine {

    private static final Float MORE_THAN_30_DAYS_FACTOR = new Float("0.8");
    private static final Float LESS_THAN_16_AND_MORE_THAN_3_DAYS_FACTOR = new Float("1.2");
    private static final Float LESS_THAN_3_DAYS_FACTOR = new Float("1.5");
    private static final Float CHILD_FACTOR = new Float("0.67");

    private Map<String, Float> airlineInfantPriceByIATA;

    public RuledPricingEngine() {

        airlineInfantPriceByIATA = new HashMap<String, Float>();
        airlineInfantPriceByIATA.put("IB", new Float("10"));
        airlineInfantPriceByIATA.put("BA", new Float("15"));
        airlineInfantPriceByIATA.put("LH", new Float("7"));
        airlineInfantPriceByIATA.put("FR", new Float("20"));
        airlineInfantPriceByIATA.put("VY", new Float("10"));
        airlineInfantPriceByIATA.put("TK", new Float("5"));
        airlineInfantPriceByIATA.put("U2", new Float("19.90"));
    }

    @Override
    public FlightTicket calculateTotal(FlightTicket base, PricingModifiers pricingModifiers) {

        Float unitPrice = calculatePriceFromDaysRules(pricingModifiers, base.getPrice());
        Float adultsPrice = unitPrice * pricingModifiers.numAdults;
        Float childrenPrice = unitPrice * pricingModifiers.numChildren * CHILD_FACTOR;
        Float infantsPrice = getInfantPrice(base.getFlightName()) * pricingModifiers.numInfants;
        return new FlightTicketImpl(base.getFlightName(), adultsPrice + childrenPrice + infantsPrice);
    }

    @Override
    public List<FlightTicket> calculateTotals(List<FlightTicket> base, PricingModifiers pricingModifiers) {

        return base.stream()
                .map(ticket -> calculateTotal(ticket, pricingModifiers))
                .collect(Collectors.toList());
    }

    private Float getInfantPrice(String flightName) {

        Float result = airlineInfantPriceByIATA.get(flightName.substring(0, 2));
        if (result == null) {
            return 0f;
        }
        return result;
    }

    private Float calculatePriceFromDaysRules(PricingModifiers pricingModifiers, Float price) {
        int daysToGo = pricingModifiers.daysToDeparture;
        if (daysToGo > 30) {
            price = price * MORE_THAN_30_DAYS_FACTOR;
        } else if (daysToGo <= 15 && daysToGo >= 3) {
            price = price * LESS_THAN_16_AND_MORE_THAN_3_DAYS_FACTOR;
        } else if (daysToGo < 3) {
            price = price * LESS_THAN_3_DAYS_FACTOR;
        }
        return price;
    }
}
