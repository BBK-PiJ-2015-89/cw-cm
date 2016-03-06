import static org.junit.Assert.*;


/**
 * Created by graemewilkinson on 06/03/16.
 */
public class ContactImplTest {
    @org.junit.Test
    public void testGetID() throws Exception {
        ContactImpl c = new ContactImpl();
        int output = c.getID();
        int expected = 10;
        assertEquals(output, expected);
    }
}
