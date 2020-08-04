package com.mastercard.developer.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class DateUtil {

    private static final DateFormat payloadDateFormatter = new SimpleDateFormat("MM/dd/yyyy");
    private static final DateFormat blackoutDateFormatter = new SimpleDateFormat("dd-MMM-yy");

    private static final Map<String, String> BLACKOUT_DATES = new HashMap<String, String>() {
        {
            put("Christmas Day", "25-DEC-20");
            put("Thanksgiving Holiday", "27-NOV-20");
            put("Thanksgiving Day", "26-NOV-20");
            put("Labor Day", "07-SEP-20");
            put("Independence Day", "04-JUL-20");
            put("Memorial Day", " 25-MAY-20");
            put("President's Day", "17-FEB-20");
            put("Martin Luther King Jr. Day", "20-JAN-20");
            put("New Year's Day", "01-JAN-20");
            put("BPX Holiday", "02-JAN-20");
        }
    };

    private static LocalDateTime getDate() {
        Date currentDate = new Date();
        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime;

    }

    private static boolean isBlackout(Date date){
        String strDate = blackoutDateFormatter.format(date);
        if(BLACKOUT_DATES.entrySet().contains(strDate)){
            return true;
        }
        else return false;
    }

    public static String getNextValidDate(){
        LocalDateTime localDateTime = getDate().plusDays(1);
        Date d = Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        while(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || isBlackout(calendar.getTime())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }


        Date newDate = calendar.getTime();
        String strDate = payloadDateFormatter.format(newDate);

        return strDate;
    }
}
