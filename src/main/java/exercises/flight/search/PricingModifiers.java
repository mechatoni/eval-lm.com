package exercises.flight.search;

public class PricingModifiers {

    public PricingModifiers() {
        
    }
    
    public PricingModifiers(int daysToDeparture, int numAdults, int numChildren, int numInfants) {
        this.daysToDeparture = daysToDeparture;
        this.numAdults = numAdults;
        this.numChildren = numChildren;
        this.numInfants = numInfants;
    }

    public int daysToDeparture;
    public int numAdults;
    public int numChildren;
    public int numInfants;
}
