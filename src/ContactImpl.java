/**
 * Created by graemewilkinson on 06/03/16.
 */
public class ContactImpl implements Contact {
    int id;
    String name;
    StringBuilder notes;

    public ContactImpl(int id, String name, String notes){
        this.id = id;
        this.name = name;
        this.notes = new StringBuilder();
        this.notes.append(notes);

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
}
