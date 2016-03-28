package Tests;


import Code.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;
/**
 * @author BBK-PiJ-2015-89
 */
public class ContactManagerImplTest {

    private ContactManager contactManagerTest;
    private Set<Contact> tempInvalidContactList;

    @Before
    public void setUp() throws Exception {
        String path = "contacts.txt";
        File cFile = new File(path); //check if file exists
        if (cFile.exists()) {
            cFile.delete();
        }// not catching exception here as if statement protects the method from throwing an exception.
        contactManagerTest = new ContactManagerImpl();
        contactManagerTest.addNewContact("Graeme", "Graeme is a  test"); //id1
        contactManagerTest.addNewContact("Phil1", "Phil is a  test"); //id2
        contactManagerTest.addNewContact("eme", "Graeme is a  test"); //id3
        contactManagerTest.addNewContact("Mark", "Graeme is a  test"); //id 4
        tempInvalidContactList = new HashSet<>();
        //tempInvalidContactList.add(new ContactImpl(1, "Graeme", "Test Notes"));
        contactManagerTest.addFutureMeeting(contactManagerTest.getContacts(1), new GregorianCalendar(2016, 5, 2, 12, 15));//id1
        tempInvalidContactList.add(new ContactImpl(8, "Phil1", "Graeme is a test"));
        contactManagerTest.addFutureMeeting(contactManagerTest.getContacts(1, 2), new GregorianCalendar(2016, 5, 2, 12, 10));//id2
        contactManagerTest.addFutureMeeting(contactManagerTest.getContacts(1), new GregorianCalendar(2016, 5, 2, 12, 30));//id3
        contactManagerTest.addNewPastMeeting(contactManagerTest.getContacts(1, 4), new GregorianCalendar(2015, 2, 12), "This meeting was good");//id4
        contactManagerTest.addNewPastMeeting(contactManagerTest.getContacts(1, 4), new GregorianCalendar(2013, 2, 12), "This meeting was good");//id5

    }

