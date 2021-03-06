package Code;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author BBK-PiJ-2015-89
 */
public class ContactImpl implements Contact, Serializable {
    private int id;
    private String name;
    private StringBuilder notes;

    public ContactImpl(int id, String name, String notes) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be > 0");
        } else if (notes == null) {
            throw new NullPointerException("Notes value cannot be null.");
        } else if (name == null) {
            throw new NullPointerException("The name value cannot be null");
        } else if (name.isEmpty()) {
            throw new IllegalArgumentException("You must enter a name");
        }
        this.id = id;
        this.name = name;
        this.notes = new StringBuilder();
        this.notes.append(notes);
    }

    public ContactImpl(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be > 0");
        } else if (name == null) {
            throw new NullPointerException("The name value cannot be null");
        } else if (name.isEmpty()) {
            throw new IllegalArgumentException("You must enter a name");
        }

        this.id = id;
        this.name = name;
        this.notes = new StringBuilder();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNotes() {
        return notes.toString();
    }

    @Override
    public void addNotes(String note) {
        notes.append(" ").append(note);
    }


    /**
     * Overriden equals to ensure that the parameter's of the object are checked, rather than the object address.
     * I am happy that the contacts are equal if both the ID and Name match.
     *
     * @param o the object to be checked for equality
     * @return True if object is equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }
        Contact c = (Contact) o;
        return c.getId() == id && Objects.equals(c.getName(), name);
    }

    /**
     * Overridden hashCode to ensure that codes are allocated based on the ID and Name of the contact.
     * This ensures that any java library methods detect equality based on the parameter's, not the object
     * address.
     *
     * @return and hashCode in the form of an int for the object created.
     */
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + id; //using id and name to create hash ensures that equal parameters in the contact Object get the same hashCode.
        hash = 31 * hash + name.hashCode();
        return hash;
    }

    /**
     * Create to enable printing of sets of contacts in tests for debugging.
     *
     * @return String of contacts id, name and notes.
     */
    @Override
    public String toString() {
        return id + ", " + name + ", " + "notes: " + notes;
    }
}
