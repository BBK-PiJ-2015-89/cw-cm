package Code;

import java.util.*;
import java.util.stream.Collectors;


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
        if (contacts == null || date == null){
            throw new NullPointerException();
        }
        int importContactSize = contacts.size();
        contacts.retainAll(contactList);
        if (contacts.size() < importContactSize || !(Calendar.getInstance().compareTo(date) < 0)){
            throw new IllegalArgumentException();
        }

        meetingList.add(new FutureMeetingImpl(meetingID, date, contacts));
        meetingID++;
        return meetingID - 1;
    }

    @Override
    public PastMeeting getPastMeeting(int id) {
        Optional<Meeting> foundMeeting = meetingList.stream().filter(c -> c.getId() == id).findFirst();
        if (!(foundMeeting.isPresent())) {
            return null;
        } else if (foundMeeting.get().getDate().compareTo(new GregorianCalendar()) > 0){
            throw new IllegalArgumentException();
        }
        return (PastMeeting)foundMeeting.get();
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) {
        Optional<Meeting> foundMeeting = meetingList.stream().filter(c -> c.getId() == id).findFirst();
        if (!(foundMeeting.isPresent())) {
            return null;
        } else if (foundMeeting.get().getDate().compareTo(new GregorianCalendar()) < 0){
            throw new IllegalArgumentException();
        }
        return (FutureMeeting)foundMeeting.get();
    }

    @Override
    public Meeting getMeeting(int id) {
        Optional<Meeting> foundMeeting = meetingList.stream().filter(c -> c.getId() == id).findFirst();
        if (foundMeeting.isPresent()) {
            return foundMeeting.get();
        }
        return null;
    }


    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        if (contact == null) {
            throw new NullPointerException();
        }
        Comparator<Meeting> byTime = (e1, e2) -> e1
                .getDate().getTime().compareTo(e2.getDate().getTime());

        int id = contact.getId();
        Optional<Contact> contactInList = contactList.stream().filter(a -> a.getId() == id).findFirst();
        if (!(contactInList.isPresent())) {
            throw new IllegalArgumentException("Contact not in list");
        }
        return meetingList.stream().filter(a -> (a instanceof FutureMeeting && contactInSet(id, a.getContacts()))).sorted(byTime).collect(Collectors.toList());



       /* return meetingList.stream().filter(c -> {
            for (int i = 0; i < meetingList.size(); i++) {
                Set<Contact> attendees = c.getContacts();
                attendees.stream().filter(b -> {
                    for (int j = 0; j < attendees.size(); j++) {
                        if (b.getId() == contact.getId()) {
                            matchesContact = true;
                        }
                    }return matchesContact;
                });
            }
            return matchesContact;
        }).sorted(byTime).collect(Collectors.toList());*/
    }

    public boolean contactInSet(int id, Set<Contact> contactSet) {
        return contactSet.stream().filter(a -> id == a.getId()).findFirst().isPresent();
    }

    @Override
    public List<Meeting> getMeetingListOn(Calendar date) {
        Comparator<Meeting> byTime = (e1, e2) -> e1
                .getDate().getTime().compareTo(e2.getDate().getTime());

        if (date == null) {
            throw new NullPointerException();
        }
        return meetingList.stream().filter(c -> {
            boolean onThisDay = false;
            Calendar nextDay = (Calendar) date.clone();
            nextDay.add(Calendar.DAY_OF_MONTH, 1);
            for (int i = 0; i < meetingList.size(); i++) {
                if (c.getDate().compareTo(date) >= 0 && c.getDate().compareTo(nextDay) < 0) {
                    onThisDay = true;
                }
            }
            return onThisDay;
        }).sorted(byTime).collect((Collectors.toList()));

    }

    @Override
    public List<PastMeeting> getPastMeetingListFor(Contact contact) {
        return null;
    }

    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        int importContactSize = contacts.size();
        contacts.retainAll(contactList);
        if (contacts.size() < importContactSize || !(Calendar.getInstance().compareTo(date) > 0)){
            throw new IllegalArgumentException();
    }

        meetingList.add(new PastMeetingImpl(meetingID, date, contacts, text));
        meetingID++;
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
            for (int id : ids) {
                if (id == b.getId()) {
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
