package exercises.flight.search;
import java.util.*;

public interface PricingEngine {

    FlightTicket calculateTotal(FlightTicket base, PricingModifiers pricingModifiers);

    List<FlightTicket> calculateTotals(List<FlightTicket> base, PricingModifiers pricingModifiers);
}
