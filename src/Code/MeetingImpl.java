package Code;

import java.util.Calendar;
import java.util.Set;

public abstract class MeetingImpl implements Meeting {

    int id;
    Calendar date;
    Set<Contact> contacts;

    /**
     *
     * @param id
     * @param date
     * @param contacts
     */

    public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
        this.contacts = contacts;
        this.date = date;
        if (id <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.id = id;
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Calendar getDate() {
        return date;
    }

    @Override
    public Set<Contact> getContacts() {
        return contacts;
    }
}
