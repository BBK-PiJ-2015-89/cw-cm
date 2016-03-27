package Code;

/**
 * Created by graemewilkinson on 06/03/16.
 */
public class ContactImpl implements Contact {
    int id;
    String name;
    StringBuilder notes;

    public ContactImpl(int id, String name, String notes){
        if(id <= 0) {
            throw new IllegalArgumentException("ID must be > 0");
        } else if(notes == null) {
            throw new NullPointerException("Notes value cannot be null.");
        } else if(name == null) {
            throw new NullPointerException("The name value cannot be null");
        } else if(name.isEmpty()) {
            throw new IllegalArgumentException("You must enter a name");
        }
        this.id = id;
        this.name = name;
        this.notes = new StringBuilder();
        this.notes.append(notes);
    }

    public ContactImpl(int id, String name){
        if(id <= 0) {
            throw new IllegalArgumentException("ID must be > 0");
        } else if(name == null) {
            throw new NullPointerException("The name value cannot be null");
        } else if(name.isEmpty()) {
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
        notes.append(" " + note);
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }
        if (!(o instanceof Contact)){
            return false;
        }
        Contact c = (Contact) o;

        if (c.getId() == id && c.getName() == name){
            return true;
        } else {
            return false;
        }
    }
}
