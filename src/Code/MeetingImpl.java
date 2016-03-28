package Code;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

public abstract class MeetingImpl implements Meeting, Serializable {

    int id;
    Calendar date;
    Set<Contact> contacts;

    /**
     *Creates new meeting with a unique ID, a date and a set of contacts assigned to the meeting.
     * You cannot pass an empty set of contacts, a null date, or 0 or negative int.
     *
     * @param id
     * @param date
     * @param contacts
     */

    public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
        if(contacts.isEmpty()) {
            throw new IllegalArgumentException();
        }else{
            this.contacts = contacts;
        }
        if(date == null){
            throw new NullPointerException("No date");
        }else {
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

    @Override
    public String toString() {
        return id + ", " + date + ", ";
    }
}
