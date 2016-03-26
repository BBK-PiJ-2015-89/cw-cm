package Code;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by graemewilkinson on 25/03/16.
 */
public class ContactManagerImpl implements ContactManager {
    private Set<Contact> contactList;
    private Set<Meeting> meetingList;
    private int contactID = 1;
    private int meetingID = 1;

    public ContactManagerImpl() {
        contactList = new HashSet<>();
        meetingList = new HashSet<>();
    }

    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        if (!(Calendar.getInstance().compareTo(date) < 0)) {
            throw new IllegalArgumentException();
        }
        meetingList.add(new FutureMeetingImpl(meetingID, date, contacts));
        meetingID++;
        return meetingID - 1;
    }

    @Override
    public PastMeeting getPastMeeting(int id) {
        return null;
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) {
        return null;
    }

    @Override
    public Meeting getMeeting(int id) {
        return null;
    }

    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        return null;
    }

    @Override
    public List<Meeting> getMeetingListOn(Calendar date) {
        if (date == null) {
            throw new NullPointerException();
        }
        List<Meeting> filtered = meetingList.stream().filter(c -> {
            boolean onThisDay = false;
            for (int i = 0; i < meetingList.size(); i++) {
                if (c.getDate() == date) {
                    onThisDay = true;
                }
            }
            return onThisDay;
        }).collect((Collectors.toList()));
        return filtered;
    }

    @Override
    public List<PastMeeting> getPastMeetingListFor(Contact contact) {
        return null;
    }

    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {

    }

    @Override
    public PastMeeting addMeetingNotes(int id, String text) {
        return null;
    }

    @Override
    public int addNewContact(String name, String notes) {
        if (notes.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            int TempID = contactID;
            contactID++;
            contactList.add(new ContactImpl(TempID, name, notes));
            return TempID;
        }
    }

    @Override
    public Set<Contact> getContacts(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        return contactList.stream().filter((Contact a) -> a.getName().matches(".*" + name + ".*")).collect(Collectors.toSet());
    }

    @Override
    public Set<Contact> getContacts(int... ids) {
        if (ids == null) {
            throw new NullPointerException();
        } else if (ids.length == 0) {
            throw new IllegalArgumentException("No ID's provided");
        }
        Set<Contact> filteredSet = contactList.stream().filter((Contact b) -> {
            boolean contactValid = false;
            for (int i = 0; i < ids.length; i++) {
                if (ids[i] == b.getId()) {
                    contactValid = true;
                }

                //else {
                //   throw new IllegalArgumentException("One or more ID's do not exist in the database");
                //}
            }
            return contactValid;
        }).collect(Collectors.toSet());
        if (filteredSet.size() < ids.length) {
            throw new IllegalArgumentException("One or more ID's do not exist in the database");
        }
        return filteredSet;
    }


    @Override
    public void flush() {

    }
}
