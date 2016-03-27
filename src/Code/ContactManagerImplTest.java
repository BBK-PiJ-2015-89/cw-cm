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
    private Set<Contact> tempInvalidContactList;

    @Before
    public void setUp() throws Exception {
        contactManagerTest = new ContactManagerImpl();
        futureDate = new GregorianCalendar(2016,5,2,12,15);
        pastDate = new GregorianCalendar(2014, 4, 20);
        contactManagerTest.addNewContact("Graeme", "Graeme is a  test");
        contactManagerTest.addNewContact("Phil1", "Phil is a  test");
        contactManagerTest.addNewContact("eme", "Graeme is a  test");
        contactManagerTest.addNewContact("Mark", "Graeme is a  test");
        tempInvalidContactList = new HashSet<>();
        //tempInvalidContactList.add(new ContactImpl(1, "Graeme", "Test Notes"));
        contactManagerTest.addFutureMeeting(contactManagerTest.getContacts(1), new GregorianCalendar(2016,5,2,12,15));//id1
        tempInvalidContactList.add(new ContactImpl(8, "Phil1", "Graeme is a test"));
        contactManagerTest.addFutureMeeting(contactManagerTest.getContacts(1, 2), new GregorianCalendar(2016, 5, 2, 12, 10));//id2
        contactManagerTest.addFutureMeeting(contactManagerTest.getContacts(1), new GregorianCalendar(2016,5,2,12,30));//id3
        contactManagerTest.addNewPastMeeting(contactManagerTest.getContacts(1), new GregorianCalendar(2015,2,12), "This meeting was good");//id4

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAddFutureMeeting() throws Exception {
        Set<Contact> tempContactList = new HashSet<>();
        tempContactList.add(new ContactImpl(1, "Graeme", "Test Notes"));
        int newMeetingID = contactManagerTest.addFutureMeeting(contactManagerTest.getContacts(1), new GregorianCalendar(2016,6,2,12,15));
        Assert.assertTrue(newMeetingID > 0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddFutureMeetingWPast() throws Exception {
        int newMeetingID = contactManagerTest.addFutureMeeting(contactManagerTest.getContacts(1), pastDate);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddFutureMeetingWPastInvalidContact() throws Exception {
        int newMeetingID = contactManagerTest.addFutureMeeting(tempInvalidContactList, new GregorianCalendar(2016,6,2,12,15));
    }


    @Test
    public void testGetPastMeeting() throws Exception {
        Meeting old = contactManagerTest.getPastMeeting(4);
        Assert.assertEquals(4, old.getId());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetPastMeetingFuture() throws Exception {
        Meeting old = contactManagerTest.getPastMeeting(2);
    }

    @Test
    public void testGetFutureMeeting() throws Exception {
        Meeting future = contactManagerTest.getFutureMeeting(2);
        Assert.assertEquals(2, future.getId());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetFutureMeetingInvalid() throws Exception {
        Meeting future = contactManagerTest.getFutureMeeting(4);
    }

    @Test
    public void testGetMeeting() throws Exception {
        Meeting fT1 = contactManagerTest.getMeeting(2);
        Assert.assertEquals(2, fT1.getId());
    }

    @Test
    public void testGetFutureMeetingList() throws Exception {
        Contact temp = contactManagerTest.getContacts(1).stream().findFirst().get();
        List<Meeting> results = contactManagerTest.getFutureMeetingList(temp);
        Assert.assertEquals(3, results.size());

    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetFutureMeetingListInvalid() throws Exception {
        Contact temp = contactManagerTest.getContacts(20).stream().findFirst().get();
        List<Meeting> results = contactManagerTest.getFutureMeetingList(temp);
    }

    @Test
    public void testGetMeetingListOn() throws Exception {
        List<Meeting> meetings = contactManagerTest.getMeetingListOn(new GregorianCalendar(2016, 5, 2));
        for(int i = 0; i<meetings.size(); i++){
            Meeting temp = meetings.get(i);
            System.out.println(temp.getDate().get(Calendar.HOUR_OF_DAY) + ":" + temp.getDate().get(Calendar.MINUTE));
        }
        Assert.assertEquals(3, meetings.size());
    }

    @Test (expected = NullPointerException.class)
    public void testGetMeetingListOnNullDate() throws Exception {
        List<Meeting> meetings = contactManagerTest.getMeetingListOn(null);

    }

    @Test
    public void testGetPastMeetingListFor() throws Exception {

    }

    @Test
    public void testAddNewPastMeeting() throws Exception {
        contactManagerTest.addNewPastMeeting(contactManagerTest.getContacts(1), new GregorianCalendar(2014,5,2,12,30), "This is a test meeting in the past");
        List<Meeting> tempList = contactManagerTest.getMeetingListOn(new GregorianCalendar(2014,5,2));
        Assert.assertEquals(1, tempList.size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingFuture() throws Exception {
        contactManagerTest.addNewPastMeeting(contactManagerTest.getContacts(1), new GregorianCalendar(2016, 5, 2, 12, 30), "This is a test meeting in the past");
    }

    @Test
    public void testAddNewPastMeetingwithValidContact(){
        Set<Contact> tempValidContactList = new HashSet<>();
        tempValidContactList.add(new ContactImpl(2, "Phil1", "Phil is a test"));
        contactManagerTest.addNewPastMeeting(tempValidContactList, new GregorianCalendar(2014, 2, 4), "This is another test meeting");
        List<Meeting> listReturned = contactManagerTest.getMeetingListOn(new GregorianCalendar(2014, 2, 4));
        Assert.assertEquals(1, listReturned.size());


    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingInvalidContact() throws Exception {
        contactManagerTest.addNewPastMeeting(tempInvalidContactList, new GregorianCalendar(2014, 5, 2, 12, 30), "This is a test meeting in the past");
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
    public void testAddNewContactWithNullNotes() throws Exception {
        contactManagerTest.addNewContact("Graeme", null);
    }

    @Test (expected = NullPointerException.class)
    public void testAddNewContactWithNullName() throws Exception {
        contactManagerTest.addNewContact(null, "Graeme is a  test");
    }

    @Test
    public void testGetContactsByString() throws Exception {
        Set<Contact> filteredSet = contactManagerTest.getContacts("eme");
        Assert.assertEquals(2, filteredSet.size());
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