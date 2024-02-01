package org.slavawins.winslib.helper;

import java.util.Date;

public class DateToTextConverter {


    public static String getRuTextDifferentAbs(Date startDate, Date endDate) {
        long milliseconds = Math.abs( endDate.getTime() - startDate.getTime());

        if (milliseconds >= 60 * 60 * 1000) {
            long hours = milliseconds / (60 * 60 * 1000);
            return " " + hours + " час" + (hours > 1 ? "а" : "");
        } else if (milliseconds >= 60 * 1000) {
            long minutes = milliseconds / (60 * 1000);
            return " " + minutes + " мин";
        } else {
            long seconds = milliseconds / 1000;
            return " " + seconds + " сек";
        }
    }
}
