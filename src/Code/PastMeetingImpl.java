package Code;

import java.util.Calendar;
import java.util.Set;

/**
 * Created by graemewilkinson on 25/03/16.
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {



    private String notes;

    /**
     * Past meeting constructor, if id, date or set of contacts is null an exception will be thrown
     * if notes are empty, then an exception will be thrown.
     * @param id
     * @param date
     * @param contacts
     */
    public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
        super(id, date, contacts);

    }

    @Override
    public String getNotes() {
        return null;
    }
}
