package Code;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ContactManagerImplTest {

    private ContactManager contactManagerTest;
    private Calendar futureDate;
    private Calendar pastDate;
    private Set<Contact> tempContactList;

    @Before
    public void setUp() throws Exception {
        contactManagerTest = new ContactManagerImpl();
        futureDate = new GregorianCalendar(2016,5,2);
        pastDate = new GregorianCalendar(2014, 4, 20);
        contactManagerTest.addNewContact("Graeme", "Graeme is a  test");
        contactManagerTest.addNewContact("Phileme", "Graeme is a  test");
        contactManagerTest.addNewContact("eme", "Graeme is a  test");
        contactManagerTest.addNewContact("Mark", "Graeme is a  test");
        tempContactList = new HashSet<>();
        tempContactList.add(new ContactImpl(1, "Graeme", "Test Notes"));
        contactManagerTest.addFutureMeeting(tempContactList, futureDate);
        tempContactList.add(new ContactImpl(2, "Mark", "Test Notes"));
        contactManagerTest.addFutureMeeting(tempContactList, futureDate);
        contactManagerTest.addFutureMeeting(tempContactList, futureDate);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAddFutureMeeting() throws Exception {
        Set<Contact> tempContactList = new HashSet<>();
        tempContactList.add(new ContactImpl(1, "Graeme", "Test Notes"));
        int newMeetingID = contactManagerTest.addFutureMeeting(tempContactList, futureDate);
        Assert.assertTrue(newMeetingID>0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddFutureMeetingWPast() throws Exception {
        int newMeetingID = contactManagerTest.addFutureMeeting(tempContactList, pastDate);
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
        List<Meeting> meetings = contactManagerTest.getMeetingListOn(futureDate);
        Assert.assertEquals(3, meetings.size());
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

    @Test (expected = IllegalArgumentException.class)
    public void testAddNewContactWithEmptyName() throws Exception {
        contactManagerTest.addNewContact("", "Test User");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNewContactWithEmptyNotes() throws Exception {
        contactManagerTest.addNewContact("Graeme", "");
    }

    @Test (expected = NullPointerException.class)
    public void testAddNewContactWithnullNotes() throws Exception {
        contactManagerTest.addNewContact("Graeme", null);
    }

    @Test (expected = NullPointerException.class)
    public void testAddNewContactWithNullName() throws Exception {
        contactManagerTest.addNewContact(null, "Graeme is a  test");
    }

    @Test
    public void testGetContactsByString() throws Exception {
        Set<Contact> filteredSet = contactManagerTest.getContacts("eme");
        Assert.assertEquals(3, filteredSet.size());
    }

    @Test
    public void testGetContactsByEmptyString() throws Exception {
        Set<Contact> filteredSet = contactManagerTest.getContacts("");
        Assert.assertEquals(4, filteredSet.size());
    }

    @Test (expected = NullPointerException.class)
    public void testGetContactsByStringNulled() throws Exception {
        String testNull = null;
        Set<Contact> filteredSet = contactManagerTest.getContacts(testNull);
        Assert.assertEquals(3, filteredSet.size());
    }

    @Test
    public void testGetContactsByID() throws Exception {
        Set<Contact> filteredSet = contactManagerTest.getContacts(1,2,3);
        Assert.assertEquals(3, filteredSet.size());
    }

    @Test
    public void testGetContactsByOneID() throws Exception {
        Set<Contact> filteredSet = contactManagerTest.getContacts(1);
        Assert.assertEquals(1, filteredSet.size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetContactsNoID() throws Exception {
        Set<Contact> filteredSet = contactManagerTest.getContacts();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetContactsInvalidID() throws Exception {
        Set<Contact> filteredSet = contactManagerTest.getContacts(670);
    }


}