package Code;

import java.util.Calendar;
import java.util.Set;

/**
 * Created by BBK-PiJ-2015-89
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

    private String notes;

    /**
     * Past meeting constructor, if id, date or set of contacts is null an exception will be thrown
     * if notes are empty, then an exception will be thrown.
     * @param id unique id - more than 0 (int)
     * @param date a date in the past
     * @param contacts a set of contacts
     */
    public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
        super(id, date, contacts);
        if(notes == null){
            throw new NullPointerException();
        }else{
            this.notes = notes;
        }
    }

    @Override
    public String getNotes() {
        return this.notes;
    }
}
