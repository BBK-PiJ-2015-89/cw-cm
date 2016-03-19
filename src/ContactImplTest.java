import static org.junit.Assert.assertEquals;

public class ContactImplTest {

    Contact one;
    Contact two;

    @org.junit.Before
    public void setUp() throws Exception {
        one = new ContactImpl(1, "Graeme", "Member of staff");
        two = new ContactImpl(2, "Phil", "External Staff Member");
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testGetId() throws Exception {
        assertEquals(1, one.getId());
        assertEquals(2, two.getId());
    }

    @org.junit.Test
    public void testGetName() throws Exception {
        assertEquals("Graeme", one.getName());
        assertEquals("Phil", two.getName());
    }

    @org.junit.Test
    public void testGetNotes() throws Exception {
        assertEquals("Member of staff", one.getNotes());
        assertEquals("External Staff Member", two.getNotes());
    }

    @org.junit.Test
    public void testAddNotes() throws Exception {
        one.addNotes("Selected");
        assertEquals("Member of staff Selected", one.getNotes());
    }
}