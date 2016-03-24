package Tests;

import Code.Contact;
import Code.ContactImpl;
import org.junit.Assert;

public class ContactImplTest {

    Contact one;
    Contact two;
    Contact three;

    @org.junit.Before
    public void setUp() throws Exception {
        one = new ContactImpl(1, "Graeme", "Member of staff");
        two = new ContactImpl(2, "Philip", "External Staff Member");
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testGetId() throws Exception {
        Assert.assertEquals(1, one.getId());
        Assert.assertEquals(2, two.getId());
    }

    @org.junit.Test
    public void testGetName() throws Exception {
        Assert.assertEquals("Graeme", one.getName());
        Assert.assertEquals("Phil", two.getName());
    }

    @org.junit.Test
    public void testGetNotes() throws Exception {
        Assert.assertEquals("Member of staff", one.getNotes());
        Assert.assertEquals("External Staff Member", two.getNotes());
    }

    @org.junit.Test
    public void testAddNotes() throws Exception {
        one.addNotes("Selected");
        Assert.assertEquals("Member of staff Selected", one.getNotes());
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void illegalID() throws Exception {
        three = new ContactImpl(-1, "Phil", "External Staff Member");
    }
    @org.junit.Test (expected = NullPointerException.class)
    public void nullName() throws Exception {
        three = new ContactImpl(3, null, "External Staff Member");
    }
    @org.junit.Test (expected = NullPointerException.class)
    public void nullnotes() throws Exception {
        three = new ContactImpl(4, "Phil", null);
    }
    @org.junit.Test (expected = IllegalArgumentException.class)
    public void blankname() throws Exception {
        three = new ContactImpl(4, "", "Notes go here");
    }
}