package Code;

import java.util.Calendar;
import java.util.Set;

public abstract class MeetingImpl implements Meeting {

    int id;
    Calendar date;
    Set<Contact> contacts;

    public MeetingImpl(int id, Calendar date, Set<Contact> contacts){
        this.contacts = contacts;
        this.date = date;
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Calendar getDate() {
        return null;
    }

    @Override
    public Set<Contact> getContacts() {
        return null;
    }
}
