package Code;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by graemewilkinson on 25/03/16.
 */
public class ContactManagerImplTest {

    private ContactManager contactManagerTest;
    private Calendar futureDate;
    private Calendar pastDate;

    @Before
    public void setUp() throws Exception {
        contactManagerTest = new ContactManagerImpl();
        futureDate = new GregorianCalendar();
        pastDate = new GregorianCalendar(2014, 04, 20);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAddFutureMeeting() throws Exception {

    }

    @Test
    public void testGetPastMeeting() throws Exception {

    }

    @Test
    public void testGetFutureMeeting() throws Exception {

    }

    @Test
    public void testGetMeeting() throws Exception {

    }

    @Test
    public void testGetFutureMeetingList() throws Exception {

    }

    @Test
    public void testGetMeetingListOn() throws Exception {

    }

    @Test
    public void testGetPastMeetingListFor() throws Exception {

    }

    @Test
    public void testAddNewPastMeeting() throws Exception {

    }

    @Test
    public void testAddMeetingNotes() throws Exception {

    }

    @Test
    public void testAddNewContactReturnPositiveInt() throws Exception {
        Assert.assertTrue(contactManagerTest.addNewContact("Graeme", "Test User") > 0);
    }

    @Test
    public void testGetContacts() throws Exception {

    }

    @Test
    public void testGetContacts1() throws Exception {

    }
}