    @Test
    public void testAddFutureMeeting() throws Exception {
        int newMeetingID = contactManagerTest.addFutureMeeting(contactManagerTest.getContacts(1), new GregorianCalendar(2016, 6, 2, 12, 15));
        Assert.assertTrue(newMeetingID > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFutureMeetingWPast() throws Exception {
        contactManagerTest.addFutureMeeting(contactManagerTest.getContacts(1), new GregorianCalendar(2013, 6, 2, 12, 15));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFutureMeetingWPastInvalidContact() throws Exception {
        contactManagerTest.addFutureMeeting(tempInvalidContactList, new GregorianCalendar(2016, 6, 2, 12, 15));
    }

    @Test
    public void testGetPastMeeting() throws Exception {
        Meeting old = contactManagerTest.getPastMeeting(4);
        Assert.assertEquals(4, old.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPastMeetingFuture() throws Exception {
        contactManagerTest.getPastMeeting(2);
    }

    @Test
    public void testGetFutureMeeting() throws Exception {
        Meeting future = contactManagerTest.getFutureMeeting(2);
        Assert.assertEquals(2, future.getId());
    }

    @Test
    public void testGetFutureMeetingBig() throws Exception {
        Meeting future = contactManagerTest.getFutureMeeting(25000);
        Assert.assertEquals(null, future);
    }

    @Test
    public void testGetPastMeetingBig() throws Exception {
        Meeting past = contactManagerTest.getPastMeeting(25000);
        Assert.assertEquals(null, past);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetFutureMeetingInvalid() throws Exception {
        contactManagerTest.getFutureMeeting(4);
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

    @Test(expected = IllegalArgumentException.class)
    public void testGetFutureMeetingListInvalid() throws Exception {
        Contact temp = contactManagerTest.getContacts(20).stream().findFirst().get();
        contactManagerTest.getFutureMeetingList(temp);
    }

    @Test
    public void testGetMeetingListOn() throws Exception {
        List<Meeting> meetings = contactManagerTest.getMeetingListOn(new GregorianCalendar(2016, 5, 2));
        for (Meeting temp : meetings) {
            System.out.println(temp.getDate().get(Calendar.HOUR_OF_DAY) + ":" + temp.getDate().get(Calendar.MINUTE));
        }
        Assert.assertEquals(3, meetings.size());
    }

    @Test(expected = NullPointerException.class)
    public void testGetMeetingListOnNullDate() throws Exception {
        contactManagerTest.getMeetingListOn(null);

    }

    @Test
    public void testGetPastMeetingListFor() throws Exception {
        Contact temp = contactManagerTest.getContacts(4).stream().findFirst().get();
        List<PastMeeting> results = contactManagerTest.getPastMeetingListFor(temp);
        Assert.assertEquals(2, results.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPastMeetingListForInvalidContact() throws Exception {
        Contact temp = contactManagerTest.getContacts(700).stream().findFirst().get();
        contactManagerTest.getPastMeetingListFor(temp);
    }

    @Test(expected = NullPointerException.class)
    public void testGetPastMeetingListForNullContact() throws Exception {
        contactManagerTest.getPastMeetingListFor(null);
    }

    @Test
    public void testAddNewPastMeeting() throws Exception {
        contactManagerTest.addNewPastMeeting(contactManagerTest.getContacts(1), new GregorianCalendar(2014, 5, 2, 12, 30), "This is a test meeting in the past");
        List<Meeting> tempList = contactManagerTest.getMeetingListOn(new GregorianCalendar(2014, 5, 2));
        Assert.assertEquals(1, tempList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingFuture() throws Exception {
        contactManagerTest.addNewPastMeeting(contactManagerTest.getContacts(1), new GregorianCalendar(2016, 5, 2, 12, 30), "This is a test meeting in the past");
    }

    @Test
    public void testAddNewPastMeetingWithValidContact() {
        Set<Contact> tempValidContactList = new HashSet<>();
        tempValidContactList.add(new ContactImpl(2, "Phil1", "Phil is a test"));
        contactManagerTest.addNewPastMeeting(tempValidContactList, new GregorianCalendar(2014, 2, 4), "This is another test meeting");
        List<Meeting> listReturned = contactManagerTest.getMeetingListOn(new GregorianCalendar(2014, 2, 4));
        Assert.assertEquals(1, listReturned.size());


    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingInvalidContact() throws Exception {
        contactManagerTest.addNewPastMeeting(tempInvalidContactList, new GregorianCalendar(2014, 5, 2, 12, 30), "This is a test meeting in the past");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingEmptyContactSet() throws Exception {
        Set<Contact> emptySet = new HashSet<>();
        contactManagerTest.addNewPastMeeting(emptySet, new GregorianCalendar(2014, 5, 2, 12, 30), "This is a test meeting in the past");
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewPastMeetingNullContactSet() throws Exception {
        Set<Contact> emptySet;
        emptySet = null;
        contactManagerTest.addNewPastMeeting(emptySet, new GregorianCalendar(2014, 5, 2, 12, 30), "This is a test meeting in the past");
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewPastMeetingCalendarNull() throws Exception {
        Set<Contact> tempValidContactList = new HashSet<>();
        tempValidContactList.add(new ContactImpl(2, "Phil1", "Phil is a test"));
        contactManagerTest.addNewPastMeeting(tempValidContactList, null, "This is a test meeting in the past");
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewPastMeetingNotesNull() throws Exception {
        Set<Contact> tempValidContactList = new HashSet<>();
        tempValidContactList.add(new ContactImpl(2, "Phil1", "Phil is a test"));
        contactManagerTest.addNewPastMeeting(tempValidContactList, new GregorianCalendar(2014, 5, 2, 12, 30), null);
    }

    @Test
    public void testAddMeetingNotes() throws Exception {
        PastMeeting returnedPastMeeting = contactManagerTest.addMeetingNotes(4, "Graeme said he would do action point 7 for tests");
        Assert.assertEquals("This meeting was good Graeme said he would do action point 7 for tests", returnedPastMeeting.getNotes());
    }

    @Test(expected = NullPointerException.class)
    public void testAddMeetingNotesNullString() throws Exception {
        contactManagerTest.addMeetingNotes(4, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddMeetingNotesNoMeeting() throws Exception {
        contactManagerTest.addMeetingNotes(70000, "Graeme said he would do action point 7 for tests");
    }

    @Test(expected = IllegalStateException.class)
    public void testAddMeetingNotesFuture() throws Exception {
        contactManagerTest.addMeetingNotes(2, "Graeme said he would do action point 7 for tests");
    }


    @Test
    public void testAddNewContactReturnPositiveInt() throws Exception {
        Assert.assertTrue(contactManagerTest.addNewContact("Graeme", "Test User") > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewContactWithEmptyName() throws Exception {
        contactManagerTest.addNewContact("", "Test User");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewContactWithEmptyNotes() throws Exception {
        contactManagerTest.addNewContact("Graeme", "");
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewContactWithNullNotes() throws Exception {
        contactManagerTest.addNewContact("Graeme", null);
    }

    @Test(expected = NullPointerException.class)
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

    @Test(expected = NullPointerException.class)
    public void testGetContactsByStringNulled() throws Exception {
        String testNull = null;
        Set<Contact> filteredSet = contactManagerTest.getContacts(testNull);
        Assert.assertEquals(3, filteredSet.size());
    }

    @Test
    public void testGetContactsByID() throws Exception {
        Set<Contact> filteredSet = contactManagerTest.getContacts(1, 2, 3);
        Assert.assertEquals(3, filteredSet.size());
    }

    @Test
    public void testGetContactsByOneID() throws Exception {
        Set<Contact> filteredSet = contactManagerTest.getContacts(1);
        Assert.assertEquals(1, filteredSet.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetContactsNoID() throws Exception {
        contactManagerTest.getContacts();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetContactsInvalidID() throws Exception {
        contactManagerTest.getContacts(670);
    }

    @Test
    public void testMeetingsAfterFlush() {
        List<Meeting> currentMeetings = contactManagerTest.getMeetingListOn(new GregorianCalendar(2016, 5, 2));
        int size = currentMeetings.size();
        contactManagerTest.flush();
        contactManagerTest = new ContactManagerImpl();
        List<Meeting> currentMeetingsNew = contactManagerTest.getMeetingListOn(new GregorianCalendar(2016, 5, 2));
        Assert.assertEquals(size, currentMeetingsNew.size());
    }

    @Test
    public void testMeetingsAfterFlushWithOutFlush() {
        contactManagerTest = new ContactManagerImpl();
        List<Meeting> currentMeetingsNew = contactManagerTest.getMeetingListOn(new GregorianCalendar(2016, 5, 2));
        Assert.assertEquals(0, currentMeetingsNew.size());
    }

    @Test
    public void testAddingContactAfterFlushIDCorrect() {
        contactManagerTest.flush();
        contactManagerTest = new ContactManagerImpl();
        contactManagerTest.addNewContact("John", "Very Sick");
        Set<Contact> returnedContact = contactManagerTest.getContacts(1, 2, 3, 4, 5);
        System.out.println(returnedContact.toString());
    }
}