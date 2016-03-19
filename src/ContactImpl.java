/**
 * Created by graemewilkinson on 06/03/16.
 */
public class ContactImpl implements Contact {
    int id;
    String name;
    String notes;

    public ContactImpl(int id, String name, String notes){
        id = this.id;
        name = this.name;
        notes = this.notes;
    }

    @Override
    public int getId() {
        return 0;
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
