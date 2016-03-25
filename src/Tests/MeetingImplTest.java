package Tests;

import Code.Contact;
import Code.ContactImpl;
import Code.FutureMeetingImpl;
import Code.Meeting;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;


public class MeetingImplTest {

    private Meeting meeting;
    Set<Contact> contactList;
    Calendar date;

    @org.junit.Before
    public void testMeeting() {
        meeting = null;
        contactList = new HashSet<>(); //unordered set to attach to meeting
        contactList.add(new ContactImpl(1, "Graeme Wilkinson", "New friend"));
        contactList.add(new ContactImpl(2, "Mark Wilkinson", "Old friend"));
        date = new GregorianCalendar(); //sets date to today
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
}