package Tests;

import Code.Contact;
import Code.ContactImpl;
import Code.Meeting;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;


public class MeetingImplTest {

    private Meeting meeting;
    Set<Contact> contactList;
    Calendar date;

    @org.junit.Before
    public void TestMeeting() {
        meeting = null;
        contactList = new HashSet<Contact>();
        contactList.add(new ContactImpl(1, "Graeme Wilkinson"));
        date = new GregorianCalendar();
    }

    @Test
    public void testGetId(){

    }

    @Test
    public void testGetDate() throws Exception {

    }

    @Test
    public void testGetContacts() throws Exception {

    }
}