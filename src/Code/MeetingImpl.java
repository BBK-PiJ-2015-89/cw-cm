package Code;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * @author BBK-PiJ-2015-89
 */
public abstract class MeetingImpl implements Meeting, Serializable {

    private int id;
    private Calendar date;
    private Set<Contact> contacts;


    MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
        if (contacts.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.contacts = contacts;
        }
        if (date == null) {
            throw new NullPointerException("No date");
        } else {
            this.date = date;
        }
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

    /**
     * Create to enable printing of sets of contacts in tests for debugging.
     *
     * @return String containing id, and date of meeting.
     */
    @Override
    public String toString() {
        return id + ", " + date;
    }
}
