package Tests;

import Code.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;


public class MeetingImplTest {

    private Meeting meeting;
    private PastMeeting pastMeeting;
    private Set<Contact> contactList;
    private Calendar date;
    private Calendar pastDate;
    private String notes;

    @org.junit.Before
    public void testMeeting() {
        meeting = null;
        pastMeeting = null;
        contactList = new HashSet<>(); //unordered set to attach to meeting
        contactList.add(new ContactImpl(1, "Graeme Wilkinson", "New friend"));
        contactList.add(new ContactImpl(2, "Mark Wilkinson", "Old friend"));
        date = new GregorianCalendar(); //sets date to today
        pastDate = new GregorianCalendar(2014, 7, 14);
        notes = "This is a test note";

    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetIdNeg(){
        meeting = new FutureMeetingImpl(-2, date, contactList);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetContactsEmpty(){
        Set<Contact> emptyList = new HashSet<>();
        meeting = new FutureMeetingImpl(102, date, emptyList);
    }

    @Test (expected = NullPointerException.class)
    public void testGetContactsContainsNull(){
        Set<Contact> emptyList;
        emptyList = null;
        meeting = new FutureMeetingImpl(102, date, emptyList);
    }

    @Test (expected = NullPointerException.class)
    public void testGetDateContainsNull(){
        meeting = new FutureMeetingImpl(102, null, contactList);
    }

    @Test
    public void testGetId(){
        meeting = new FutureMeetingImpl(27, date, contactList);
        Assert.assertEquals(27, meeting.getId());
    }

    @Test
    public void testGetDate(){
        meeting = new FutureMeetingImpl(168, date, contactList);
        assertTrue(date == meeting.getDate());
    }

    @Test
    public void testGetContacts(){
        meeting = new FutureMeetingImpl(274, date, contactList);
        assertTrue(contactList == meeting.getContacts());
    }

    @Test
    public void testPastMeetingGetId(){
        pastMeeting = new PastMeetingImpl(100, pastDate, contactList, notes);
        Assert.assertEquals(100, pastMeeting.getId());
    }

    @Test
    public void testPastMeetingGetDate(){
        pastMeeting = new PastMeetingImpl(100, pastDate, contactList, notes);
        assertTrue(pastDate == pastMeeting.getDate());
    }

    @Test
    public void testPastMeetingGetContacts(){
        pastMeeting = new PastMeetingImpl(100, pastDate, contactList, notes);
        assertTrue(contactList == pastMeeting.getContacts());
    }

    @Test
    public void testPastMeetingGetNotes(){
        pastMeeting = new PastMeetingImpl(100, pastDate, contactList, notes);
        assertTrue(notes.equals(pastMeeting.getNotes()));
    }

    @Test
    public void testPastMeetingAddEmptyNotes(){
        String emptyNotes = "";
        pastMeeting = new PastMeetingImpl(100, pastDate, contactList, emptyNotes);
        assertTrue(emptyNotes.equals(pastMeeting.getNotes()));
    }

    @Test (expected = NullPointerException.class)
    public void testPastMeetingAddNullNotes(){
        String emptyNotes = null;
        pastMeeting = new PastMeetingImpl(100, pastDate, contactList, emptyNotes);
    }



}