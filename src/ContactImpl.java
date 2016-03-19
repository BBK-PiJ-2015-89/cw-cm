/**
 * Created by graemewilkinson on 06/03/16.
 */
public class ContactImpl implements Contact {
    int id;
    String name;
    String notes;

    public ContactImpl(int id, String name, String notes){
        this.id = id;
        this.name = name;
        this.notes = notes;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getNotes() {
        return null;
    }

    @Override
    public void addNotes(String note) {

    }
}
