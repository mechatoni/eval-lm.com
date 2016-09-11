package exercises.flight.search.impl;

import java.math.*;
import java.util.*;
import java.util.stream.*;

import exercises.flight.search.*;

public class PricingEngineByDaysToDepartureAndNumPersons implements PricingEngine {

    private static final BigDecimal MORE_THAN_30_DAYS_FACTOR = new BigDecimal("0.8");
    private static final BigDecimal LESS_THAN_16_AND_MORE_THAN_3_DAYS_FACTOR = new BigDecimal("1.2");
    private static final BigDecimal LESS_THAN_3_DAYS_FACTOR = new BigDecimal("1.5");
    private static final BigDecimal CHILD_FACTOR = new BigDecimal("0.67");

    private Map<String, BigDecimal> airlineInfantPriceByIATA;

    public PricingEngineByDaysToDepartureAndNumPersons() {

        airlineInfantPriceByIATA = new HashMap<String, BigDecimal>();
        airlineInfantPriceByIATA.put("IB", new BigDecimal("10"));
        airlineInfantPriceByIATA.put("BA", new BigDecimal("15"));
        airlineInfantPriceByIATA.put("LH", new BigDecimal("7"));
        airlineInfantPriceByIATA.put("FR", new BigDecimal("20"));
        airlineInfantPriceByIATA.put("VY", new BigDecimal("10"));
        airlineInfantPriceByIATA.put("TK", new BigDecimal("5"));
        airlineInfantPriceByIATA.put("U2", new BigDecimal("19.90"));
    }

    @Override
    public List<FlightTicket> calculateTotals(List<FlightTicket> base, PricingModifiers pricingModifiers) {

        return base.stream()
                .map(ticket -> calculateTotal(ticket, pricingModifiers))
                .collect(Collectors.toList());
    }

    @Override
    public FlightTicket calculateTotal(FlightTicket base, PricingModifiers pricingModifiers) {

        BigDecimal unitPrice = calculatePriceFromDaysRules(pricingModifiers, base.price);
        BigDecimal adultsPrice = calculateAdultsPrice(pricingModifiers, unitPrice);
        BigDecimal childrenPrice = calculateChildrenPrice(pricingModifiers, unitPrice);
        BigDecimal infantsPrice = calculateInfantsPrice(base, pricingModifiers);
        return new FlightTicket(base.flightName, adultsPrice.add(childrenPrice).add(infantsPrice));
    }

    private BigDecimal calculatePriceFromDaysRules(PricingModifiers pricingModifiers, BigDecimal price) {
        int daysToGo = pricingModifiers.daysToDeparture;
        if (daysToGo > 30) {
            price = price.multiply(MORE_THAN_30_DAYS_FACTOR);
        } else if (daysToGo <= 15 && daysToGo >= 3) {
            price = price.multiply(LESS_THAN_16_AND_MORE_THAN_3_DAYS_FACTOR);
        } else if (daysToGo < 3) {
            price = price.multiply(LESS_THAN_3_DAYS_FACTOR);
        }
        return price;
    }

    private BigDecimal calculateAdultsPrice(PricingModifiers pricingModifiers, BigDecimal unitPrice) {
        return unitPrice.multiply(new BigDecimal(pricingModifiers.numAdults));
    }

    private BigDecimal calculateChildrenPrice(PricingModifiers pricingModifiers, BigDecimal unitPrice) {
        return unitPrice.multiply(new BigDecimal(pricingModifiers.numChildren)).multiply(CHILD_FACTOR);
    }

    private BigDecimal calculateInfantsPrice(FlightTicket base, PricingModifiers pricingModifiers) {
        return getInfantPrice(base.flightName).multiply(new BigDecimal(pricingModifiers.numInfants));
    }

    private BigDecimal getInfantPrice(String flightName) {

        BigDecimal result = airlineInfantPriceByIATA.get(flightName.substring(0, 2));
        if (result == null) {
            return BigDecimal.ZERO;
        }
        return result;
    }
}
