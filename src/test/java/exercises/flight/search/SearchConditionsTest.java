package exercises.flight.search;
import static org.junit.Assert.*;

import org.junit.Test;

public class SearchConditionsTest {

    @Test
    public void testNonNullParameters() {
        assertNotNull(new SearchConditions("origin", "destination"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullOrigin() {
        assertNull(new SearchConditions(null, "destination"));
    }
    

    @Test(expected = IllegalArgumentException.class)
    public void testNullDestination() {
        assertNull(new SearchConditions("origin", null));
    }
}
