package se.byfootapp.utils;

import java.util.Calendar;
import java.util.Locale;

public abstract class CalendarUtils {
    
    public static String getCalendarAsString(Calendar calendar){
        String calendarAsString = "";
        calendarAsString += calendar.get(Calendar.DAY_OF_MONTH) + "/";
        calendarAsString += calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + "/";
        calendarAsString += calendar.get(Calendar.YEAR);
        return calendarAsString;
    }

}
