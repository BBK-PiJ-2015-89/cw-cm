import com.sun.tools.javac.util.Assert;

import static org.junit.Assert.*;


/**
 * Created by graemewilkinson on 06/03/16.
 */
public class ContactTest {
    @org.junit.Test
    public void testGetID() throws Exception {
        Contact c = new Contact();
        int output = c.getID();
        int expected = 10;
        assertEquals(output, expected);
    }
}
