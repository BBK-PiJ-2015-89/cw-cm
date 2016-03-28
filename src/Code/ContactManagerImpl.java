package Code;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by BBK-PiJ-2015-89
 */

public class ContactManagerImpl implements ContactManager, Serializable {

    private static final String FILE = "contacts.txt";
    private Set<Contact> contactList;
    private Set<Meeting> meetingList;
    private int contactID; // used to assign ID's to contacts, kept here so I can assign ID's before passing the contact through the constructor of Contact.
    private int meetingID; // used to assign ID's to meetings, kept here so I can assign ID's before passing the contact through the constructor of Meeting.
    private final static int MAX = Integer.MAX_VALUE;

    public ContactManagerImpl() {
        contactID = 1; //counter initialised
        meetingID = 1; //counter initialised
        contactList = new HashSet<>();
        meetingList = new HashSet<>();
        readFromFile(); //counters need to set at correct level so will be read from file, if available. Contact and Meeting lists too.
    }

    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        if (maxMeeting()) {
            throw new IllegalStateException("No more meeting spaces");
        }
        if (contacts == null || date == null) {
            throw new NullPointerException();
        }
        int importContactSize = contacts.size();
        contacts.retainAll(contactList); //manipulate the imported list and leave only those contacts in the list that match both name and ID.
        if (contacts.size() < importContactSize || !(Calendar.getInstance().compareTo(date) < 0)) {
            throw new IllegalArgumentException();
        }

