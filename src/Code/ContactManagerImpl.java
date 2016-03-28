package Code;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class ContactManagerImpl implements ContactManager, Serializable {

    private static final String FILE = "contacts.txt";
    private Set<Contact> contactList;
    private Set<Meeting> meetingList;
    private int contactID;
    private int meetingID;
    private final static int MAX = Integer.MAX_VALUE;

    public ContactManagerImpl() {
        contactID = 1;
        meetingID = 1;
        contactList = new HashSet<>();
        meetingList = new HashSet<>();
        readFromFile();
    }

    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        if(maxMeeting()){
            throw new IllegalStateException("No more meeting spaces");
        }
        if (contacts == null || date == null) {
            throw new NullPointerException();
        }
        int importContactSize = contacts.size();
        contacts.retainAll(contactList);
        if (contacts.size() < importContactSize || !(Calendar.getInstance().compareTo(date) < 0)) {
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
        } else if (foundMeeting.get().getDate().compareTo(new GregorianCalendar()) > 0) {
            throw new IllegalArgumentException();
        }
        return (PastMeeting) foundMeeting.get();
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) {
        Optional<Meeting> foundMeeting = meetingList.stream().filter(c -> c.getId() == id).findFirst();
        if (!(foundMeeting.isPresent())) {
            return null;
        } else if (foundMeeting.get().getDate().compareTo(new GregorianCalendar()) < 0) {
            throw new IllegalArgumentException();
        }
        return (FutureMeeting) foundMeeting.get();
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
        List<PastMeeting> returnList = new ArrayList<>();
        List<Meeting> filteredList = meetingList.stream().filter(a -> (a instanceof PastMeeting && contactInSet(id, a.getContacts()))).sorted(byTime).collect(Collectors.toList());
        for (Meeting pm : filteredList) {
            returnList.add((PastMeeting) pm);
        }
        return returnList;
    }

    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        if(maxMeeting()){
            throw new IllegalStateException("No more meeting spaces");
        }
        int importContactSize = contacts.size();
        contacts.retainAll(contactList);
        if (contacts.size() < importContactSize || !(Calendar.getInstance().compareTo(date) > 0)) {
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
        if (current == null) {
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
        if(maxContact()){
            throw new IllegalStateException("Max contents reached");
        }
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
        ObjectOutputStream fileOut = null;
        try {
            fileOut = new ObjectOutputStream((new BufferedOutputStream(new FileOutputStream(FILE))));
            fileOut.writeInt(contactID);
            fileOut.writeInt(meetingID);
            fileOut.writeObject(contactList);
            fileOut.writeObject(meetingList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException f) {
            f.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException g) {
                g.printStackTrace();
            }
        }
    }

    private void readFromFile() {
        File cFile = new File("contacts.txt");
        if (cFile.exists()) {
            ObjectInputStream fileIn = null;
            int fromFileContactID = 0;
            int fromFileMeetingID = 0;
            Set<Meeting> fromFileMeetings = new HashSet<>();
            Set<Contact> fromFileContacts = new HashSet<>();
            try {
                fileIn = new ObjectInputStream(new BufferedInputStream((new FileInputStream(FILE))));

                fromFileContactID = fileIn.readInt();
                fromFileMeetingID = fileIn.readInt();
                fromFileContacts = (Set<Contact>) fileIn.readObject();
                fromFileMeetings = (Set<Meeting>) fileIn.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (FileNotFoundException f) {
                f.printStackTrace();
            } catch (IOException g) {
                g.printStackTrace();
            }
            try {
                if (fileIn != null) {
                    fileIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (fromFileContactID != 0) {
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

    private boolean maxContact() {
        if (contactID == MAX) {
            return true;
        }
        return false;
    }

    private boolean maxMeeting() {
        if (meetingID == MAX) {
            return true;
        }
        return false;
    }
}