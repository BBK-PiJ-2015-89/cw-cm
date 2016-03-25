package Code;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class ContactManagerImplTest {

    private ContactManager contactManagerTest;
    private Calendar futureDate;
    private Calendar pastDate;

    @Before
    public void setUp() throws Exception {
        contactManagerTest = new ContactManagerImpl();
        futureDate = new GregorianCalendar();
        pastDate = new GregorianCalendar(2014, 04, 20);
        contactManagerTest.addNewContact("Graeme", "Graeme is a  test");
        contactManagerTest.addNewContact("Phileme", "Graeme is a  test");
        contactManagerTest.addNewContact("eme", "Graeme is a  test");
        contactManagerTest.addNewContact("Mark", "Graeme is a  test");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAddFutureMeeting() throws Exception {
        Set<Contact> tempContactList = new HashSet<>();
        tempContactList.add(new ContactImpl(1, "Graeme", "Test Notes"));
        Assert.assertTrue(contactManagerTest.addFutureMeeting(tempContactList, futureDate) > 0);
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