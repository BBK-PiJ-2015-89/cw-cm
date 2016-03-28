package Code;

import java.util.Calendar;
import java.util.Set;

/**
 * Created by BBK-PiJ-2015-89
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {

    public FutureMeetingImpl(int ID, Calendar date, Set<Contact> contacts) {
        super(ID, date, contacts);

    }
}