        meetingList.add(new FutureMeetingImpl(meetingID, date, contacts));
        meetingID++;
        return meetingID - 1;
    }

    @Override
    public PastMeeting getPastMeeting(int id) {
        Optional<Meeting> foundMeeting = meetingList.stream().filter(c -> c.getId() == id).findFirst(); //check if the meeting is in the set.
        if (!(foundMeeting.isPresent())) {
            return null; //if meeting is not in set return null
        } else if (foundMeeting.get().getDate().compareTo(new GregorianCalendar()) > 0) { // check if meeting is after today, if so, not likely to have taken place so throw exception.
            throw new IllegalArgumentException();
        }
        return (PastMeeting) foundMeeting.get();
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) {
        Optional<Meeting> foundMeeting = meetingList.stream().filter(c -> c.getId() == id).findFirst();
        if (!(foundMeeting.isPresent())) {
            return null;
        } else if (foundMeeting.get().getDate().compareTo(new GregorianCalendar()) < 0) { // if meeting happened before today, then throw exception as it is in the past.
            throw new IllegalArgumentException();
        }
        return (FutureMeeting) foundMeeting.get();
    }

    @Override
    public Meeting getMeeting(int id) {
        Optional<Meeting> foundMeeting = meetingList.stream().filter(c -> c.getId() == id).findFirst(); //filter for meeting
        if (foundMeeting.isPresent()) {
            return foundMeeting.get(); //if we found the meeting return it.
        }
        return null; // otherwise return null.
    }

    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        if (contact == null) {
            throw new NullPointerException();
        }
        Comparator<Meeting> byTime = (e1, e2) -> e1
                .getDate().getTime().compareTo(e2.getDate().getTime());

        int id = contact.getId();
        String name = contact.getName();
        Optional<Contact> contactInList = contactList.stream().filter(a -> a.getId() == id && Objects.equals(a.getName(), name)).findFirst();//check is passed in contact is valid by name and ID
        if (!(contactInList.isPresent())) {
            throw new IllegalArgumentException("Contact not in list");
        }
        return meetingList.stream().filter(a -> (a instanceof FutureMeeting && contactInList.isPresent())).sorted(byTime).collect(Collectors.toList());
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
                if (c.getDate().compareTo(date) >= 0 && c.getDate().compareTo(nextDay) < 0) { //filter meetings from the day specified and ensuring they are before the next day.
                    onThisDay = true;
                }
            }
            return onThisDay;
        }).sorted(byTime).collect((Collectors.toList()));

    }

    @Override
    public List<PastMeeting> getPastMeetingListFor(Contact contact) {
        if (contact == null) {
            throw new NullPointerException();
        }
        Comparator<Meeting> byTime = (e1, e2) -> e1
                .getDate().getTime().compareTo(e2.getDate().getTime());

        int id = contact.getId();
        Optional<Contact> contactInList = contactList.stream().filter(a -> a.getId() == id).findFirst(); // check if the contact is in the contactList
        if (!(contactInList.isPresent())) {
            throw new IllegalArgumentException("Contact not in list");
        }
        List<PastMeeting> returnList = new ArrayList<>();
        List<Meeting> filteredList = meetingList.stream().filter(a -> (a instanceof PastMeeting && contactInList.isPresent())).sorted(byTime).collect(Collectors.toList());
        returnList.addAll(filteredList.stream().map(pm -> (PastMeeting) pm).collect(Collectors.toList()));
        return returnList;
    }

    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        if (maxMeeting()) {
            throw new IllegalStateException("No more meeting spaces");
        }
        int importContactSize = contacts.size();
        contacts.retainAll(contactList); //manipulate the imported list and leave only those contacts in the list that match both name and ID.
        if (contacts.size() < importContactSize || !(Calendar.getInstance().compareTo(date) > 0) || contacts.size() == 0) {
            throw new IllegalArgumentException();
        }

        meetingList.add(new PastMeetingImpl(meetingID, date, contacts, text));
        meetingID++;
    }

    @Override
    public PastMeeting addMeetingNotes(int id, String text) {
        if (text == null) {
            throw new NullPointerException();
        }
        Meeting current = getMeeting(id);
        if (current == null) { //if meeting does not exsist throw exception.
            throw new IllegalArgumentException();
        }
        if (current.getDate().compareTo(new GregorianCalendar()) > 0) {
            throw new IllegalStateException();
        }
        String notes = "";
        if (current instanceof PastMeeting) {
            notes = ((PastMeeting) current).getNotes();
            notes += " ";
        }
        Calendar date = current.getDate();
        Set<Contact> contacts = current.getContacts();
        notes += text;
        meetingList.remove(current);
        meetingList.add(new PastMeetingImpl(id, date, contacts, notes));
        return getPastMeeting(id);
    }

    @Override
    public int addNewContact(String name, String notes) {
        if (maxContact()) {
            throw new IllegalStateException("Max contents reached");
        }
        if (notes.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            int TempID = contactID;
            contactID++;
            contactList.add(new ContactImpl(TempID, name, notes)); //if name is null here a NPE will be thrown by Contact constructor.
            return TempID;
        }
    }

    @Override
    public Set<Contact> getContacts(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        return contactList.stream().filter((Contact a) -> a.getName().matches(".*" + name + ".*")).collect(Collectors.toSet()); //filter set by names that contain the string passed in.
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
                if (id == b.getId()) { //iterate through the set of contacts matching ID's and filtering those contacts to a new list.
                    contactValid = true;
                }
            }
            return contactValid;
        }).collect(Collectors.toSet());
        if (filteredSet.size() < ids.length) { //check size of list, if it is not same size, then must be some ID's missing.
            throw new IllegalArgumentException("One or more ID's do not exist in the database");
        }
        return filteredSet;
    }


    @Override
    public void flush() {
        ObjectOutputStream fileOut = null;
        try { //write 4 main objects/ints to external file
            fileOut = new ObjectOutputStream((new BufferedOutputStream(new FileOutputStream(FILE))));
            fileOut.writeInt(contactID);
            fileOut.writeInt(meetingID);
            fileOut.writeObject(contactList);
            fileOut.writeObject(meetingList);

        } catch (IOException f) {
            f.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();//ensure we close the stream.
                }
            } catch (IOException g) {
                g.printStackTrace();
            }
        }
    }

    /**
     * Reads the ID counters, contact and meeting retrieves them from file back to the Contact Manager.
     */
    private void readFromFile() {
        File cFile = new File("contacts.txt");
        if (cFile.exists()) {
            ObjectInputStream fileIn = null;
            int fromFileContactID = 0;
            int fromFileMeetingID = 0;
            Set<Meeting> fromFileMeetings = new HashSet<>();
            Set<Contact> fromFileContacts = new HashSet<>();
            try {
                fileIn = new ObjectInputStream(new BufferedInputStream((new FileInputStream(FILE)))); //open file and start input stream, objects will come back, FIFO.

                fromFileContactID = fileIn.readInt();
                fromFileMeetingID = fileIn.readInt();
                fromFileContacts = (Set<Contact>) fileIn.readObject();
                fromFileMeetings = (Set<Meeting>) fileIn.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException g) { //removing this catch causes problems, even though software says it's same as above.
                g.printStackTrace();
            }
            try {
                if (fileIn != null) {
                    fileIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (fromFileContactID != 0) { //this set of if statements to check if the file has actually been saved in the first place, if not, don't write over.
                contactID = fromFileContactID;
            }
            if (fromFileMeetingID != 0) {
                meetingID = fromFileMeetingID;
            }
            if (fromFileContacts != null) {
                contactList = fromFileContacts;
            }
            if (fromFileMeetings != null) {
                meetingList = fromFileMeetings;
            }
        }
    }

    /**
     * Checks whether the max id for a contact has been reached
     *
     * @return true or false based on the above.
     */
    private boolean maxContact() {
        return contactID == MAX;
    } //heap space will run out first no doubt.

    /**
     * Checks whether the max id for a meeting has been reached
     *
     * @return true or false based on the above.
     */
    private boolean maxMeeting() {
        return meetingID == MAX;
    }
}