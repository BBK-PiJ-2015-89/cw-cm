package Code;

import java.util.Calendar;
import java.util.Set;

/**
 * Created by graemewilkinson on 24/03/16.
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {

    public FutureMeetingImpl(int ID, Calendar date, Set<Contact> contacts){
        super(ID, date, contacts);

    }
}
